package com.jclient.models.requests;

public record SignInRequest(
        String username,
        String password
) {}
