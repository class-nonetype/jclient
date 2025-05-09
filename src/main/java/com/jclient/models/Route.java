package com.jclient.models;

import com.jclient.models.interfaces.RouteInterface;
import java.net.URI;


public class Route {

    private static boolean verifyEnvironmentVariable() {
        String environmentVariable = System.getenv("API_URL");

        return environmentVariable != null && !environmentVariable.isBlank();
    }

    public static URI signIn() {
        return URI.create(RouteInterface.SIGN_IN);
    }

    public static URI signUp() {
        return URI.create(RouteInterface.SIGN_UP);
    }

    public static URI verifySession() {
        return URI.create(RouteInterface.VERIFY_SESSION);
    }

}