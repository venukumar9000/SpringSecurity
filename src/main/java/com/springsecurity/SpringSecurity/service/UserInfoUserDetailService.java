package com.springsecurity.SpringSecurity.service;

import com.springsecurity.SpringSecurity.config.UserInfoUserDetails;
import com.springsecurity.SpringSecurity.entity.UserInfo;
import com.springsecurity.SpringSecurity.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userInfo =userInfoRepository.findByName(username);
        return userInfo.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException(" no user found"));
    }
}
