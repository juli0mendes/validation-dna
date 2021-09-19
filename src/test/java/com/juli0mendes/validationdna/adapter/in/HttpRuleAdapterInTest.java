package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.adapter.out.repository.MongoRuleRepository;
import com.juli0mendes.validationdna.application.mocks.RuleDtoMock;
import com.juli0mendes.validationdna.application.mocks.RuleMock;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("AdapterIn - Rule Http")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpRuleAdapterInTest {

    private static final String ENDPOINT = "/challenge-meli/v1/rules/";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoRuleRepository mongoRuleRepository;

    @BeforeEach
    @After
    public void setUp() {
        mongoRuleRepository.deleteAll();
    }

    @Test
    @DisplayName("Create with success")
    public void createWithSuccess() throws URISyntaxException {

        var ruleDto = RuleDtoMock.toCreate();

        var headers = new HttpHeaders();

        var request = new HttpEntity<>(ruleDto, headers);

        var responseCreate = this.testRestTemplate
                .exchange(ENDPOINT, POST, request, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(CREATED);

        var location = responseCreate.getHeaders().get("Location");
        var idFromLocation = getIdFromLocation(location);

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT + idFromLocation, GET, getHttpEntity(), RuleDto.class);

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(responseRead.getBody().getId()).isEqualTo(idFromLocation);
    }

    @Test
    @DisplayName("Bad Request Invalid Name")
    public void badRequestInvalidName() {

        var ruleDto = RuleDtoMock.invalidName();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        var createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        var responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("Bad Request Invalid Description")
    public void badRequestInvalidDescription() {

        var ruleDto = RuleDtoMock.invalidDescription();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        var createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        var responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("Bad Request Invalid Criterias")
    public void badRequestInvalidCriterias() {

        var ruleDto = RuleDtoMock.invalidDescription();

        var headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);

        var createRuleHttpEntity = new HttpEntity<>(ruleDto, headers);

        var responseCreate = testRestTemplate
                .exchange(ENDPOINT, POST, createRuleHttpEntity, Object.class);

        assertThat(responseCreate.getStatusCode()).isEqualTo(BAD_REQUEST);
    }

    @Test
    @DisplayName("Should return a Rule")
    public void shouldReturnARule() {

        var rule = this.mongoRuleRepository.save(RuleMock.toCreate());

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT + rule.getId(), GET, getHttpEntity(), RuleDto.class);

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(responseRead.getBody()).isNotNull();

        var ruleDto = responseRead.getBody();

        assertThat(ruleDto.getId()).isEqualTo(rule.getId());
        assertThat(ruleDto.getName()).isEqualTo(rule.getName());
        assertThat(ruleDto.getDescription()).isEqualTo(rule.getDescription());
        assertThat(ruleDto.getStatus()).isEqualTo(rule.getStatus());
        assertThat(ruleDto.getCriterias().size()).isEqualTo(rule.getCriterias().size());

        ruleDto.getCriterias().stream().forEach(criteriaDto -> {
            rule.getCriterias()
                    .stream()
                    .filter(criteria -> criteria.getCharactersSequence().equalsIgnoreCase(criteriaDto.getCharactersSequence()));
        });
    }

    @Test
    @DisplayName("Should return not found")
    public void shouldReturnNotFound() {

        final var idNotExists = "1234567890";

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT + idNotExists, GET, this.getHttpEntity(), RuleDto.class);

        assertThat(responseRead.getStatusCode()).isEqualTo(NOT_FOUND);
        assertThat(responseRead.getBody()).isNull();
    }

    @Test
    @DisplayName("Should return as Rules")
    public void shouldReturnAsRules() {

        for (int i = 0; i < 10; i++)
            this.mongoRuleRepository.save(RuleMock.toCreate());

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT, GET, getHttpEntity(), List.class);

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(responseRead.getBody()).isNotNull();

        var rulesDto = responseRead.getBody();

        assertThat(rulesDto.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("Should return as Rules zero")
    public void shouldReturnAsRulesZero() {

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT, GET, getHttpEntity(), List.class);

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(responseRead.getBody()).isNotNull();

        var rulesDto = responseRead.getBody();

        assertThat(rulesDto.size()).isEqualTo(0);
    }

    private String getIdFromLocation(List<String> location) {
        var id = location.get(0);

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

    private String dateFormatter(Instant instantDate) {
        var formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        return instantDate != null ? formatter.format(Date.from(instantDate)) : null;
    }
}
