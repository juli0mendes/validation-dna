package com.juli0mendes.validationdna.application.mocks;

import com.juli0mendes.validationdna.application.domain.Criteria;

import static com.juli0mendes.validationdna.application.domain.CriteriaStatus.ACTIVE;

public class CriteriaMock {

    public static Criteria successAaaa() {
        return new Criteria()
                .setStatus(ACTIVE)
                .setCharactersSequence("AAAA");
    }

    public static Criteria successTttt() {
        return new Criteria()
                .setStatus(ACTIVE)
                .setCharactersSequence("TTTT");
    }

    public static Criteria successCccc() {
        return new Criteria()
                .setStatus(ACTIVE)
                .setCharactersSequence("CCCC");
    }

    public static Criteria successGggg() {
        return new Criteria()
                .setStatus(ACTIVE)
                .setCharactersSequence("GGGG");
    }
}
