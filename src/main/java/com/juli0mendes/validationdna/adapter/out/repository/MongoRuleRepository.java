package com.juli0mendes.validationdna.adapter.out.repository;

import com.juli0mendes.validationdna.application.domain.Rule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoRuleRepository extends MongoRepository<Rule, String> {
    Rule findByName(String name);
}
