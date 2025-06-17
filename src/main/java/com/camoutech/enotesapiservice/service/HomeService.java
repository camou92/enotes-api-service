package com.camoutech.enotesapiservice.service;

public interface HomeService {
    public Boolean verifyAccount(Integer userId,String verificationCode) throws Exception;

}