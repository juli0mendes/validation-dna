package com.juli0mendes.validationdna.adapter.out.repository;

import com.juli0mendes.validationdna.application.domain.Validation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoValidationRepository extends MongoRepository<Validation, String> {
    Validation findByDna(String[] dna);
}
