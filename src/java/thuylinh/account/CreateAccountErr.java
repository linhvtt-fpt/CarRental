/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thuylinh.account;

import java.io.Serializable;

/**
 *
 * @author Thuy Linh
 */
public class CreateAccountErr implements Serializable{
    private String emailFormatError;
    private String emailLengError;
    private String emailIsExisted;
    private String passwordLengError;
    private String confirmNotMatched;
    private String nameLengError;
    private String phoneIsNotNumber;
    private String addressLengError;
    
    public CreateAccountErr() {
    }

    public CreateAccountErr(String emailFormatError, String emailLengError, String emailIsExisted, String passwordLengError, String confirmNotMatched, String nameLengError, String phoneIsNotNumber, String addressLengError) {
        this.emailFormatError = emailFormatError;
        this.emailLengError = emailLengError;
        this.emailIsExisted = emailIsExisted;
        this.passwordLengError = passwordLengError;
        this.confirmNotMatched = confirmNotMatched;
        this.nameLengError = nameLengError;
        this.phoneIsNotNumber = phoneIsNotNumber;
        this.addressLengError = addressLengError;
    }

    public String getAddressLengError() {
        return addressLengError;
    }

    public void setAddressLengError(String addressLengError) {
        this.addressLengError = addressLengError;
    }

   

    public String getPhoneIsNotNumber() {
        return phoneIsNotNumber;
    }

    public void setPhoneIsNotNumber(String phoneIsNotNumber) {
        this.phoneIsNotNumber = phoneIsNotNumber;
    }

    

    public String getEmailFormatError() {
        return emailFormatError;
    }

    public void setEmailFormatError(String emailFormatError) {
        this.emailFormatError = emailFormatError;
    }

    public String getEmailLengError() {
        return emailLengError;
    }

    public void setEmailLengError(String emailLengError) {
        this.emailLengError = emailLengError;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }

    public String getPasswordLengError() {
        return passwordLengError;
    }

    public void setPasswordLengError(String passwordLengError) {
        this.passwordLengError = passwordLengError;
    }

    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }

    public String getNameLengError() {
        return nameLengError;
    }

    public void setNameLengError(String nameLengError) {
        this.nameLengError = nameLengError;
    }
}
