package com.juli0mendes.validationdna.application.mocks;

import com.juli0mendes.validationdna.application.domain.Validation;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.UUID;

public class ValidationMock {

    public static Validation success() {

        final String [] dnaIsSimian = {
                RandomStringUtils.randomAlphabetic(6),
                RandomStringUtils.randomAlphabetic(6),
                RandomStringUtils.randomAlphabetic(6),
                RandomStringUtils.randomAlphabetic(6),
                RandomStringUtils.randomAlphabetic(6),
                RandomStringUtils.randomAlphabetic(6)
        };

        return new Validation()
                .setId(UUID.randomUUID().toString())
                .setSimian(getRandomBoolean())
                .setDna(dnaIsSimian)
                .setCreatedAt(Instant.now());

    }

    private static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}