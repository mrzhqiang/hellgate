package hellgate.common.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdCardRepository extends JpaRepository<IdCard, Long> {

    Optional<IdCard> findByNumber(String number);
}
