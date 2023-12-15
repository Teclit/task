package com.ecolepmn.task.entity;

public class AuthenticationResp {

    private final String jwt;

    public AuthenticationResp(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}

