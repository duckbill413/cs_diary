package com.example.jparepository.repository;

import com.example.jparepository.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
    List<Member> findByCreatedAtAfter(LocalDateTime yesterday); // >
    List<Member> findByCreatedAtGreaterThan(LocalDateTime yesterday); // >
    List<Member> findByCreatedAtGreaterThanEqual(LocalDateTime yesterday); // >=
    List<Member> findByIdIsNotNull();
    List<Member> findByAddressIsNotEmpty();
    List<Member> findByNameIn(List<String> name);
    List<Member> findByNameContaining(String name);
    List<Member> findByNameStartingWith(String name);
    List<Member> findByNameEndingWith(String name);
    List<Member> findByNameLike(String name);
    Optional<Member> findByEmail(String email);

    // FEAT: Order
    List<Member> findTop1ByName(String name);
    List<Member> findTop1ByNameOrderByIdDesc(String name);
    List<Member> findFirstByNameOrderByIdDescEmailAsc(String name);
    List<Member> findFirstByName(String name, Sort sort); // import org.springframework.data.domain.Sort;

    // FEAT: Paging
    Page<Member> findByName(String name, Pageable pageable);

    // FEAT: Enum check
    @Query(value = "select * from member limit 1;", nativeQuery = true)
    Map<String, Object> findRawRecord();
}
