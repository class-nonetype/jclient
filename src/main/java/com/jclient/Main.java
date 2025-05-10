package com.jclient;

import com.jclient.clients.HTTPClient;
import com.jclient.models.responses.SignInResponse;
import com.jclient.views.MenuView;
import com.jclient.views.SignInView;

import java.io.IOException;
import java.util.prefs.Preferences;


public class Main {
    public static void main(String[] args) {
        HTTPClient httpClient = new HTTPClient();
        Preferences prefs = Preferences.userNodeForPackage(SignInView.class);
        String token = prefs.get("userAccessToken", null);

        try {
            if (token != null) {
                SignInResponse session = httpClient.verifySession(token);
                if (session != null) {
                    new MenuView();
                } else {
                    new SignInView();
                }
            } else {
                new SignInView();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            new SignInView();
        }
    }
}

