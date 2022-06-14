package hellgate.common.util;

import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter.serializeAllExcept;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.util.ClassUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * JSON 工具。
 * <p>
 * 以 Spring Boot 默认采用的 Jackson 框架为主。
 */
public final class Jsons {
    private Jsons() {
        // no instances
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 排除敏感属性字段
     */
    private static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    static {
        OBJECT_MAPPER.registerModules(SecurityJackson2Modules.getModules(ClassUtils.getDefaultClassLoader()));
        OBJECT_MAPPER.setFilterProvider(new SimpleFilterProvider()
                .addFilter("fieldFilter", serializeAllExcept(EXCLUDE_PROPERTIES)));
    }

    private static String generateJson(Object o, boolean prettyPrint, boolean escapeNonAscii) {
        ObjectWriter writer = OBJECT_MAPPER.writer();
        if (prettyPrint) {
            writer.with(SerializationFeature.INDENT_OUTPUT);
        }
        if (escapeNonAscii) {
            writer.with(JsonWriteFeature.ESCAPE_NON_ASCII);
        }
        try {
            return writer.writeValueAsString(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static JsonNode toJson(Object data) {
        return OBJECT_MAPPER.valueToTree(data);
    }

    public static <A> A fromJson(JsonNode json, Class<A> clazz) {
        try {
            return OBJECT_MAPPER.treeToValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectNode newObject() {
        return OBJECT_MAPPER.createObjectNode();
    }

    public static ArrayNode newArray() {
        return OBJECT_MAPPER.createArrayNode();
    }

    public static String stringify(JsonNode json) {
        return generateJson(json, false, false);
    }

    public static String asciiStringify(JsonNode json) {
        return generateJson(json, false, true);
    }

    public static String prettyPrint(JsonNode json) {
        return generateJson(json, true, false);
    }

    public static JsonNode parse(String src) {
        try {
            return OBJECT_MAPPER.readTree(src);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Map<String, Object> toMap(T entity) {
        if (entity == null) {
            return Collections.emptyMap();
        }

        try {
            ObjectMapper objectMapper = OBJECT_MAPPER;
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            MapType mapType = typeFactory.constructMapType(HashMap.class, String.class, Object.class);
            String content = objectMapper.writeValueAsString(entity);
            return objectMapper.readValue(content, mapType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
