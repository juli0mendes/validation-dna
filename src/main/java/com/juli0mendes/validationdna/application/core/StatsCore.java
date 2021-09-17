package com.juli0mendes.validationdna.application.core;

import com.juli0mendes.validationdna.application.domain.Validation;
import com.juli0mendes.validationdna.application.ports.in.StatsDto;
import com.juli0mendes.validationdna.application.ports.in.StatsPortIn;
import com.juli0mendes.validationdna.application.ports.out.RuleDatabasePortOut;
import com.juli0mendes.validationdna.application.ports.out.ValidationDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class StatsCore implements StatsPortIn {

    private static final Logger log = LoggerFactory.getLogger(StatsCore.class);

    private final ValidationDatabasePortOut validationDatabasePortOut;

    public StatsCore(ValidationDatabasePortOut validationDatabasePortOut) {
        this.validationDatabasePortOut = validationDatabasePortOut;
    }

    @Override
    public StatsDto findStats() {
        log.info("find-stats; start; system; rule=\"{}\";");

        List<Validation> validations = this.validationDatabasePortOut.findAll();

        long countMutantDna = validations.stream().filter(item -> item.isSimian()).count();
        long countHumanDna = validations.stream().filter(item -> !item.isSimian()).count();
        long ration = this.calculateRation(countMutantDna, countHumanDna);

        StatsDto stats = StatsDto.create(countMutantDna, countHumanDna, ration);

        log.info("find-stats; end; system; stats=\"{}\";", stats);

        return stats;
    }

    private long calculateRation(long countMutantDna, long countHumanDna) {
        long total = countHumanDna + countMutantDna;
        return (countMutantDna * 100) / total;
    }

}
