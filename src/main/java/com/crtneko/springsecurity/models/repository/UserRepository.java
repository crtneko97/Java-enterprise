package com.crtneko.springsecurity.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crtneko.springsecurity.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // UserEntity could be an OPTIONAL as well if you want it to be
    UserEntity findByUsername(String username);

}