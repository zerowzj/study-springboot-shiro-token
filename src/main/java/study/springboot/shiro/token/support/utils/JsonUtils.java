package study.springboot.shiro.token.support.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtils {

    public static String toJson(Object obj) {
        String text = "";
        if (obj != null) {
            text = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
        }
        return text;
    }

    public static <T> T fromJson(String text, Class<T> clazz) {
        T t = JSON.parseObject(text, clazz);
        return t;
    }
}
