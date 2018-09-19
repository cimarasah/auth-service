package com.cimarasah.auth.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ErrorMessage {

    private int code;
    private List<String> errors = new ArrayList<>();
    private String message;

    public ErrorMessage() {
    }

    public ErrorMessage(int code, String error) {
        this.code = code;
        this.errors = Arrays.asList(error);
    }

    public ErrorMessage(int code, List<String> errors) {
        this.code = code;
        this.errors = errors;
    }

    public ErrorMessage(int code, String message, String error) {
        this(code, message, Collections.singletonList(error));
    }

    public ErrorMessage(int code, String message, List<String> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

