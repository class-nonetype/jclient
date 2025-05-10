package com.jclient.interfaces;

public interface RouteInterface {
    String apiUrl = System.getenv().getOrDefault("API_URL", "http://192.168.1.85:7500/api/v1");

    String authenticationPrefix = apiUrl + "/authentication";
    String applicationPrefix = apiUrl + "/application";

    String signInUrl = authenticationPrefix + "/sign-in";
    String signUpUrl = authenticationPrefix + "/sign-up";
    String verifySessionUrl = authenticationPrefix + "/verify/session";
    // …
}