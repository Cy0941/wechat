package cn.cxy.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by lenovo on 2016/10/26.
 */
public class JsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    //设置在输入时忽略JSON字符串中存在但Java对象实际没有的属性
    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
    }

    /**
     * Json字符串反序列话为对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String json,Class<T> clazz){
        Object object = null;
        try {
            object = mapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T)object;
    }

    /**
     * 对象序列化为JSON字符串
     * @param object
     * @return
     */
    public static String serialize(Object object){
            StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer,object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

}
