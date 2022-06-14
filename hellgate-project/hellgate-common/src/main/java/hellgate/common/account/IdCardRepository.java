package hellgate.common.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 身份证仓库。
 */
@Repository
public interface IdCardRepository extends JpaRepository<IdCard, Long> {

    /**
     * 通过身份证号码找到身份证。
     *
     * @param number 身份证号码。
     * @return 可选的身份证实体。
     */
    Optional<IdCard> findByNumber(String number);
}
