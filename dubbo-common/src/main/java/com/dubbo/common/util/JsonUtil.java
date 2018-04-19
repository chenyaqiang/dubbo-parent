package com.dubbo.common.util;

import com.dubbo.common.exception.SysRunException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:58
 * @version:
 **/
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public static ObjectMapper getInstance() {
        return objectMapper;
    }

    /**
     * 使用Jackson 数据绑定 将对象转换为 json字符串 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("转换为json字符串失败" + e.toString());
        }
        return null;
    }

    /**
     * json字符串转化为 JavaBean 还可以直接JsonUtils.getInstance().readValue(String content,Class valueType)用这种方式
     *
     * @param <T>
     * @param content
     * @param valueType
     * @return
     */
    public static <T> T toBean(String content, Class<T> valueType) {
        try {
            objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            logger.error("转换为json字符串失败", e);
        }
        return null;
    }

    /**
     * json字符串转化为list 还可以 直接使用 JsonUtils.getInstance().readValue(String content, new TypeReference<List<T>>(){})方式
     *
     * @param <T>
     * @param content
     * @param typeReference
     * @return
     * @throws IOException
     */
    public static <T> List<T> toList(String content, TypeReference<List<T>> typeReference) throws IOException {
        try {
            return objectMapper.readValue(content, typeReference);
        } catch (JsonParseException e) {
            logger.error("json字符串转化为 list失败", e);
            throw new SysRunException("json字符串转化为 list失败");
        } catch (JsonMappingException e) {
            logger.error("json字符串转化为 list失败", e);
            throw new JsonMappingException("json字符串转化为 list失败");
        } catch (IOException e) {
            logger.error("json字符串转化为 list失败", e);
            throw new IOException("json字符串转化为 list失败");
        }
    }

    /**
     * json字符串转化为list 还可以 直接使用 JsonUtils.getInstance().readValue(String content, new TypeReference<List<T>>(){})方式
     *
     * @param <T>
     * @param content
     * @param typeReference
     * @return
     * @throws IOException
     */
    public static <T> Map<String, T> toMap(String content, TypeReference<Map<String, T>> typeReference)
            throws IOException {
        try {
            objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
            return objectMapper.readValue(content, typeReference);
        } catch (JsonParseException e) {
            logger.error("json字符串转化为 map失败", e);
            throw new SysRunException("json字符串转化为 map失败");
        } catch (JsonMappingException e) {
            logger.error("json字符串转化为 map失败", e);
            throw new JsonMappingException("json字符串转化为 map失败");
        } catch (IOException e) {
            logger.error("json字符串转化为 map失败", e);
            throw new IOException("json字符串转化为 map失败");
        }
    }
}
