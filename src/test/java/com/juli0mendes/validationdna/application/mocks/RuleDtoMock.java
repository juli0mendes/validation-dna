package com.juli0mendes.validationdna.application.mocks;

import com.juli0mendes.validationdna.application.domain.Criteria;
import com.juli0mendes.validationdna.application.domain.RuleStatus;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RuleDtoMock {

    public static RuleDto success() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());

        return new RuleDto()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(RuleStatus.ACTIVE);
    }

    public static RuleDto toCreate() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());

        return new RuleDto()
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(RuleStatus.ACTIVE);
    }

    public static RuleDto invalidName() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());

        return new RuleDto()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(2))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(RuleStatus.ACTIVE);
    }

    public static RuleDto invalidDescription() {

        List<Criteria> criterias = new ArrayList<>();
        criterias.add(CriteriaMock.successAaaa());
        criterias.add(CriteriaMock.successTttt());

        return new RuleDto()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setName(RandomStringUtils.randomAlphabetic(110))
                .setCriterias(criterias)
                .setStatus(RuleStatus.ACTIVE);
    }

    public static RuleDto invalidCriterias() {

        List<Criteria> criterias = new ArrayList<>();

        return new RuleDto()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(RuleStatus.ACTIVE);
    }
}
