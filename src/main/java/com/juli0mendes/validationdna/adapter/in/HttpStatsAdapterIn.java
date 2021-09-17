package com.juli0mendes.validationdna.adapter.in;

import com.juli0mendes.validationdna.application.ports.in.StatsDto;
import com.juli0mendes.validationdna.application.ports.in.StatsPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("challenge-meli/v1/stats")
public class HttpStatsAdapterIn {

    private static final Logger log = LoggerFactory.getLogger(HttpStatsAdapterIn.class);

    private final StatsPortIn statsPortIn;

    public HttpStatsAdapterIn(StatsPortIn statsPortIn) {
        this.statsPortIn = statsPortIn;
    }

    @GetMapping
    public ResponseEntity<StatsDto> findAll() {

        log.info("find-all; start; system;");

        StatsDto stats = this.statsPortIn.findStats();

        log.info("find-all; end; system; stats=\"{}\";", stats);

        return ResponseEntity.ok(stats);
    }
}