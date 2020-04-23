package com.vasylyev.hometasks.controller;

import com.vasylyev.hometasks.dto.AccountDto;
import com.vasylyev.hometasks.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAccountPage() {
        ModelAndView accountModel = new ModelAndView();
        accountModel.setViewName("account");
        return accountModel;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public AccountDto getAccount() {
        return accountService.getDefaultAccount();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public void updateAccount(@RequestBody AccountDto accountDto) {
        accountService.addAccount(accountDto);
    }
}
