package com.example.jparepository.service;

import com.example.jparepository.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final EntityManager entityManager;

    @Transactional
    public void put(){
        Member member = new Member();
        member.setName("member");
        member.setEmail("member@gmail.com");
    }
    @Transactional
    public void managed(){
        Member member = new Member();
        member.setName("member");
        member.setEmail("member@gmail.com");

        entityManager.persist(member);

        member.setName("newMemberAfterPersist"); // INFO: name 의 정보가 반영됨을 확인 가능 save 가 호출되지 않더라도 반영 가능
    }
    @Transactional
    public void detached(){
        Member member = new Member();
        member.setName("member");
        member.setEmail("member@gmail.com");

        entityManager.persist(member);
        entityManager.detach(member); // INFO: newMemberAfterPersit 가 반영되지 않는다.

        member.setName("newMemberAfterPersist");
        entityManager.merge(member); // INFO: newMemberAfterPersist 가 반영됨
    }
    @Transactional
    public void detached_clear(){
        Member member = new Member();
        member.setName("member");
        member.setEmail("member@gmail.com");

        entityManager.persist(member);
        entityManager.detach(member); // INFO: newMemberAfterPersit 가 반영되지 않는다.

        member.setName("newMemberAfterPersist");
        entityManager.merge(member); // INFO: newMemberAfterPersist 가 반영됨

//        entityManager.flush(); // INFO: flush 를 하면 반영된다.
        entityManager.clear();
    }
}
