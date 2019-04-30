/*
*JSONUtil.java
*Created on 2014-9-29 上午9:54 by Ivan
*Copyright(c)2014 Guangzhou Onion Information Technology Co., Ltd.
*http://www.cniao5.com
*/
package com.example.administrator.igoushop_app.tools;

import com.example.administrator.igoushop_app.pojos.Brand;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 14-9-29.
 * Copyright(c)2014 Guangzhou Onion Information Technology Co., Ltd.
 * http://www.cniao5.com
 */
public class JacksonUtils {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 将JSON字符串转化成List<Bean> 形式
     * @param json
     * @param list
     * @param bean
     * @param <T>
     * @return
     * @throws IOException
     */
    public static  <T> T toList(String json,Class<T> list,Class<?> bean) throws IOException {
        return mapper.readValue(json,getCollectionType(list,bean));
    }

    /*
    将JSON字符串 转化成Bean类
     */
    public <T> T toBean(String json,Class<T> bean) throws IOException {
        return mapper.readValue(json,bean);
    }


    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

}
