package com.springsecurity.SpringSecurity.repository;

import com.springsecurity.SpringSecurity.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {

    Optional<UserInfo> findByName(String username);
}
