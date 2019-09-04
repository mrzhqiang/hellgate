package com.github.mrzhqiang.hellgateapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiang.zhang
 */
@RestController
public class HomeController {

  @RequestMapping("/")
  public String index() {
    return "Hello World";
  }
}
