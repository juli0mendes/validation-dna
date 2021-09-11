package com.juli0mendes.validationdna.adapter.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.juli0mendes.validationdna.application.common.ScapeUtil.scape;
import static com.juli0mendes.validationdna.application.common.ScapeUtil.scapeStackTrace;

@ControllerAdvice
public class ErrorHandler {

    // TODO -- implementar log
    private static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    private static final String ALREADY_EXISTS_MESSAGE = " already exists.";

    private final MessageService messageService;

    public ErrorHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    // ERRORS 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody
    ErrorDto handleBadRequest(HttpServletRequest req, Exception ex) {
        log.warn("handle-bad-request; exception; system; exception=\"{}\";", scape(ex));
        String errorMessage = this.buildErrorItems(((MethodArgumentNotValidException) ex).getBindingResult());
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), errorMessage,
                this.buildItems(((MethodArgumentNotValidException) ex).getBindingResult()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody
    ErrorDto handleIlegalArgument2(HttpServletRequest req, Exception ex) {
        log.warn("handle-ilegal-argument-2; exception; system; exception=\"{}\";", scape(ex));
        String message = this.handleNotReadableMessage(req, (HttpMessageNotReadableException) ex);
        List<ErrorDetailDto> details = this.buildListOfNotReadableMessage(req, (HttpMessageNotReadableException) ex);
        if (message != null && message.matches(".*request\\sbody\\sis\\smissing.*")) {
            message = "body must not be null";
        }
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), message, details);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicateKeyException.class)
    public @ResponseBody
    ErrorDto handleDuplicateKeyException(HttpServletRequest req, DuplicateKeyException ex) {
        log.error("handle-duplicate-key-exception; exception; system; exception=\"{}\";", scapeStackTrace(ex));
        return new ErrorDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    // ERROR 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    ResponseEntity<?> handleNotFound(HttpServletRequest req, Exception ex) {
        log.warn("handle-not-found; exception; system; exception=\"{}\";", scape(ex));
        return ResponseEntity.notFound().build();
    }

    private String buildErrorItems(BindingResult bindingResult) {
        if (bindingResult.getFieldErrors().isEmpty()) {
            return "";
        }
        var fullPath = bindingResult.getFieldErrors().get(0).getField();
        String[] paths = fullPath.contains(".") ?
                StringUtils.split(fullPath, ".") :
                new String[]{fullPath};

        try {
            Field field = bindingResult.getTarget().getClass().getDeclaredField(paths[0]);

            int index = 0;
            for (String path : paths) {
                if (index != 0) {
                    field = field.getType().getDeclaredField(path);
                }
                if (field.isAnnotationPresent(JsonProperty.class)) {
                    paths[index] = field.getAnnotation(JsonProperty.class).value();
                }
                index++;
            }

        } catch (NoSuchFieldException e) {

        }

        return convertToSnakeCase(StringUtils.join(paths, "."))
                + " " + bindingResult.getFieldErrors().stream().findFirst().get().getDefaultMessage();
    }

    private String convertToSnakeCase(String value) {
        return CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE).convert(value);
    }

    private List<ErrorDetailDto> buildItems(final BindingResult bindingResult) {

        if (bindingResult.getFieldErrors().isEmpty())
            return null;

        final List<ErrorDetailDto> details = new ArrayList<>();

        bindingResult.getFieldErrors().forEach(error -> details.add(new ErrorDetailDto(convertToSnakeCase(error.getField()), error.getDefaultMessage())));
        return details;
    }

    private String handleNotReadableMessage(HttpServletRequest req, HttpMessageNotReadableException e) {
        if (e.getCause() instanceof JsonMappingException) {
            return this.buildErrorMessage((JsonMappingException) e.getCause());
        }

        return e.getMessage();
    }

    private String buildErrorMessage(JsonMappingException e) {
        String fieldName;
        try {
            fieldName = StringUtils.join(e.getPath().stream().map(JsonMappingException.Reference::getFieldName).toArray(), ".");
        } catch (Exception ex) {
            fieldName = e.getPath().get(0).getFieldName();
        }
        return StringUtils.join(convertToSnakeCase(fieldName), " is in an invalid format");
    }

    private List<ErrorDetailDto> buildListOfNotReadableMessage(HttpServletRequest req, HttpMessageNotReadableException e) {
        if (e.getCause() instanceof JsonMappingException) {
            List<ErrorDetailDto> errorList = new ArrayList<>();
            errorList.add(this.buildErrorDetailDto((JsonMappingException) e.getCause()));
            return errorList;
        }

        return null;
    }

    private ErrorDetailDto buildErrorDetailDto(JsonMappingException e) {
        String fieldName;
        try {
            fieldName = StringUtils.join(e.getPath().stream().map(JsonMappingException.Reference::getFieldName).toArray(), ".");
        } catch (Exception ex) {
            fieldName = e.getPath().get(0).getFieldName();
        }
        return new ErrorDetailDto(convertToSnakeCase(fieldName), "is in an invalid format");
    }
}
