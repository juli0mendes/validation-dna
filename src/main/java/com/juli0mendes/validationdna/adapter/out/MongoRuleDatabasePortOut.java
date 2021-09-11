package com.juli0mendes.validationdna.adapter.out;

import com.juli0mendes.validationdna.adapter.exceptions.NotFoundException;
import com.juli0mendes.validationdna.adapter.in.HttpRuleAdapterIn;
import com.juli0mendes.validationdna.adapter.out.repository.MongoRuleRepository;
import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class MongoRuleDatabasePortOut implements RuleDatabasePortOut {

    // TODO - adicionar logs
    // TODO - adicionar tratamento de erros

    private static final Logger logger = LoggerFactory.getLogger(MongoRuleDatabasePortOut.class);

    private final MongoRuleRepository mongoRuleRepository;
    private final MongoTemplate mongoTemplate;

    public MongoRuleDatabasePortOut(final MongoRuleRepository mongoRuleRepository,
                                    final MongoTemplate mongoTemplate) {
        this.mongoRuleRepository = mongoRuleRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Rule uppsert(RuleDto ruleDto) {

        Rule ruleToSave = null;
        Optional<Rule> ruleExists = null;

        if (ruleDto.getId() != null) {
            this.mongoRuleRepository.findById(ruleDto.getId())
                    .orElseThrow(NotFoundException::new);

            ruleToSave = Rule.update(
                    ruleDto.getName(),
                    ruleDto.getDescription(),
                    ruleDto.getCriterias(),
                    ruleDto.getStatus());
        }

        ruleToSave = Rule.create(
                ruleDto.getName(),
                ruleDto.getDescription(),
                ruleDto.getCriterias(),
                ruleDto.getStatus());

        return this.mongoRuleRepository.save(ruleToSave);
    }

    @Override
    public Rule getById(String id) {
        return this.mongoRuleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Rule getByName(String name) {
        return this.mongoRuleRepository.findByName(name);
    }
}
