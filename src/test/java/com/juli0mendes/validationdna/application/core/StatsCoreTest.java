package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.domain.Validation;
import com.juli0mendes.validationdna.application.ports.in.StatsDto;
import com.juli0mendes.validationdna.application.ports.out.ValidationDatabasePortOut;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Core - Stats")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StatsCoreTest {

    @Autowired
    private StatsCore statsCore;

    @MockBean
    private ValidationDatabasePortOut validationDatabasePortOut;

    @Test
    @DisplayName("Should return stats with one simian and one not simian")
    public void shouldReturnStatsWithOneSimianAndOneNotSimian() throws Exception {

        String[] dnaIsSimian = {randomUUID().toString(), randomUUID().toString()};
        String[] dnaNotSimian = {randomUUID().toString(), randomUUID().toString()};

        List<Validation> validations = Arrays.asList(
                Validation.create(dnaIsSimian, true),
                Validation.create(dnaNotSimian, false)
        );

        doReturn(validations).when(this.validationDatabasePortOut).findAll();

        StatsDto stats = this.statsCore.findStats();

        verify(this.validationDatabasePortOut, times(1)).findAll();

        assertEquals(50, stats.getRatio());
        assertEquals(1, stats.getCountHumanDna());
        assertEquals(1, stats.getCountMutantDna());
    }

    @Test
    @DisplayName("Should return stats with one simian and four not simian")
    public void shouldReturnStatsWithOneSimianAndOneFourSimian() throws Exception {

        List<Validation> validations = new ArrayList<>();

        String[] dnaIsSimian = {randomUUID().toString(), randomUUID().toString()};

        validations.add(Validation.create(dnaIsSimian, true));

        for (int i = 0; i < 4; i++) {
            String[] dnaNotSimian = {randomUUID().toString(), randomUUID().toString()};
            validations.add(Validation.create(dnaIsSimian, false));
        }

        doReturn(validations).when(this.validationDatabasePortOut).findAll();

        StatsDto stats = this.statsCore.findStats();

        verify(this.validationDatabasePortOut, times(1)).findAll();

        assertEquals(20, stats.getRatio());
        assertEquals(4, stats.getCountHumanDna());
        assertEquals(1, stats.getCountMutantDna());
    }

    @Test
    @DisplayName("Should return zero stats with success")
    public void shouldReturnZeroStatsWithSuccess() throws Exception {

        List<Validation> validations = Arrays.asList();

        doReturn(validations).when(this.validationDatabasePortOut).findAll();

        StatsDto stats = this.statsCore.findStats();

        verify(this.validationDatabasePortOut, times(1)).findAll();

        assertEquals(0, stats.getRatio());
        assertEquals(0, stats.getCountHumanDna());
        assertEquals(0, stats.getCountMutantDna());
    }
}
