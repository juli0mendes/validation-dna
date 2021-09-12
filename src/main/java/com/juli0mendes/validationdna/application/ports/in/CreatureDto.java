package com.juli0mendes.validationdna.application.ports.in;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.juli0mendes.validationdna.application.domain.Criteria;
import com.juli0mendes.validationdna.application.domain.RuleStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreatureDto {

    @NotNull
    @Size(min = 1, message = "deve conter pelo menos 1 item")
    private List<String> dna;

    public CreatureDto() {
    }

    public List<String> getDna() {
        return dna;
    }

    public CreatureDto setDna(List<String> dna) {
        this.dna = dna;
        return this;
    }

    public static CreatureDto create(List<String> dna) {
        return new CreatureDto()
                .setDna(dna);
    }
}