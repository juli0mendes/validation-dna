package com.juli0mendes.validationdna.application.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Document(collection = "rules")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Rule implements Serializable {

    private static final long serialVersionUID = -823885409620831322L;

    @Id
    private String id;

    private String name;

    private String description;

    private List<Criteria> criterias;

    private RuleStatus status;

    private Instant createdAt;

    private Instant updatedAt;

    public Rule() {
    }

    public String getId() {
        return id;
    }

    public Rule setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Rule setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Rule setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Criteria> getCriterias() {
        return criterias;
    }

    public Rule setCriterias(List<Criteria> criterias) {
        this.criterias = criterias;
        return this;
    }

    public RuleStatus getStatus() {
        return status;
    }

    public Rule setStatus(RuleStatus status) {
        this.status = status;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Rule setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Rule setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public static Rule create(String name, String description, List<Criteria> criterias, RuleStatus status) {
        return new Rule()
                .setName(name)
                .setDescription(description)
                .setCriterias(criterias)
                .setStatus(status)
                .setCreatedAt(Instant.now());
    }

    public static Rule update(String name, String description, List<Criteria> criterias, RuleStatus status) {
        return new Rule()
                .setName(name)
                .setDescription(description)
                .setCriterias(criterias)
                .setStatus(status)
                .setUpdatedAt(Instant.now());
    }
}
