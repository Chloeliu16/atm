package com.xiaojun.login;

import org.springframework.stereotype.Component;

@Component
public class ATM {
  public void login(ILogin ilogin) {
     ilogin.accountLogin();
  }
}
