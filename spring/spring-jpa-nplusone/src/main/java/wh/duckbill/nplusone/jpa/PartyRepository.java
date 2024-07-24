package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.eager.Party;

public interface PartyRepository extends JpaRepository<Party, Long> {
}
