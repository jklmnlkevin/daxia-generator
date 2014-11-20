package com.daxia.generator.util;

import java.util.Collection;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.PropertyFilter;

public class JsonArrayUtils {
	
	public static String toJsonArray(Collection<? extends Object> coll, PropertyFilter propertyFilter) {
		return JSONArray.toJSONString(coll, propertyFilter);
	}
}
