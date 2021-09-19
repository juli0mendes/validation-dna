package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.adapter.out.repository.MongoRuleRepository;
import com.juli0mendes.validationdna.adapter.out.repository.MongoValidationRepository;
import com.juli0mendes.validationdna.application.mocks.CreatureDtoMock;
import com.juli0mendes.validationdna.application.mocks.RuleMock;
import com.juli0mendes.validationdna.application.ports.in.CreatureDto;
import org.junit.After;
import org.junit.Before;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("AdapterIn - Creature Http")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpCreatureAdapterInTest {

    private static final String ENDPOINT = "/challenge-meli/v1/creatures/is-simian";
    private static final String RULE_NAME = "is_simian";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoRuleRepository mongoRuleRepository;

    @Autowired
    private MongoValidationRepository validationRepository;

    @BeforeEach
    @Before
    public void before() {
        this.validationRepository.deleteAll();
    }

    @BeforeEach
    @After
    public void setUp() {
        this.mongoRuleRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return as true")
    public void shouldReturnATrue() {

        CreatureDto creatureDto = CreatureDtoMock.successIsSimian();

        this.mongoRuleRepository.save(RuleMock.createRuleIsSimian());

        var headers = new HttpHeaders();

        var request = new HttpEntity<>(creatureDto, headers);

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT, POST, request, Object.class);

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(responseRead.getBody()).isNull();

        var validationsExits = this.validationRepository.findAll();

        assertThat(validationsExits.size()).isEqualTo(1);
        assertTrue(validationsExits.get(0).isSimian());
    }

    @Test
    @DisplayName("Should return as false")
    public void shouldReturnAFalse() {

        CreatureDto creatureDto = CreatureDtoMock.successIsNotSimian();

        this.mongoRuleRepository.save(RuleMock.createRuleIsSimian());

        var headers = new HttpHeaders();

        var request = new HttpEntity<>(creatureDto, headers);

        var response = this.testRestTemplate
                .exchange(ENDPOINT, POST, request, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(FORBIDDEN);
        assertThat(response.getBody()).isNull();

        var validationsExits = this.validationRepository.findAll();

        assertThat(validationsExits.size()).isEqualTo(1);
        assertFalse(validationsExits.get(0).isSimian());
    }

    @Test
    @DisplayName("Should return as BusinessException When Rule NotExist")
    public void shouldReturnAsBusinessExceptionWhenRuleNotExist() {

        CreatureDto creatureDto = CreatureDtoMock.successIsNotSimian();

        var headers = new HttpHeaders();

        var request = new HttpEntity<>(creatureDto, headers);

        var response = this.testRestTemplate
                .exchange(ENDPOINT, POST, request, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(UNPROCESSABLE_ENTITY);

        var validationsExits = this.validationRepository.findAll();

        assertThat(validationsExits.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return as BusinessException When Rule is Inactive")
    public void shouldReturnAsBusinessExceptionWhenRuleIsInactive() {

        CreatureDto creatureDto = CreatureDtoMock.successIsNotSimian();

        this.mongoRuleRepository.save(RuleMock.inactive());

        var headers = new HttpHeaders();

        var request = new HttpEntity<>(creatureDto, headers);

        var response = this.testRestTemplate
                .exchange(ENDPOINT, POST, request, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(UNPROCESSABLE_ENTITY);

        var validationsExits = this.validationRepository.findAll();

        assertThat(validationsExits.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return as BusinessException When Name Rule less than 1")
    public void shouldReturnAsBusinessExceptionWhenDnaCreatureNameRuleLessThan1() {

        CreatureDto creatureDto = CreatureDtoMock.errorSizeMin();

        var headers = new HttpHeaders();

        var request = new HttpEntity<>(creatureDto, headers);

        var response = this.testRestTemplate
                .exchange(ENDPOINT, POST, request, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);

        var validationsExits = this.validationRepository.findAll();

        assertThat(validationsExits.size()).isEqualTo(0);
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
