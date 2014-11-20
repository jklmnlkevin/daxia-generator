package com.daxia.generator.util;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

@SuppressWarnings("deprecation")
public class JsonUtils {

	private static final JSONSerializerMap mapping;
	
	static {
		mapping = new JSONSerializerMap();
		// 使用和json-lib兼容的日期输出格式
		mapping.put(Date.class, new JSONLibDataFormatSerializer()); 
	}
	
	
	private static final SerializerFeature[] features = { SerializerFeature.WriteMapNullValue, // 输出空置字段
        SerializerFeature.WriteNullListAsEmpty, // list字段如果为null，输出为[]，而不是null
        // SerializerFeature.writenulln, // 数值字段如果为null，输出为0，而不是null
        SerializerFeature.WriteNullBooleanAsFalse, // Boolean字段如果为null，输出为false，而不是null
        SerializerFeature.WriteNullStringAsEmpty // 字符类型字段如果为null，输出为""，而不是null
        };
	
	// 序列化和JSON-LIB兼容的字符串
	public static String toJson(Object object) {
		return JSON.toJSONString(object,mapping,features);
	}
	
	// 序列化和JSON-LIB兼容的字符串
	public static String toJson(Object object,PropertyFilter filter) {
		return JSON.toJSONString(object,filter,features);
	}
	
	public static PropertyFilter setFiler(final String[] fileds) {
		return new PropertyFilter() {
			@Override
			public boolean apply(Object arg0, String name, Object arg2) {
				for(int i = 0; i < fileds.length; i++) {
					if(fileds[i].equals(name)) {
						return false;
					}
				}
				return true;
			}
		};
	}
}
