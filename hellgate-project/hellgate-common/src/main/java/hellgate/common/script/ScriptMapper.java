package hellgate.common.script;

import com.google.common.base.Strings;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ScriptMapper {

    @Mapping(target = "name", source = "entity")
    ScriptData toData(Script entity);

    default String nameToString(Script entity) {
        String name = entity.getName();
        String label = entity.getLabel();
        if (Strings.isNullOrEmpty(label)) {
            String description = entity.getType().getDescription();
            return Strings.lenientFormat(ScriptData.LABEL_TEMPLATE, name, description);
        }
        return Strings.lenientFormat(ScriptData.LABEL_TEMPLATE, name, label);
    }
}
