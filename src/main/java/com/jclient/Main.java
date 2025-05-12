package com.jclient;

import com.jclient.clients.HTTPClient;
import com.jclient.models.responses.SessionResponse;
import com.jclient.views.MenuView;
import com.jclient.views.SignInView;

import java.io.IOException;
import java.util.prefs.Preferences;

public class Main {
    public static void main(String[] args) {
        try {
            HTTPClient httpClient = new HTTPClient();
            Preferences prefs = Preferences.userNodeForPackage(SignInView.class);
            String userAccessToken = prefs.get("userAccessToken", null);


            if (userAccessToken != null) {
                // Se realiza un POST con el token almacenado,
                // se verifica la validez en el endpoint 'authentication/verify/session'
                SessionResponse session = httpClient.verifySession(userAccessToken);

                if (session != null) {
                    new MenuView();
                } else {
                    new SignInView();
                }
            } else {
                new SignInView();
            }


        } catch (Exception e) {
            new SignInView();
            throw new RuntimeException(e);
        }



    }
}
