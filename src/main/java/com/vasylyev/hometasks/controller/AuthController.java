package com.vasylyev.hometasks.controller;

import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.vasylyev.hometasks.service.AppSettingsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@AllArgsConstructor
public class AuthController {

    private final GoogleAuthorizationCodeFlow googleAuth;
    private final AppSettingsService appSettingsService;

    @GetMapping("/google/setup")
    @ResponseBody
    public String getGoogleSetup(@RequestParam String name, HttpServletRequest request) {
        return googleAuth.newAuthorizationUrl()
                .setRedirectUri(request.getRequestURL().toString().replace(request.getRequestURI(), "/google/auth"))
                .setScopes(googleAuth.getScopes())
                .setState(name)
                .toString() + "&prompt=consent";
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
        return "redirect:/account";
    }

}
