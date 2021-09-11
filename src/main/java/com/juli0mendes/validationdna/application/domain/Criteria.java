package com.juli0mendes.validationdna.application.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Criteria {

    @Valid
    @NotNull
    private CriteriaStatus status;

    @NotBlank
    @Size(min = 4, max = 4)
    private String charactersSequence;

    public Criteria() {
    }

    public CriteriaStatus getStatus() {
        return status;
    }

    public Criteria setStatus(CriteriaStatus status) {
        this.status = status;
        return this;
    }

    public String getCharactersSequence() {
        return charactersSequence;
    }

    public Criteria setCharactersSequence(String charactersSequence) {
        this.charactersSequence = charactersSequence;
        return this;
    }

    public static Criteria create(CriteriaStatus status, String charactersSequence) {
        return new Criteria()
                .setStatus(status)
                .setCharactersSequence(charactersSequence);
    }
}
