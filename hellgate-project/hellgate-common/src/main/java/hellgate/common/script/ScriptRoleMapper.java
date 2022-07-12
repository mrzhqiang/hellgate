package hellgate.common.script;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScriptRoleMapper {

    ScriptRoleData toData(ScriptRole entity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "strRatio", ignore = true)
    @Mapping(target = "script", ignore = true)
    @Mapping(target = "roleSkills", ignore = true)
    @Mapping(target = "mpRatio", ignore = true)
    @Mapping(target = "lastModifiedBy", ignore = true)
    @Mapping(target = "lastModified", ignore = true)
    @Mapping(target = "labels", ignore = true)
    @Mapping(target = "intRatio", ignore = true)
    @Mapping(target = "initSTR", ignore = true)
    @Mapping(target = "initMp", ignore = true)
    @Mapping(target = "initINT", ignore = true)
    @Mapping(target = "initHp", ignore = true)
    @Mapping(target = "initDEX", ignore = true)
    @Mapping(target = "initCON", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hpRatio", ignore = true)
    @Mapping(target = "growMp", ignore = true)
    @Mapping(target = "growHp", ignore = true)
    @Mapping(target = "dexRatio", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "conRatio", ignore = true)
    ScriptRole toEntity(ScriptRoleForm form);
}
