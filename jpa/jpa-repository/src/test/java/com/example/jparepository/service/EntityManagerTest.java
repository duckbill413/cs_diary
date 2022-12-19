package com.example.jparepository.service;

import com.example.jparepository.domain.Member;
import com.example.jparepository.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class EntityManagerTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void entityManagerTest(){
        System.out.println(entityManager.createQuery("select m from Member m").getResultList());
    }

    @Test
    void findCache(){
        // FEAT: Entity Cache 에 의하여 한 번만 select 문이 실행된다. 1차 캐시를 활용해 성능이 상승한다.
        System.out.println(memberRepository.findById(1L).get());
        System.out.println(memberRepository.findById(1L).get());
        System.out.println(memberRepository.findById(1L).get());
    }
    @Test
    void cacheFindTest(){
        Member member = memberRepository.findById(1L).get();
        member.setName("marrrrrrrrrrtin");
        memberRepository.save(member);
//        memberRepository.flush();

        System.out.println("---------------------");
        member.setEmail("marrrrrrrrrrrrrtin@gmail.com");
        memberRepository.save(member);
//        memberRepository.flush();

        System.out.println(">>>1 : " + memberRepository.findById(1L).get());
//        memberRepository.flush(); // INFO: Flush 메소드 호출로 의도적으로 데이터 동기화 가능
        System.out.println(">>>2 : " + memberRepository.findById(1L).get());

        // INFO: findAll() 등 복잡한 쿼리 발생시 데이터를 미리 flush 하고 그 다음을 실행
        memberRepository.findAll().forEach(System.out::println); // INFO: 마지막 라인에서 flush 발생
    }
}
