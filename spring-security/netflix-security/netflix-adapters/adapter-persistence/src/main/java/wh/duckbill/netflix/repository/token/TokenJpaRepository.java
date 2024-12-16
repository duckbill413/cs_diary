package wh.duckbill.netflix.repository.token;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.token.TokenEntity;

public interface TokenJpaRepository extends JpaRepository<TokenEntity, String>, TokenCustomRepository {
}
