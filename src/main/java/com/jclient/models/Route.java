package com.jclient.models;

import com.jclient.models.interfaces.RouteInterface;
import java.net.URI;


public class Route {

    public static URI signIn() {
        return URI.create(RouteInterface.signInUrl);
    }

    public static URI signUp() {
        return URI.create(RouteInterface.signUpUrl);
    }

    public static URI verifySession() {
        return URI.create(RouteInterface.verifySessionUrl);
    }

}