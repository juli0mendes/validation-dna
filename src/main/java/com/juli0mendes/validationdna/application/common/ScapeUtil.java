package com.juli0mendes.validationdna.application.common;

import org.apache.commons.text.StringEscapeUtils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ScapeUtil {

    public static String scapeStackTrace(Throwable e) {
        StringWriter errors = new StringWriter();

        e.printStackTrace(new PrintWriter(errors));

        String messageStackTrace = errors.toString();

        return StringEscapeUtils.escapeJava(messageStackTrace);
    }

    public static String scape(Object object) {
        return StringEscapeUtils.escapeJava(String.valueOf(object));
    }
}
