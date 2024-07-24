package wh.duckbill.nplusone;

import jakarta.persistence.EntityManager;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.lazy.Member;
import wh.duckbill.nplusone.lazy.Order;
import wh.duckbill.nplusone.jpa.MemberRepository;
import wh.duckbill.nplusone.jpa.OrderRepository;
import wh.duckbill.nplusone.querydsl.MemberSearch;

import java.util.List;
import java.util.stream.Collectors;

import static org.jeasy.random.FieldPredicates.*;

@SpringBootTest
public class LazyTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MemberSearch memberSearch;

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Member.class)))
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Order.class)));

    EasyRandom easyRandom = new EasyRandom(parameters);

    /**
     * FetchType.LAZY
     * 멤버 조회시 N+1 문제가 생기지 않는다.
     */
    @Transactional
    @DisplayName("멤버-오더 생성 및 멤버 조회 테스트 (LAZY)")
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            Member member = easyRandom.nextObject(Member.class);
            memberRepository.save(member);
            Order order = easyRandom.nextObject(Order.class);
            order.setMember(member);
            member.getOrders().add(order);
            orderRepository.save(order);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        List<Member> everyMembers = memberRepository.findAll();
    }

    /**
     * FetchType.LAZY
     * test1 과 동일하게 멤버를 조회하였으나
     * member 의 하위 연관관계인 order 를 조회하자
     * N+1 문제가 발생하는 것을 확인할 수 있음
     */
    @Transactional
    @DisplayName("멤버-오더 생성 및 멤버,오더 조회 테스트 (LAZY)")
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            Member member = easyRandom.nextObject(Member.class);
            memberRepository.save(member);
            Order order = easyRandom.nextObject(Order.class);
            order.setMember(member);
            member.getOrders().add(order);
            orderRepository.save(order);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        List<Member> everyMembers = memberRepository.findAll();
        List<String> orderNames = everyMembers.stream().flatMap(member -> member.getOrders().stream().map(Order::getName)).collect(Collectors.toList());
        System.out.println(orderNames);
    }

    /**
     * FetchType.LAZY - Fetch Join
     * FetchJoin 을 이용하여 조회하면 하위 연관관계를 조회해도
     * N+1 문제가 생기지 않음
     */
    @Transactional
    @DisplayName("멤버-오더 생성 및 멤버,오더 FETCH JOIN 조회 테스트 (LAZY)")
    @Test
    public void test3() {
        for (int i = 0; i < 10; i++) {
            Member member = easyRandom.nextObject(Member.class);
            memberRepository.save(member);
            Order order = easyRandom.nextObject(Order.class);
            order.setMember(member);
            member.getOrders().add(order);
            orderRepository.save(order);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        List<Member> everyMembers = memberRepository.findAllWithOrder();
        List<String> orderNames = everyMembers.stream().flatMap(member -> member.getOrders().stream().map(Order::getName)).collect(Collectors.toList());
        System.out.println(orderNames);
    }

    /**
     * FetchType.LAZY - EntityGraph
     * EntityGraph 를 이용하여 조회
     * N+1 문제 생기지 않음
     */
    @Transactional
    @DisplayName("멤버-오더 생성 및 멤버,오더 ENTITY GRAPH 조회 테스트 (LAZY)")
    @Test
    public void test4() {
        for (int i = 0; i < 10; i++) {
            Member member = easyRandom.nextObject(Member.class);
            memberRepository.save(member);
            Order order = easyRandom.nextObject(Order.class);
            order.setMember(member);
            member.getOrders().add(order);
            orderRepository.save(order);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        List<Member> everyMembers = memberRepository.findAllEntityGraph();
        List<String> orderNames = everyMembers.stream().flatMap(member -> member.getOrders().stream().map(Order::getName)).collect(Collectors.toList());
        System.out.println(orderNames);
    }

    /**
     * FetchType.LAZY - QueryDsl
     * QueryDsl 을 이용하여 조회
     * N+1 문제 생기지 않음
     */
    @Transactional
    @DisplayName("멤버-오더 생성 및 멤버,오더 Querydsl 조회 테스트 (LAZY)")
    @Test
    public void test5() {
        for (int i = 0; i < 10; i++) {
            Member member = easyRandom.nextObject(Member.class);
            memberRepository.save(member);
            Order order = easyRandom.nextObject(Order.class);
            order.setMember(member);
            member.getOrders().add(order);
            orderRepository.save(order);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();

        List<Member> members = memberSearch.searchAllWithOrders();
        List<String> names = members.stream().flatMap(member -> member.getOrders().stream().map(Order::getName)).toList();
        System.out.println(names);
    }
}
