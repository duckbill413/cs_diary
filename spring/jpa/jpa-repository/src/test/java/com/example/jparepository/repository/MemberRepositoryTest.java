package com.example.jparepository.repository;

import com.example.jparepository.domain.Address;
import com.example.jparepository.domain.Gender;
import com.example.jparepository.domain.Member;
import com.example.jparepository.domain.MemberHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberHistoryRepository memberHistoryRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    void crud(){ // create, read, update, delete

        System.out.println(">>> name 을 기준으로 내림차순 출력");
        List<Member> members1 = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        members1.forEach(System.out::println);

        System.out.println(">>> ID 가 1, 3, 5번인 Member 출력");
        List<Member> members2 = memberRepository.findAllById(Lists.newArrayList(1L, 3L, 5L));
        members2.forEach(System.out::println);

        System.out.println(">>> 멤버 삽입");
        Member member1 = new Member("duckbill", "duckbill@gmail.com");
        Member member2 = new Member("pypy", "pypy@gmail.com");
        memberRepository.saveAll(Lists.newArrayList(member1, member2));
        memberRepository.findAll().forEach(System.out::println);

        System.out.println("Member 중 ID 1 멤버 조회");
        Member member = memberRepository.findById(1L).orElse(null);
        System.out.println(member);
    }

    @Test
    void countAndExists(){
        long count = memberRepository.count();
        System.out.println(count);

        boolean exists = memberRepository.existsById(1L); // count 를 참조
        System.out.println(exists);
    }

    @Test
    void delete(){ // FEAT: n개의 delete 에 대해 n번의 delete 명령 발생
        memberRepository.delete(memberRepository.findById(1L).orElseThrow(RuntimeException::new));
        memberRepository.findAll().forEach(System.out::println);
    }

    @Test
    void deleteInBatch(){ // FEAT: n개의 delete 에 대해 1번의 delete 명령 발생 (or 명령)
        memberRepository.deleteAllByIdInBatch(Lists.newArrayList(1L, 3L));
        memberRepository.findAll().forEach(System.out::println);
    }

    @Test
    void paging(){ // 페이징 기법
        Page<Member> members = memberRepository.findAll(PageRequest.of(0, 3)); // 크기가 3인 페이지의 0번쨰 장
        System.out.println("page: " + members);
        System.out.println("totalElements: " + members.getTotalElements());
        System.out.println("totalPages: " + members.getTotalPages());
        System.out.println("numberOfElements: " + members.getNumberOfElements());
        System.out.println("sort: " + members.getSort());
        System.out.println("size: " + members.getSize());
        members.stream().forEach(System.out::println);
    }

    @Test
    void qbe(){ // MEMO: query by example : entity 를 example 로 만들고 matcher 를 추가하여 쿼리를 생성
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("name") // name 은 무시
                .withMatcher("email", endsWith()); // email 은 뒷 부분 검색

        Example<Member> example = Example.of(new Member("ma", "gmail.com"), matcher);
        memberRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void select(){
        System.out.println("findByName: " + memberRepository.findByName("martin"));
        System.out.println("findByCreatedAt: " + memberRepository.findByCreatedAtAfter(
                LocalDateTime.now().minusDays(1L)
        ));
        System.out.println("findByCreatedAtGreaterThan: " + memberRepository.findByCreatedAtGreaterThan(
                LocalDateTime.now().minusDays(1L)
        ));
        System.out.println("findBYCreatedAtGreaterThanEqual: " + memberRepository.findByCreatedAtGreaterThanEqual(
                LocalDateTime.now().minusDays(1L)
        ));
        System.out.println("findByIdIsNotNull: " + memberRepository.findByIdIsNotNull());
        System.out.println("findByNameIn: " + memberRepository.findByNameIn(
                Lists.newArrayList("martin", "dennis")
        ));

        System.out.println("findByNameStartingWith: " + memberRepository.findByNameStartingWith("mar"));
        System.out.println("findByNameEndingWith: " + memberRepository.findByNameEndingWith("tin"));
        System.out.println("findByNameContaining: " + memberRepository.findByNameContaining("rti"));
        System.out.println("findByNameLike: " + memberRepository.findByNameLike("%rti%"));

    }

    @Test
    void sorting(){
        System.out.println("findTop1ByName: " + memberRepository.findTop1ByName("martin"));
        System.out.println("findLast1ByName: " + memberRepository.findTop1ByNameOrderByIdDesc("martin"));
        System.out.println("findFirstByNameOrderByIdDescEmailAsc: " + memberRepository.findFirstByNameOrderByIdDescEmailAsc("martin"));
        // INFO: 코드 가독성과 자유도 면에서 Sort를 사용하는 편이 뛰어나다.
        System.out.println("findFirstByNameWithSortParameter: " + memberRepository.findFirstByName(
                "martin", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))
        ));
        // FEAT: getSort() 메서드로 대체
        System.out.println("findFirstByNameWithSortParameter: " + memberRepository.findFirstByName(
                "martin", getSort()
        ));

    }
    private Sort getSort(){
        return Sort.by(
                Sort.Order.desc("id"),
                Sort.Order.asc("email"),
                Sort.Order.desc("createdAt"),
                Sort.Order.asc("updatedAt")
        );
    }

    @Test
    void page(){
        System.out.println("findByNameWithPaging: " + memberRepository.findByName(
                "martin", PageRequest.of(0, 1, Sort.by(Sort.Order.desc("id")))
        ).getContent());
    }

    @Test
    void insertAndUpdateTest(){
        Member member = Member.builder()
                .name("martin")
                .email("martin@gmail.com")
                .build();

        memberRepository.save(member);

        Member findMember = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        findMember.setName("marrrrrtin");
        memberRepository.save(findMember);
    }

    @Test
    void enumTest(){
        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        member.setGender(Gender.FEMALE);
        memberRepository.save(member);
        memberRepository.findAll().forEach(System.out::println);

        System.out.println(memberRepository.findAllRawRecord().get("gender"));
    }

    @Test
    void listenerTest(){
        Member member = Member.builder()
                .email("martin@gmail.com").name("martin").build();

        memberRepository.save(member);

        Member findMember = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        findMember.setName("marrrrrrrrrrrtin");
        memberRepository.save(findMember);
        memberRepository.deleteById(4L);
    }

    @Test
    void prePersistTest(){
        Member member = Member.builder()
                .email("martin2@gmail.com")
                .name("martin")
                .build();
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());

        memberRepository.save(member);

        System.out.println(memberRepository.findByEmail("martin2@gmail.com").orElseThrow(RuntimeException::new));
    }

    @Test
    void preUpdateTest(){
        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("as-is: : " + member);
        member.setName("martin22");
        memberRepository.save(member);
        System.out.println("to-be : " + memberRepository.findAll().get(0));
    }

    @Test
    void memberHistoryTest(){
        Member member = Member.builder()
                .name("martin-new")
                .email("martin-new@gmail.com")
                .build();
        memberRepository.save(member);
        member.setName("duckbill");
        memberRepository.save(member);

        memberHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void userRelationTest(){
        Member member = new Member();
        member.setName("david");
        member.setEmail("david@gmail.com");
        member.setGender(Gender.MALE);

        memberRepository.save(member);

        member.setName("daniel");
        memberRepository.save(member);

        member.setName("duckbill");
        memberRepository.save(member);

//        memberHistoryRepository.findAll().forEach(System.out::println);

        List<MemberHistory> result = memberRepository.findByEmail(
                "david@gmail.com"
        ).orElseThrow(RuntimeException::new).getMemberHistories();
        result.forEach(System.out::println);
    }

    @Test
    void embedTest(){
        memberRepository.findAll().forEach(System.out::println);

        Member member = new Member();
        member.setName("joy");
        member.setHomeAddress(new Address("서울시", "성동구", "성수1가", "023902"));
        member.setCompanyAddress(new Address("경기도", "남양주", "어딘가", "581912"));

        memberRepository.save(member);

        Member member1 = new Member();
        member1.setName("joshua");
        member1.setHomeAddress(null);
        member1.setCompanyAddress(null);

        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("jordan");
        member2.setHomeAddress(new Address());
        member2.setCompanyAddress(new Address());

        memberRepository.save(member2);

//        entityManager.clear();

        memberRepository.findAll().forEach(System.out::println);
        memberHistoryRepository.findAll().forEach(System.out::println);

        memberRepository.findAllRawRecord().forEach((s, o) -> System.out.println(o));

        // FEAT: 274번째 라인의 주석을 해제하면 에러가 발생한다. 영속성 캐시가 없어지면서 문제가 발생하게 되는 것이다.
        assertAll(
                () -> assertThat(memberRepository.findById(7L).get().getHomeAddress()).isNull(),
                () -> assertThat(memberRepository.findById(8L).get().getHomeAddress()).isInstanceOf(Address.class)
        );
    }
}