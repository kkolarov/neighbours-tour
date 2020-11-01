package com.kamen.NeighboursTour.oauth;

import org.springframework.beans.factory.annotation.Value;

public class OAuthProperties {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    @Value("${oauth.checkTokenUrl}")
    private String checkTokenUrl;
    @Value("${oauth.userInfoUrl}")
    private String userInfoUrl;

    public OAuthProperties() {
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getCheckTokenUrl() {
        return checkTokenUrl;
    }

    public String getUserInfoUrl() {
        return userInfoUrl;
    }
}
