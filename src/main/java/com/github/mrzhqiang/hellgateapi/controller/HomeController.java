package com.github.mrzhqiang.hellgateapi.controller;

import com.github.mrzhqiang.hellgateapi.model.Account;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiang.zhang
 */
@RestController
public class HomeController {

  @RequestMapping("/")
  public Account index() {
    Account account = new Account();
    account.setUsername("admin");
    account.setPassword("123456");
    return account;
  }

  @RequestMapping(value = "/api/register", method = RequestMethod.POST)
  public String register() {
    return "ok";
  }
}
