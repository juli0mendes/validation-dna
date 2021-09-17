package com.juli0mendes.validationdna.application.ports.out;

import com.juli0mendes.validationdna.application.domain.Validation;
import com.juli0mendes.validationdna.application.ports.in.StatsDto;

import java.util.List;

public interface ValidationDatabasePortOut {

    void uppsert(String[] dna, boolean isSimian);

    List<Validation> findAll();
}
