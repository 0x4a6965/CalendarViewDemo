package com.jie.calendarviewdemo;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {

	private static Gson sGson = new Gson();

	public static String beanToJson(Object o) {
		try {
			return sGson.toJson(o);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T jsonToBean(String json, Class<T> tClass) {
		try {
			return sGson.fromJson(json, tClass);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

	public static Object jsonToBean(String json, Type type) {
		try {
			return sGson.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

	public static <T> T jsonToBean(String json, TypeToken<T> typeToken) {
		try {
			return sGson.fromJson(json, typeToken.getType());
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

	public static <T> List<T> jsonToList(String json, TypeToken<List<T>> typeToken) {
		try {
			return sGson.fromJson(json, typeToken.getType());
		} catch (JsonSyntaxException e) {
			return null;
		}
	}

	public static JSONObject strToJsonObj(String str) throws JSONException {
		if (null == str || str.indexOf("{") != 0 || str.lastIndexOf("}") != str.length() - 1)
			return null;
		return new JSONObject(str);
	}

	public static JSONArray strToJsonArray(String str) throws JSONException {
		if (null == str || str.indexOf("{") != 0 || str.lastIndexOf("}") != str.length() - 1)
			return null;
		return new JSONArray(str);
	}
}
