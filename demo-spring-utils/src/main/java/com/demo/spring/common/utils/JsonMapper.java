package com.demo.spring.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * 简单封装Jackson，实现JSON String<->Java Object的Mapper.
 * 封装不同的输出风格, 使用不同的builder函数创建实例.
 */
public class JsonMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	private static Logger log = LoggerFactory.getLogger(JsonMapper.class);

	private static JsonMapper mapper;

	public JsonMapper() {
	}

	/**
	 * 创建只输出非Null且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口中使用.
	 */



	public static JsonMapper getInstance() {
		if (mapper == null){
			mapper = new JsonMapper().enableSimple();

		}
		return mapper;
	}

	/**
	 * Object可以是POJO，也可以是Collection或数组。
	 * 如果对象为Null, 返回"null".
	 * 如果集合为空集合, 返回"[]".
	 */
	public String toJson(Object object) {
		try {
			return this.writeValueAsString(object);
		} catch (IOException e) {
			log.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 反序列化POJO或简单Collection如List<String>.
	 *
	 * 如果JSON字符串为Null或"null"字符串, 返回Null.
	 * 如果JSON字符串为"[]", 返回空集合.
	 *
	 * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String,JavaType)
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) throws Exception {
			return this.readValue(jsonString, clazz);
	}

	/**
	 * 設定是否使用Enum的toString函數來讀寫Enum,
	 * 為False時時使用Enum的name()函數來讀寫Enum, 默認為False.
	 * 注意本函數一定要在Mapper創建後, 所有的讀寫動作之前調用.
	 */
	public JsonMapper enableEnumUseToString() {
		this.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
		this.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
		return this;
	}

	/**
	 * 允许单引号
	 * 允许不带引号的字段名称
	 */
	public JsonMapper enableSimple() {
		this.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		this.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		return this;
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return this;
	}

	/**
	 * 对象转换为JSON字符串
	 * @param object
	 * @return
	 */
	public static String toJsonString(Object object){
		return JsonMapper.getInstance().toJson(object);
	}

	/**
	 * JSON字符串转换为对象
	 * @param jsonString
	 * @param clazz
	 * @return
	 */
	public static <T>  T fromJsonString(String jsonString, Class<T> clazz) throws Exception {
		return JsonMapper.getInstance().fromJson(jsonString, clazz);
	}


	public static <T> List<T> fromJsonStringToList(String jsonString, Class<T> clazz){
		return JSONObject.parseArray(jsonString,clazz);
	}

}
