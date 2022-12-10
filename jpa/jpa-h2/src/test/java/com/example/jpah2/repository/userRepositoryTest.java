package com.example.jpah2.repository;

import com.example.jpah2.dto.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class userRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void crud(){ // create, read, update, delete
        userRepository.save(new User());
        System.out.println(">>> " + userRepository.findAll());
    }
}