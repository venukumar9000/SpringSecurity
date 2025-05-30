package com.springsecurity.SpringSecurity.service;

import com.springsecurity.SpringSecurity.entity.UserInfo;
import com.springsecurity.SpringSecurity.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private UserInfoRepository  userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public String addUser(UserInfo userInfo){
         userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfoRepository.save(userInfo);

        return "user saved successfully";
    }
}
