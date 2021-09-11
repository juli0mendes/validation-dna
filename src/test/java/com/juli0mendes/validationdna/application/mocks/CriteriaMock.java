package com.juli0mendes.validationdna.application.mocks;

import com.juli0mendes.validationdna.application.domain.Criteria;
import com.juli0mendes.validationdna.application.domain.CriteriaStatus;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

import static com.juli0mendes.validationdna.application.domain.CriteriaStatus.ACTIVE;

public class CriteriaMock {

    public static Criteria successAaaa() {
        return new Criteria()
                .setStatus(ACTIVE)
                .setCharactersSequence("AAAA");
    }

    public static Criteria successBbbb() {
        return new Criteria()
                .setStatus(ACTIVE)
                .setCharactersSequence("BBBB");
    }
}
