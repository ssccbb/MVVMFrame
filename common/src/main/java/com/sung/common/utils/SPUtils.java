package com.sung.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sung.common.Constants;

import java.util.Map;

/**
 * Create by sung at 2020-04-20
 *
 * @desc: sp
 * @notice:
 */
public class SPUtils {
    private static Context context;

    public static void init(Context ctx) {
        context = ctx;
    }

    public synchronized static SharedPreferences get(String name) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public synchronized static boolean clear(String name) {
        SharedPreferences sp = get(name);
        if (sp == null) {
            return false;
        }
        return sp.edit().clear().commit();
    }

    public static void put(String key, Object object) {
        if (object == null) {
            return;
        }
        synchronized (SPUtils.class) {
            if (context == null) {
                return;
            }
            SharedPreferences sp = context.getSharedPreferences(Constants.DEFAULT_SP_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();

            if (object instanceof String) {
                editor.putString(key, (String) object);
            } else if (object instanceof Integer) {
                editor.putInt(key, (Integer) object);
            } else if (object instanceof Boolean) {
                editor.putBoolean(key, (Boolean) object);
            } else if (object instanceof Float) {
                editor.putFloat(key, (Float) object);
            } else if (object instanceof Long) {
                editor.putLong(key, (Long) object);
            } else {
                editor.putString(key, object.toString());
            }
            editor.commit();
        }
    }

    public static <T extends Object> T getValue(String key, T defaultValue) {
        synchronized (SPUtils.class) {
            if (context == null) {
                return defaultValue;
            }
            SharedPreferences preferences = context.getSharedPreferences(Constants.DEFAULT_SP_NAME, Context.MODE_PRIVATE);
            Map<String, T> map = (Map<String, T>) preferences.getAll();
            T value = map.get(key);
            return value != null ? value : defaultValue;
        }
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        if (context == null) {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(Constants.DEFAULT_SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
