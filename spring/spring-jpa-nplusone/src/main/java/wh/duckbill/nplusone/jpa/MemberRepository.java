package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wh.duckbill.nplusone.lazy.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // fetch join
    @Query(value = "select m from Member m join fetch m.orders")
    List<Member> findAllWithOrder();

    // EntityGraph
    @EntityGraph(attributePaths = {"orders"})
    @Query(value = "select m from Member m")
    List<Member> findAllEntityGraph();
}
