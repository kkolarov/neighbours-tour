//package com.efbet.NeighboursTour.configs;
//
//import com.efbet.NeighboursTour.oauth.AccessTokenValidator;
//import com.efbet.NeighboursTour.oauth.GoogleAccessTokenValidator;
//import com.efbet.NeighboursTour.oauth.GoogleTokenServices;
//import com.efbet.NeighboursTour.oauth.OAuthProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
//
//@Configuration
//@EnableResourceServer
//@EnableWebSecurity
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//    private static final String LOGIN_URL = "/login";
//    private static final String LOGOUT_URL = "/logout";
//    private static final String LOGOUT_SUCCESS_URL = "/";
//
//    @Autowired
//    private OAuthProperties oAuthProperties;
//
//    @Override
//    public void configure(final HttpSecurity http) throws Exception {
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().anyRequest().hasRole("USER");
//    }
//
//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.resourceId(oAuthProperties.getClientId());
//    }
//
//
//    @Bean
//    public ResourceServerTokenServices tokenServices(OAuthProperties oAuthProperties, AccessTokenValidator tokenValidator) {
//        GoogleTokenServices googleTokenServices = new GoogleTokenServices(tokenValidator);
//        googleTokenServices.setUserInfoUrl(oAuthProperties.getUserInfoUrl());
//        return googleTokenServices;
//    }
//
//    @Bean
//    public AccessTokenValidator tokenValidator(OAuthProperties oAuthProperties) {
//        GoogleAccessTokenValidator accessTokenValidator = new GoogleAccessTokenValidator();
//        accessTokenValidator.setClientId(oAuthProperties.getClientId());
//        accessTokenValidator.setCheckTokenUrl(oAuthProperties.getCheckTokenUrl());
//        return accessTokenValidator;
//    }
//
//    @Bean
//    public OAuthProperties oAuthProperties() {
//        return new OAuthProperties();
//    }
//}
