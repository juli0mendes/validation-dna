package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.mocks.RuleDtoMock;
import com.juli0mendes.validationdna.application.mocks.RuleMock;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DisplayName("Core - Order")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RuleCoreTest {

    // TODO - adicionar logs
    // TODO - adicionar tratamento de erros

    @Autowired(required = true)
    private RuleCore ruleCore;

    @MockBean
    private RuleDatabasePortOut ruleDatabasePortOut;

    @Test
    @DisplayName("Should create rule with success")
    public void shouldCreateRuleWithSuccess() throws Exception {
        doReturn(null).when(this.ruleDatabasePortOut).getByName(anyString());
        doReturn(RuleMock.success()).when(this.ruleDatabasePortOut).uppsert(any());

        this.ruleCore.create(RuleDtoMock.success());

        verify(this.ruleDatabasePortOut, times(1)).getByName(anyString());
        verify(this.ruleDatabasePortOut, times(1)).uppsert(any());
    }

    @Test
    @DisplayName("Should throw DuplicateKeyException when rule already exists")
    public void shoudThrowDuplicateKeyExceptionWhenRuleAlreadyExists() throws Exception {
        doReturn(RuleMock.success()).when(this.ruleDatabasePortOut).getByName(anyString());

        Assert.assertThrows(DuplicateKeyException.class, () -> {
            this.ruleCore.create(RuleDtoMock.success());
        });

        verify(this.ruleDatabasePortOut, times(1)).getByName(anyString());
        verify(this.ruleDatabasePortOut, times(0)).uppsert(RuleDtoMock.success());
    }

    @Test
    @DisplayName("Should show rule already exists")
    public void shouldShowRuleAlreadyExists() throws Exception {
        doReturn(RuleMock.success()).when(this.ruleDatabasePortOut).getById(anyString());

        this.ruleCore.getById(anyString());

        verify(this.ruleDatabasePortOut, times(1)).getById(anyString());
    }
}
