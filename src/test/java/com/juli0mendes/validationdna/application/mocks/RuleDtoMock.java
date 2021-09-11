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
        criterias.add(CriteriaMock.successBbbb());

        return new RuleDto()
                .setId(UUID.randomUUID().toString())
                .setName(RandomStringUtils.randomAlphabetic(10))
                .setDescription(RandomStringUtils.randomAlphabetic(20))
                .setCriterias(criterias)
                .setStatus(RuleStatus.ACTIVE);
    }
}
