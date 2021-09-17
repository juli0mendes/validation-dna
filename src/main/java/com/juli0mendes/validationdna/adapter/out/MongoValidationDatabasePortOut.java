package com.juli0mendes.validationdna.adapter.out;

import com.juli0mendes.validationdna.adapter.out.repository.MongoValidationRepository;
import com.juli0mendes.validationdna.application.domain.Validation;
import com.juli0mendes.validationdna.application.ports.out.ValidationDatabasePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoValidationDatabasePortOut implements ValidationDatabasePortOut {

    private static final Logger log = LoggerFactory.getLogger(MongoValidationDatabasePortOut.class);

    private final MongoValidationRepository mongoValidationRepository;
    private final MongoTemplate mongoTemplate;

    public MongoValidationDatabasePortOut(final MongoValidationRepository mongoValidationRepository,
                                          final MongoTemplate mongoTemplate) {
        this.mongoValidationRepository = mongoValidationRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void uppsert(String[] dna, boolean isSimian) {

        log.info("uppsert; start; system; dna=\"{}\"; isSimian=\"{}\"", dna, isSimian);

        Validation validation = this.mongoValidationRepository.findByDna(dna);

        if (validation != null)
            validation = Validation.update(
                    validation.getId(),
                    validation.getDna(),
                    isSimian,
                    validation.getCreatedAt());
        else
            validation = Validation.create(
                    dna,
                    isSimian);

        Validation validationCreated = this.mongoValidationRepository.save(validation);

        log.info("uppsert; end; system; validationCreated=\"{}\";", validationCreated);
    }

    @Override
    public List<Validation> findAll() {

        log.info("find-all; start; system;");

        List<Validation> validations = this.mongoValidationRepository.findAll();

        log.info("find-all; end; system; validations=\"{}\";", validations);

        return validations;
    }
}
