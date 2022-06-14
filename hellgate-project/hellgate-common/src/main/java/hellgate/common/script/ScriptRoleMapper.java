package hellgate.common.script;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScriptRoleMapper {

    ScriptRoleData toData(ScriptRole entity);

    ScriptRole toEntity(ScriptRoleForm form);
}
