package com.xiaojun.login;

import org.springframework.stereotype.Component;

/**
 * The {@code ATM} class represents an ATM machine simulation in terms of user authentication.
 * It uses dependency injection to utilize different login mechanisms.
 */
@Component
public class ATM {
  public void login(ILogin ilogin) {
    ilogin.accountLogin();
  }
}
