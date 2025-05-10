package com.jclient.controllers;

import com.jclient.clients.HTTPClient;
import com.jclient.models.requests.SignInRequest;
import com.jclient.models.responses.SessionResponse;
import com.jclient.models.responses.SignInResponse;

import java.io.IOException;

public class RequestController {

    // Ahora propagamos IOException e InterruptedException
    public SignInResponse signIn(String username, String password) throws IOException, InterruptedException {

        HTTPClient httpClient = new HTTPClient();
        SignInRequest payload = new SignInRequest(username, password);
        return httpClient.signIn(payload);
    }

    // Si tienes verifySession en el controlador:
    public SessionResponse verifySession(String token) throws IOException, InterruptedException {

        return new HTTPClient().verifySession(token);
    }
}