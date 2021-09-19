package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.core.exceptions.BusinessException;
import com.juli0mendes.validationdna.application.mocks.RuleDtoMock;
import com.juli0mendes.validationdna.application.mocks.RuleMock;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
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

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Creature - Stats")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatureCoreTest {

    @Autowired
    private CreatureCore creatureCore;

    @MockBean
    private RuleDatabasePortOut ruleDatabasePortOut;

    @MockBean
    private ValidationDatabasePortOut validationDatabasePortOut;

    private final static String [] DNA_IS_SIMIAN = {
            "CTGAGA",
            "CTGAGC",
            "TATTGT",
            "AGAGGG",
            "CCCCTA",
            "TCACTG"
    };

    @Test
    @DisplayName("Should return true")
    public void shouldReturnTrue() throws Exception {

        doReturn(RuleMock.success()).when(this.ruleDatabasePortOut).getByName(anyString());
        doReturn(null).when(this.ruleDatabasePortOut).uppsert(RuleDtoMock.success());

        var isSimian = this.creatureCore.isSimian(DNA_IS_SIMIAN);

        verify(this.ruleDatabasePortOut, times(1)).getByName(anyString());
        verify(this.validationDatabasePortOut, times(1)).uppsert(DNA_IS_SIMIAN, true);

        assertTrue(isSimian);
    }

    @Test
    @DisplayName("Should throw BusinessException when status rule is inactive")
    public void shouldThrowBusinessExceptionWhenStatusRuleIsInactive() throws Exception {

        doReturn(RuleMock.inactive()).when(this.ruleDatabasePortOut).getByName(anyString());
        doReturn(null).when(this.ruleDatabasePortOut).uppsert(RuleDtoMock.success());

        Assert.assertThrows(BusinessException.class, () -> {
            this.creatureCore.isSimian(DNA_IS_SIMIAN);
        });

        verify(this.ruleDatabasePortOut, times(1)).getByName(anyString());
        verify(this.validationDatabasePortOut, times(0)).uppsert(DNA_IS_SIMIAN, true);
    }

    @Test
    @DisplayName("Should throw BusinessException when status rule not exists")
    public void shouldThrowBusinessExceptionWhenStatusRuleNotExists() throws Exception {

        doReturn(null).when(this.ruleDatabasePortOut).getByName(anyString());
        doReturn(null).when(this.ruleDatabasePortOut).uppsert(RuleDtoMock.success());

        Assert.assertThrows(BusinessException.class, () -> {
            this.creatureCore.isSimian(DNA_IS_SIMIAN);
        });

        verify(this.ruleDatabasePortOut, times(1)).getByName(anyString());
        verify(this.validationDatabasePortOut, times(0)).uppsert(DNA_IS_SIMIAN, true);
    }
}
