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

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequestMapping("challenge-meli/v1/creatures")
public class HttpCreatureAdapterIn {

    private static final Logger log = LoggerFactory.getLogger(HttpCreatureAdapterIn.class);

    private final CreaturePortIn creaturePortIn;

    public HttpCreatureAdapterIn(CreaturePortIn creaturePortIn) {
        this.creaturePortIn = creaturePortIn;
    }

    @PostMapping("/is-simian")
    public ResponseEntity<?> isSimian(@RequestBody @Valid CreatureDto creature) {

        log.info("is-simian; start; system; creature=\"{}\";", creature);

        String[] dna = this.convertArrayListToStringArray(creature);

        var isSimian = this.creaturePortIn.isSimian(dna);

        log.info("is-simian; end; system; isSimian=\"{}\";", isSimian);

        if (isSimian)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(FORBIDDEN).build();
    }

    private String[] convertArrayListToStringArray(CreatureDto creatureDto) {

        log.info("convert-array-list-to-string-array; start; system; creatureDto=\"{}\";", creatureDto);

        List<String> dnaListRequest = creatureDto.getDna();

        String[] dnaArray = new String[dnaListRequest.size()];

        for (int j = 0; j < dnaListRequest.size(); j++) {
            dnaArray[j] = dnaListRequest.get(j);
        }

        log.info("convert-array-list-to-string-array; end; system; dnaArray=\"{}\";", dnaArray);

        return dnaArray;
    }
}