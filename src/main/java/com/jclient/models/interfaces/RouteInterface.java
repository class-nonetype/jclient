package com.jclient.models.interfaces;

public interface RouteInterface {
    String API_URL = System.getenv().getOrDefault("API_URL", "http://192.168.1.85:7500/api/v1");
    String AUTHENTICATION_PREFIX = API_URL + "/authentication";
    String APPLICATION_PREFIX = API_URL + "/application";
    String SIGN_IN = AUTHENTICATION_PREFIX + "/sign-in";
    String SIGN_UP = AUTHENTICATION_PREFIX + "/sign-up";
    String VERIFY_SESSION = AUTHENTICATION_PREFIX + "/verify/session";
    // …
}