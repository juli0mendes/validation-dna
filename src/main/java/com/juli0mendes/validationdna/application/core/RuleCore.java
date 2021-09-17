package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.core.exceptions.BusinessException;
import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import com.juli0mendes.validationdna.application.ports.in.RulePortIn;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.juli0mendes.validationdna.application.domain.RuleStatus.ACTIVE;
import static java.util.stream.Collectors.toList;

@Service
public class RuleCore implements RulePortIn {

    // TODO - adicionar tratamento de erros

    private static final Logger log = LoggerFactory.getLogger(RuleCore.class);

    private final RuleDatabasePortOut ruleDatabasePortOut;

    public RuleCore(RuleDatabasePortOut ruleDatabasePortOut) {
        this.ruleDatabasePortOut = ruleDatabasePortOut;
    }

    @Override
    public RuleDto create(RuleDto rule) {

        log.info("create; start; system; rule=\"{}\";", rule);

        this.findRulesActives();
        this.findRuleByName(rule);

        Rule ruleCreated = this.ruleDatabasePortOut.uppsert(rule);

        log.info("create; end; system; ruleCreated=\"{}\";", ruleCreated);

        return RuleDto.create(
                ruleCreated.getId(),
                ruleCreated.getName(),
                ruleCreated.getDescription(),
                ruleCreated.getCriterias(),
                ruleCreated.getStatus());
    }

    @Override
    public RuleDto getById(String id) {

        log.info("create; start; system; id=\"{}\";", id);

        Rule rule = this.ruleDatabasePortOut.getById(id);

        log.info("create; end; system; rule=\"{}\";", rule);

        return RuleDto.create(
                rule.getId(),
                rule.getName(),
                rule.getDescription(),
                rule.getCriterias(),
                rule.getStatus());
    }

    private void findRuleByName(RuleDto ruleDto) {
        Rule ruleExists = this.ruleDatabasePortOut.getByName(ruleDto.getName());

        log.debug("create; business; system; ruleExists=\"{}\";", ruleExists);

        if (ruleExists != null)
            throw new DuplicateKeyException("duplicated key");
    }

    private void findRulesActives() {
        List<Rule> rulesActives = this.ruleDatabasePortOut.findAll()
                .stream()
                .filter(rule -> rule.getStatus() == ACTIVE)
                .collect(toList());

        log.debug("create; business; system; rulesActives=\"{}\";", rulesActives);

        if (rulesActives.size() > 0)
            throw new BusinessException("there is active rule");
    }
}
