package com.otus.spring.ui.impl;

import java.util.List;

public class InputFormatException extends RuntimeException {

    private final Format format;
    private final List<Object> params;

    public InputFormatException(Format format, List<Object> params) {
        this.format = format;
        this.params = params;
    }

    public Format getInvalidFormat() {
        return format;
    }

    public List<Object> getParams() {
        return params;
    }

    public enum Format {

        DIGITS_WITH_SEPARATOR("format.only.digits.with.separator"),
        DIGITS_WITHIN_RANGE("format.digits.within.range");

        private final String messageKey;

        Format(String messageKey) {
            this.messageKey = messageKey;
        }

        public String getMessageKey() {
            return messageKey;
        }

    }

}
