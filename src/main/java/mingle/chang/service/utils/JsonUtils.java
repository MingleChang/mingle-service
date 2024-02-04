package mingle.chang.service.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.micrometer.common.util.StringUtils;

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> String toJson(T object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writer().writeValueAsString(object);
            return json;
        }catch (Exception e) {
            return null;
        }
    }

    public static <T> T parse(String json, Class<T> clazz) {
        return parse(json, clazz, null);
    }
    public static <T> T parse(String json, TypeReference typeReference) {
        return parse(json, null, typeReference);
    }
    private static <T> T parse(String json, Class<T> clazz, TypeReference typeReference) {
        T obj = null;
        if (!StringUtils.isEmpty(json)) {
            try {
                if (clazz != null) {
                    obj = objectMapper.readValue(json, clazz);
                }else {
                    obj = (T) objectMapper.readValue(json, typeReference);
                }
                return obj;
            }catch (Exception e) {
                return null;
            }
        }else {
            return null;
        }
    }
}
