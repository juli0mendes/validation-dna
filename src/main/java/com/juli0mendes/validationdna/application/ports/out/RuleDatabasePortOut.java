package com.juli0mendes.validationdna.application.ports.out;

import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;

public interface RuleDatabasePortOut {
    Rule uppsert(RuleDto ruleDto);

    Rule getById(String id);

    Rule getByName(String name);
}
