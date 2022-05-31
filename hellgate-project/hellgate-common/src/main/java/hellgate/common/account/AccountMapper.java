package hellgate.common.account;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 账号映射器。
 * <p>
 * 一般是将实体映射为数据，或将表单映射为实体。
 */
@Mapper
public interface AccountMapper {

    /**
     * 通过单例进行映射。
     */
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountData toData(Account account);

    Account toEntity(RegisterForm registerForm);
}
