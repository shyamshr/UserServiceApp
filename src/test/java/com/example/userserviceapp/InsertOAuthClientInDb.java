package com.example.userserviceapp;

import com.example.userserviceapp.security.repositories.JpaRegisteredClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.UUID;

//@SpringBootTest
public class InsertOAuthClientInDb {

    private PasswordEncoder passwordEncoder;
    private JpaRegisteredClientRepository jpaRegisteredClientRepository;

    //@Autowired
    public InsertOAuthClientInDb(PasswordEncoder passwordEncoder, JpaRegisteredClientRepository jpaRegisteredClientRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jpaRegisteredClientRepository = jpaRegisteredClientRepository;
    }

    //@Test
    public void insertOAuthClientInDb() {
//        RegisteredClient postmanClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("product-service")
//                .clientSecret(passwordEncoder.encode("password"))
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//                .redirectUri("https://oauth.pstmn.io/v1/callback")
//                .postLogoutRedirectUri("http://127.0.0.1:8080/")
//                .scope(OidcScopes.OPENID)
//                .scope(OidcScopes.PROFILE)
//                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                .build();
//        jpaRegisteredClientRepository.save(postmanClient);
    }
}
