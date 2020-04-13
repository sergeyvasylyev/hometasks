package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.google.GoogleSheetsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@AllArgsConstructor
public class GooglesheetController {

    private final GoogleSheetsService googleSheetsService;

    @RequestMapping(value = "/sheet", method = RequestMethod.GET)
    public void getSheet(){
//        try {
//            googleSheetsService.appendRow();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (GeneralSecurityException e) {
//            e.printStackTrace();
//        }
    }
}
