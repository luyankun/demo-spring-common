package com.demo.spring.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @author lagon
 * @time 2017/1/10 20:00
 * @description 基于XStream框架工具类
 */
@SuppressWarnings("unchecked")
public class XStreamUtil {

    /**
     * 对象转成xml字符串
     *
     * @throws Exception
     */
    public static <T> String toXml(T object) {
        XStream xStream = new XStream(new DomDriver("utf-8"));
        xStream.processAnnotations(object.getClass());
        return xStream.toXML(object);
    }

    /**
     * xml字符串转成对象
     *
     * @throws Exception
     */
    public static <T> T fromXml(String xmlStr, Class<T> clazz) {
        XStream xStream = new XStream(new DomDriver("utf-8")) {
            @Override
            protected MapperWrapper wrapMapper(MapperWrapper next) {
                return new MapperWrapper(next) {
                    @Override
                    public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                        if (definedIn == Object.class) {
                            return false;
                        }
                        return super.shouldSerializeMember(definedIn, fieldName);
                    }
                };
            }
        };
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xmlStr);
    }
}
