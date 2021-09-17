package com.juli0mendes.validationdna.adapter.out;

import com.juli0mendes.validationdna.adapter.exceptions.NotFoundException;
import com.juli0mendes.validationdna.adapter.out.repository.MongoRuleRepository;
import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoRuleDatabasePortOut implements RuleDatabasePortOut {

    private static final Logger log = LoggerFactory.getLogger(MongoRuleDatabasePortOut.class);

    private final MongoRuleRepository mongoRuleRepository;
    private final MongoTemplate mongoTemplate;

    public MongoRuleDatabasePortOut(final MongoRuleRepository mongoRuleRepository,
                                    final MongoTemplate mongoTemplate) {
        this.mongoRuleRepository = mongoRuleRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Rule uppsert(RuleDto rule) {

        log.info("uppsert; start; system; rule=\"{}\";", rule);

        Rule ruleToSave = null;
        Optional<Rule> ruleExists = null;

        if (rule.getId() != null) {
            this.mongoRuleRepository.findById(rule.getId())
                    .orElseThrow(NotFoundException::new);

            ruleToSave = Rule.update(
                    rule.getName(),
                    rule.getDescription(),
                    rule.getCriterias(),
                    rule.getStatus());
        }

        ruleToSave = Rule.create(
                rule.getName(),
                rule.getDescription(),
                rule.getCriterias(),
                rule.getStatus());

        Rule ruleCreated = this.mongoRuleRepository.save(ruleToSave);

        log.info("uppsert; end; system; ruleCreated=\"{}\";", ruleCreated);

        return ruleCreated;
    }

    @Override
    public Rule getById(String id) {
        return this.mongoRuleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Rule getByName(String name) {
        return this.mongoRuleRepository.findByName(name);
    }

    @Override
    public List<Rule> findAll() {
        return this.mongoRuleRepository.findAll();
    }
}
