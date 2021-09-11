package com.juli0mendes.validationdna.adapter.exceptions;

import java.util.List;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6497975673702371031L;

    private List<ErrorDetailDto> details;

    public NotFoundException() {
    }
}
