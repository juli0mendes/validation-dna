package com.juli0mendes.validationdna.application.ports.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class StatsDto {

    private long countMutantDna;

    private long countHumanDna;

    private long ratio;

    public StatsDto() {
    }


    public long getCountMutantDna() {
        return countMutantDna;
    }

    public StatsDto setCountMutantDna(long countMutantDna) {
        this.countMutantDna = countMutantDna;
        return this;
    }

    public long getCountHumanDna() {
        return countHumanDna;
    }

    public StatsDto setCountHumanDna(long countHumanDna) {
        this.countHumanDna = countHumanDna;
        return this;
    }

    public long getRatio() {
        return ratio;
    }

    public StatsDto setRatio(long ratio) {
        this.ratio = ratio;
        return this;
    }

    public static StatsDto create(long countMutantDna, long countHumanDna, long ration) {
        return new StatsDto()
                .setCountMutantDna(countMutantDna)
                .setCountHumanDna(countHumanDna)
                .setRatio(ration);
    }
}
