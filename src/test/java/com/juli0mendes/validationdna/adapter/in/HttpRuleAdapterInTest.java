package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.adapter.out.repository.MongoRuleRepository;
import com.juli0mendes.validationdna.application.mocks.RuleDtoMock;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayName("AdapterIn - Creature Http")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpRuleAdapterInTest {

    private static final String ENDPOINT = "/challenge-meli/v1/rules/";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoRuleRepository mongoRuleRepository;

    @BeforeEach
    @After
    public void setUp() {
        mongoRuleRepository.deleteAll();
    }

    @Test
    @DisplayName("Create with success")
    public void createWithSuccess() {

        var ruleDto = RuleDtoMock.success();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<RuleDto> createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        ResponseEntity<Object> responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(CREATED);

        List<String> location = responseCreate.getHeaders().get("Location");
        String idFromLocation = getIdFromLocation(location);

        ResponseEntity<RuleDto> responseRead = testRestTemplate
                .exchange(ENDPOINT + idFromLocation, GET, getHttpEntity(), RuleDto.class);

        assertThat(responseRead.getBody().getId()).isEqualTo(ruleDto.getId());
    }

    @Test
    @DisplayName("Bad Request Invalid Name")
    public void badrequestInvalidName() {

        var ruleDto = RuleDtoMock.invalidName();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<RuleDto> createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        ResponseEntity<Object> responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("Bad Request Invalid Description")
    public void badrequestInvalidDescription() {

        var ruleDto = RuleDtoMock.invalidDescription();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<RuleDto> createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        ResponseEntity<Object> responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("Bad Request Invalid Criterias")
    public void badrequestInvalidCriterias() {

        var ruleDto = RuleDtoMock.invalidDescription();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        HttpEntity<RuleDto> createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        ResponseEntity<Object> responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    private String getIdFromLocation(List<String> location) {
        String id = location.get(0);

        int idIndex = id.lastIndexOf("/") + 1;

        return id.substring(idIndex);

    }

    public static HttpEntity<?> getHttpEntity() {
        var headers = getHttpHeaders();
        return new HttpEntity<>(headers);
    }

    private static HttpHeaders getHttpHeaders() {
        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return headers;
    }
}
