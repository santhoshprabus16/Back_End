package com.login.login.service;

import com.login.login.models.User;

public class AuthenticationResponse {
    private final String token;

    public AuthenticationResponse(String token) {

        this.token=token;
    }


}
