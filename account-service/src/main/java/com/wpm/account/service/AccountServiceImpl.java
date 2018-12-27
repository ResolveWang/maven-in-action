package com.wpm.account.service;

import java.util.Map;

import com.wpm.account.captcha.AccountCaptchaException;
import com.wpm.account.captcha.AccountCaptchaService;
import com.wpm.account.captcha.RandomGenerator;
import com.wpm.account.email.AccountEmailException;
import com.wpm.account.email.AccountEmailService;
import com.wpm.account.persist.AccountPersistException;
import com.wpm.account.persist.AccountPersistService;
import com.wpm.account.persist.Account;

import java.util.HashMap;

public class AccountServiceImpl implements AccountService{
    private AccountPersistService accountPersistService;

    private AccountEmailService accountEmailService;

    private AccountCaptchaService accountCaptchaService;


    public AccountPersistService getAccountPersistService() {
        return accountPersistService;
    }

    public void setAccountPersistService(AccountPersistService accountPersistService){
        this.accountPersistService = accountPersistService;
    }

    public AccountCaptchaService getAccountCaptchaService() {
        return accountCaptchaService;
    }

    public void setAccountCaptchaService(AccountCaptchaService accountCaptchaService) {
        this.accountCaptchaService = accountCaptchaService;
    }

    public AccountEmailService getAccountEmailService() {

        return accountEmailService;
    }

    public void setAccountEmailService(AccountEmailService accountEmailService) {
        this.accountEmailService = accountEmailService;
    }

    public byte[] generateCaptchaImage(String captchaKey) throws  AccountServiceException{
        try{
            return accountCaptchaService.generateCaptchaImage(captchaKey);
        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to generate Captcha Image");
        }
    }

    public String generateCaptchaKey() throws AccountServiceException{
        try{
            return accountCaptchaService.generateCaptchaKey();
        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to generate Captcha key");
        }
    }

    private Map<String, String> activationMap = new HashMap<String, String>();

    public void signup(SignUpRequest signUpRequest) throws AccountServiceException{
        try{
            if(!signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())){
                throw new AccountServiceException("2 passwords do not match");
            }

            if(!accountCaptchaService.validateCaptcha(signUpRequest.getCaptchaKey(), signUpRequest.getCaptchaValue())){
                throw new AccountServiceException("Incorrect Captcha");
            }

            Account account = new Account();
            account.setId(signUpRequest.getId());
            account.setEmail(signUpRequest.getEmail());
            account.setName(signUpRequest.getName());
            account.setPassword(signUpRequest.getPassword());
            account.setActivated(false);

            accountPersistService.createAccount(account);
            String activationId = RandomGenerator.getRandomString();
            activationMap.put(activationId, account.getId());
            String link = signUpRequest.getActivateServiceUrl().endsWith("/")?signUpRequest.getActivateServiceUrl() +
                    activationId : signUpRequest.getActivateServiceUrl() + "?key = " + activationId;
            accountEmailService.sendMail(account.getEmail(), "Please Activate Your Account", link);

        }catch (AccountCaptchaException e){
            throw new AccountServiceException("Unable to validate captcha");
        }catch (AccountPersistException e){
            throw new AccountServiceException("Unable to create account");
        }catch(AccountEmailException e){
            throw new AccountServiceException("Unable to send activation email");
        }
    }
}
