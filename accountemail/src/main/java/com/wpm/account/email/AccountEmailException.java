package com.wpm.account.email;

public class AccountEmailException extends Exception {
    public AccountEmailException(){
        super();
    }

    public AccountEmailException(String msg, Exception e){
        super(msg, e);
    }
}
