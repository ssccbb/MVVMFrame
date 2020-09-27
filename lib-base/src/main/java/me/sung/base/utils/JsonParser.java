package me.sung.base.utils;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Create by sung at 2020-04-19
 *
 * @desc: json转换
 * @notice:
 */
public class JsonParser {
    public static Gson gson = new Gson();

    /**
     * 多个value转换成JsonObject
     *
     * @param values key:value形式
     */
    public static JSONObject parserToJSONObject(String... values) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (values.length <= 0) {
                return jsonObject;
            }
            for (int pos = 0; pos < values.length; pos++) {
                String value = values[pos];
                String[] split = value.split(":");
                if (split.length != 2) {
                    continue;
                }
                jsonObject.put(split[0], split[1]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 多个value转换成json字符串
     *
     * @param values
     */
    public static String parserToJson(String... values) {
        JSONObject jsonObject = parserToJSONObject(values);
        if (jsonObject != null) {
            return jsonObject.toString();
        }
        return "{}";
    }

    public static String parserToJson(Object obj){
        return gson.toJson(obj);
    }

}
