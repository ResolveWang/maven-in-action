package com.wpm.account.persist;

public class AccountPersistException extends Exception {
    public AccountPersistException(String msg, Exception e){
        super(msg, e);
    }
}
