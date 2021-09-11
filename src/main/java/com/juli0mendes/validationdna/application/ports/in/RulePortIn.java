package com.juli0mendes.validationdna.application.ports.in;

public interface RulePortIn {

    RuleDto create(RuleDto ruleDto);

    RuleDto getById(String id);

}
