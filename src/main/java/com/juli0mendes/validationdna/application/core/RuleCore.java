package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import com.juli0mendes.validationdna.application.ports.in.RulePortIn;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class RuleCore implements RulePortIn {

    // TODO - adicionar logs
    // TODO - adicionar tratamento de erros

    private static final Logger logger = LoggerFactory.getLogger(RulePortIn.class);

    private final RuleDatabasePortOut ruleDatabasePortOut;

    public RuleCore(RuleDatabasePortOut ruleDatabasePortOut) {
        this.ruleDatabasePortOut = ruleDatabasePortOut;
    }

    @Override
    public RuleDto create(RuleDto ruleDto) {

        Rule ruleExists = this.ruleDatabasePortOut.getByName(ruleDto.getName());

        if (ruleExists != null)
            throw new DuplicateKeyException("duplicated key");

        Rule ruleCreated = this.ruleDatabasePortOut.uppsert(ruleDto);

        return RuleDto.create(
                ruleCreated.getId(),
                ruleCreated.getName(),
                ruleCreated.getDescription(),
                ruleCreated.getCriterias(),
                ruleCreated.getStatus());
    }

    @Override
    public RuleDto getById(String id) {
        Rule ruleExists = this.ruleDatabasePortOut.getById(id);

        return RuleDto.create(
                ruleExists.getId(),
                ruleExists.getName(),
                ruleExists.getDescription(),
                ruleExists.getCriterias(),
                ruleExists.getStatus());
    }
}
