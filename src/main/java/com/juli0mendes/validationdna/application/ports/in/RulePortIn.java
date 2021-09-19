package com.juli0mendes.validationdna.application.ports.in;

import com.juli0mendes.validationdna.application.domain.Rule;

import java.util.List;

public interface RulePortIn {

    RuleDto create(RuleDto ruleDto);

    RuleDto getById(String id);

    List<RuleDto> findAll();

}
