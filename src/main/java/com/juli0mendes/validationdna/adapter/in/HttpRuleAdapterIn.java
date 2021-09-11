package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import com.juli0mendes.validationdna.application.ports.in.RulePortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("challenge-meli/v1/rules")
public class HttpRuleAdapterIn {

    // TODO - adicionar logs
    // TODO - adicionar tratamento de erros

    private static final Logger logger = LoggerFactory.getLogger(HttpRuleAdapterIn.class);

    private final RulePortIn rulePortIn;

    public HttpRuleAdapterIn(RulePortIn rulePortIn) {
        this.rulePortIn = rulePortIn;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RuleDto ruleDto) {
        var ruleCreated = this.rulePortIn.create(ruleDto);

        return ResponseEntity.created(this.getLocation(ruleCreated.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> getById(@PathVariable("id") String id) {
        RuleDto ruleDtoExists = this.rulePortIn.getById(id);

        return ResponseEntity.ok(ruleDtoExists);
    }

    private URI getLocation(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
