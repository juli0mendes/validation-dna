package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.application.ports.in.CreatureDto;
import com.juli0mendes.validationdna.application.ports.in.CreaturePortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("challenge-meli/v1/creatures")
public class HttpCreatureAdapterIn {

    // TODO - adicionar logs
    // TODO - adicionar tratamento de erros

    private static final Logger logger = LoggerFactory.getLogger(HttpCreatureAdapterIn.class);

    private final CreaturePortIn creaturePortIn;

    public HttpCreatureAdapterIn(CreaturePortIn creaturePortIn) {
        this.creaturePortIn = creaturePortIn;
    }

    @PostMapping("/is-simian")
    public ResponseEntity<?> create(@RequestBody @Valid CreatureDto creatureDto) {

        String[] dna = this.convertArrayListToStringArray(creatureDto);

        var ruleCreated = this.creaturePortIn.isSimian(dna);

        return ResponseEntity.ok().build();
    }

    private String[] convertArrayListToStringArray(CreatureDto creatureDto) {
        List<String> dnaListRequest = creatureDto.getDna();

        String[] dnaArray = new String[dnaListRequest.size()];

        for (int j = 0; j < dnaListRequest.size(); j++) {
            dnaArray[j] = dnaListRequest.get(j);
        }
        return dnaArray;
    }
}