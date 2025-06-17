package com.camoutech.enotesapiservice.service.impl;

import com.camoutech.enotesapiservice.entity.AccountStatus;
import com.camoutech.enotesapiservice.entity.User;
import com.camoutech.enotesapiservice.exception.ResourceNotFoundException;
import com.camoutech.enotesapiservice.exception.SuccessException;
import com.camoutech.enotesapiservice.repository.UserRepository;
import com.camoutech.enotesapiservice.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("invalid user"));

        if(user.getStatus().getVerificationCode()==null)
        {
            throw new SuccessException("Account alreday verified");
        }

        if (user.getStatus().getVerificationCode().equals(verificationCode)) {
            AccountStatus status = user.getStatus();
            status.setIsActive(true);
            status.setVerificationCode(null);

            userRepo.save(user);

            return true;
        }

        return false;
    }

}