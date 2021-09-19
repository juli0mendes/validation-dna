package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.application.domain.Rule;
import com.juli0mendes.validationdna.application.ports.in.RuleDto;
import com.juli0mendes.validationdna.application.ports.in.RulePortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("challenge-meli/v1/rules")
public class HttpRuleAdapterIn {

    private static final Logger log = LoggerFactory.getLogger(HttpRuleAdapterIn.class);

    private final RulePortIn rulePortIn;

    public HttpRuleAdapterIn(RulePortIn rulePortIn) {
        this.rulePortIn = rulePortIn;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid RuleDto ruleDto) {

        log.info("create; start; system; ruleDto=\"{}\";", ruleDto);

        var ruleCreated = this.rulePortIn.create(ruleDto);

        log.info("create; end; system; ruleCreated=\"{}\";", ruleCreated);

        return ResponseEntity.created(this.getLocation(ruleCreated.getId())).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> getById(@PathVariable("id") String id) {

        log.info("get-by-id; start; system; id=\"{}\";", id);

        RuleDto ruleExists = this.rulePortIn.getById(id);

        log.info("get-by-id; end; system; ruleExists=\"{}\";", ruleExists);

        return ResponseEntity.ok(ruleExists);
    }

    @GetMapping
    public ResponseEntity<List<RuleDto>> findAll() {

        // TODO -- implementar paginacao

        log.info("find-all; start; system;");

        List<RuleDto> rules = this.rulePortIn.findAll();

        log.info("find-all; end; system; rules=\"{}\";", rules);

        return ResponseEntity.ok(rules);
    }

    private URI getLocation(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
