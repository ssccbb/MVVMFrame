package com.sung.common.utils;

import android.os.Environment;

import com.sung.common.BuildConfig;
import com.sung.common.CommonApi;
import com.sung.common.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Create by sung at 2018/11/13
 *
 * @Description: log工具
 */
public class Log {
    /**
     * 日志开关
     */
    private static boolean LOG_INTERCEPT = (!BuildConfig.DEBUG);
    /**
     * 记录交互
     */
    private static boolean SAVE_USER_OPERATION = true;
    private static DateFormat dateFormat;

    /**
     * logd
     *
     * @param strings 数值>1 首位tag 其余content
     */
    public static void d(String... strings) {
        if (isIntercept() || strings.length <= 0) {
            return;
        }
        logLongString("d", strings.length > 1 ? strings[0] : Constants.TAG, readContent(strings));
    }

    /**
     * logv
     *
     * @param strings 数值>1 首位tag 其余content
     */
    public static void v(String... strings) {
        if (isIntercept() || strings.length <= 0) {
            return;
        }
        logLongString("v", strings.length > 1 ? strings[0] : Constants.TAG, readContent(strings));
    }

    /**
     * loge
     *
     * @param strings 数值>1 首位tag 其余content
     */
    public static void e(String... strings) {
        if (isIntercept() || strings.length <= 0) {
            return;
        }
        logLongString("e", strings.length > 1 ? strings[0] : Constants.TAG, readContent(strings));
    }

    /**
     * logi
     *
     * @param strings 数值>1 首位tag 其余content
     */
    public static void i(String... strings) {
        if (isIntercept() || strings.length <= 0) {
            return;
        }
        logLongString("i", strings.length > 1 ? strings[0] : Constants.TAG, readContent(strings));
    }

    /**
     * 打印长文本
     *
     * @param method
     * @param strings
     */
    private static void logLongString(String method, String... strings) {
        try {
            String tag = strings.length > 1 ? strings[0] : Constants.TAG;
            String content = readContent(strings);
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (content.length() > max_str_length) {
                if (method.equals("d")) {
                    android.util.Log.d(tag, content.substring(0, max_str_length));
                } else if (method.equals("v")) {
                    android.util.Log.v(tag, content.substring(0, max_str_length));
                } else if (method.equals("e")) {
                    android.util.Log.e(tag, content.substring(0, max_str_length));
                } else if (method.equals("i")) {
                    android.util.Log.i(tag, content.substring(0, max_str_length));
                }
                content = content.substring(max_str_length);
            }
            //剩余部分
            if (method.equals("d")) {
                android.util.Log.d(tag, content);
            } else if (method.equals("v")) {
                android.util.Log.v(tag, content);
            } else if (method.equals("e")) {
                android.util.Log.e(tag, content);
            } else if (method.equals("i")) {
                android.util.Log.i(tag, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isIntercept() {
        return LOG_INTERCEPT;
    }

    private static String readContent(String... strings) {
        int length = strings.length;
        String content = "";
        if (length == 1) {
            content = strings[0];
        }
        if (length == 2) {
            content = strings[1];
        }
        if (length > 2) {
            StringBuilder sb = new StringBuilder();
            for (String string : strings) {
                sb.append("\t" + string);
            }
            content = sb.toString();
        }
        return content;
    }

    /**
     * @date: 2018/12/6_5:04 PM
     * @Description: 日志记录
     */
    public static void saveOperationIntoLocal(String content) {
        if (!SAVE_USER_OPERATION) return;
        try {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            String logPath = CommonApi.getInstance().getLogPath();
            if (!FileUtils.isFileExists(logPath)) {
                FileUtils.createFileByDeleteOldFile(logPath);
            }
            content = "\r\n" + millis2String(System.currentTimeMillis()) + " : " + content + "\r\n";
            FileUtils.writeFileFromString(logPath, content, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String millis2String(long millis) {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }
        return dateFormat.format(new Date(millis));
    }
}
