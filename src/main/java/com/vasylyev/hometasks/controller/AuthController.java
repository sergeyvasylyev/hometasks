package com.vasylyev.hometasks.controller;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class AuthController {

    private final GoogleAuthorizationCodeFlow googleAuth;

    @GetMapping("/google/setup")
    public String getGoogleSetup(@RequestParam String name, HttpServletRequest request) {
        String userEmail = "s.vasylyev@gmail.com";
        return "redirect:" + googleAuth.newAuthorizationUrl()
                .setRedirectUri(request.getRequestURL().toString().replace(request.getRequestURI(), "/google/auth"))
                .setState(userEmail)
                .build();
    }

    @GetMapping("/google/auth")
    public String getGoogleResponse(HttpServletRequest request,
                                    @RequestParam String code,
                                    @RequestParam String state
    ) throws IOException {
        TokenResponse tokenResponse = googleAuth.newTokenRequest(code)
                .setRedirectUri(request.getRequestURL().toString())
                .execute();
        googleAuth.createAndStoreCredential(tokenResponse, state);
        return "/account";
    }

}
