package com.juli0mendes.validationdna.application.mocks;

import com.juli0mendes.validationdna.application.domain.Criteria;
import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.domain.RuleStatus;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.juli0mendes.validationdna.application.domain.RuleStatus.ACTIVE;

public class RuleMock {

    private static final String RULE_NAME = "is_simian";

    public static Rule success() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());
        criterias.add(CriteriaMock.successCccc());
        criterias.add(CriteriaMock.successGggg());

        return new Rule()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(ACTIVE)
                .setCreatedAt(Instant.now());
    }

    public static Rule createRuleIsSimian() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());
        criterias.add(CriteriaMock.successCccc());
        criterias.add(CriteriaMock.successGggg());

        return new Rule()
                .setId(UUID.randomUUID().toString())
                .setName(RULE_NAME)
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(ACTIVE)
                .setCreatedAt(Instant.now());
    }

    public static Rule toCreate() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());
        criterias.add(CriteriaMock.successCccc());
        criterias.add(CriteriaMock.successGggg());

        return new Rule()
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(ACTIVE)
                .setCreatedAt(Instant.now());
    }

    public static Rule inactive() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());
        criterias.add(CriteriaMock.successCccc());
        criterias.add(CriteriaMock.successGggg());

        return new Rule()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(RuleStatus.INACTIVE)
                .setCreatedAt(Instant.now());
    }
}
