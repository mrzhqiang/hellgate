package hellgate.common.model.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityCardRepository extends JpaRepository<IdentityCard, Long> {

    Optional<IdentityCard> findByNumber(String number);
}
