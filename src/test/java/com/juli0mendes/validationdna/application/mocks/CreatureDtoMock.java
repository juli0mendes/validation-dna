package com.juli0mendes.validationdna.application.mocks;

import com.juli0mendes.validationdna.application.ports.in.CreatureDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class CreatureDtoMock {

    public static CreatureDto successIsSimian() {

        final var dnaIsSimian = asList(
                "CTGAGA",
                "CTGAGC",
                "TATTGT",
                "AGAGGG",
                "CCCCTA",
                "TCACTG"
        );

        return new CreatureDto()
                .setDna(dnaIsSimian);
    }

    public static CreatureDto successIsNotSimian() {

        final var dnaIsSimian = asList(
                "XXXXXX",
                "XXXXXX",
                "XXXXXX",
                "XXXXXX",
                "XXXXXX",
                "XXXXXX"
        );

        return new CreatureDto()
                .setDna(dnaIsSimian);
    }

    public static CreatureDto errorSizeMin() {

        final List<String> dnaIsSimian = new ArrayList<>();

        return new CreatureDto()
                .setDna(dnaIsSimian);
    }
}
