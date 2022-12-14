package com.example.jparepository.domain;

import com.example.jparepository.repository.MemberHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberHistoryRepositoryTest {
    @Autowired
    private MemberHistoryRepository memberHistoryRepository;

}