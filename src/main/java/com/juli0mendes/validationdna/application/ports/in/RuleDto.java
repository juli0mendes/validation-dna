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
public class RuleDto {

    private String id;

    @NotBlank
    @Size(min = 3, max = 40)
    private String name;

    @NotBlank
    @Size(min = 5, max = 100)
    private String description;

    @NotNull
    @Size(min = 1, message = "deve conter pelo menos 1 item")
    private List<@Valid Criteria> criterias;

    @Valid
    @NotNull
    private RuleStatus status;

    public RuleDto() {
    }

    public String getId() {
        return id;
    }

    public RuleDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RuleDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RuleDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Criteria> getCriterias() {
        return criterias;
    }

    public RuleDto setCriterias(List<Criteria> criterias) {
        this.criterias = criterias;
        return this;
    }

    public RuleStatus getStatus() {
        return status;
    }

    public RuleDto setStatus(RuleStatus status) {
        this.status = status;
        return this;
    }

    public static RuleDto create(String id,
                                 String name,
                                 String description,
                                 List<Criteria> criterias,
                                 RuleStatus status) {
        return new RuleDto()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setCriterias(criterias)
                .setStatus(status);
    }
}
