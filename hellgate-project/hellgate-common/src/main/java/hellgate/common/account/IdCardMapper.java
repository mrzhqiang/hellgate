package hellgate.common.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IdCardMapper {

    IdCardData toData(IdCard card);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    @Mapping(target = "holders", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "created", ignore = true)
    IdCard toEntity(IdCardForm form);
}
