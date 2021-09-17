package com.juli0mendes.validationdna.application.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Document(collection = "validations")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Validation implements Serializable {

    private static final long serialVersionUID = -4019845289125653581L;

    private String id;

    private String[] dna;

    private boolean isSimian;

    private Instant createdAt;

    private Instant updatedAt;

    public Validation() {
    }

    public String getId() {
        return id;
    }

    public Validation setId(String id) {
        this.id = id;
        return this;
    }

    public String[] getDna() {
        return dna;
    }

    public Validation setDna(String[] dna) {
        this.dna = dna;
        return this;
    }

    public boolean isSimian() {
        return isSimian;
    }

    public Validation setSimian(boolean simian) {
        isSimian = simian;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Validation setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Validation setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public static Validation create(String[] dna, boolean isSimian) {
        return new Validation()
                .setDna(dna)
                .setSimian(isSimian)
                .setCreatedAt(Instant.now());
    }

    public static Validation update(String id, String[] dna, boolean isSimian, Instant createdAt) {
        return new Validation()
                .setId(id)
                .setDna(dna)
                .setSimian(isSimian)
                .setCreatedAt(createdAt)
                .setUpdatedAt(Instant.now());
    }
}
