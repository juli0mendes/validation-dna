package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.adapter.out.repository.MongoValidationRepository;
import com.juli0mendes.validationdna.application.mocks.ValidationMock;
import com.juli0mendes.validationdna.application.ports.in.StatsDto;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("AdapterIn - Stats Http")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HttpStatsAdapterInTest {

    private static final String ENDPOINT = "/challenge-meli/v1/stats/";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoValidationRepository mongoValidationRepository;

    @BeforeEach
    @After
    public void setUp() {
        mongoValidationRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return as Stats")
    public void shouldReturnARule() {

        for (int i = 0; i < 5; i++)
            this.mongoValidationRepository.save(ValidationMock.success());

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT, GET, getHttpEntity(), StatsDto.class);

        var statsDto = responseRead.getBody();

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(statsDto).isNotNull();

        var ratio = this.calculateRation(statsDto.getCountMutantDna(), statsDto.getCountHumanDna());

        assertThat(statsDto.getRatio()).isEqualTo(ratio);
    }

    @Test
    @DisplayName("Should return as Stats zero")
    public void shouldReturnARuleZero() {

        var responseRead = this.testRestTemplate
                .exchange(ENDPOINT, GET, getHttpEntity(), StatsDto.class);

        var statsDto = responseRead.getBody();

        assertThat(responseRead.getStatusCode()).isEqualTo(OK);
        assertThat(statsDto).isNotNull();
        assertThat(statsDto.getCountMutantDna()).isEqualTo(0);
        assertThat(statsDto.getCountHumanDna()).isEqualTo(0);

        var ratio = this.calculateRation(statsDto.getCountMutantDna(), statsDto.getCountHumanDna());

        assertThat(statsDto.getRatio()).isEqualTo(ratio);
    }

    private long calculateRation(long countMutantDna, long countHumanDna) {
        long total = countHumanDna + countMutantDna;

        if (total == 0)
            return 0;
        else
            return (countMutantDna * 100) / total;
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
