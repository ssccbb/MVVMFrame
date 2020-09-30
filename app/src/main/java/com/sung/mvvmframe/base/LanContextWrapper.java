package com.sung.mvvmframe.base;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;

import me.sung.base.Constants;
import me.sung.base.cache.MCache;

import java.util.Locale;

/**
 * Create by sung at 2020-04-04
 *
 * @desc: 自定义contextwrapper
 * @notice: 国际化
 */
public class LanContextWrapper extends ContextWrapper {

    public static final String LANG_HK = "hk";
    public static final String LANG_CN = "cn";
    public static final String LANG_EN = "en";
    public static final String LANG_JP = "jp";
    public static final String LANG_KO = "ko";

    public LanContextWrapper(Context ctx) {
        super(ctx);
    }

    public static ContextWrapper wrap(Context context) {
        Locale newLocale;
        SharedPreferences sharedPreferences = context.getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        String lanTag = sharedPreferences.getString(Constants.CONFIG_LANGUAGE, "en");
        switch (lanTag) {
            //没有手动设置过对应的语言则默认读取手机系统的语言
            case "def":
                String locale = Locale.getDefault().getLanguage();
                String langFlag = "";
                if (TextUtils.isEmpty(locale)) {
                    langFlag = LANG_CN;
                    newLocale = Locale.CHINESE;
                } else if (locale.contains("en")) {
                    langFlag = LANG_EN;
                    newLocale = Locale.ENGLISH;
                } else if (locale.contains("ko")){
                    langFlag = LANG_KO;
                    newLocale = Locale.KOREA;
                }else if (locale.startsWith("zh")) {
                    String region = Locale.getDefault().getDisplayCountry();
                    if (region.equals("香港特別行政區") || region.equals("台灣")) {
                        langFlag = LANG_HK;
                        newLocale = Locale.TRADITIONAL_CHINESE;
                    } else {
                        langFlag = LANG_CN;
                        newLocale = Locale.SIMPLIFIED_CHINESE;
                    }
                } else {
                    newLocale = Locale.CHINESE;
                }
                sharedPreferences.edit().putString(Constants.CONFIG_LANGUAGE, langFlag).apply();
                break;
            //设置为英语
            case LANG_EN:
                newLocale = Locale.ENGLISH;
                break;
            //设置为繁體
            case LANG_HK:
                newLocale = Locale.TRADITIONAL_CHINESE;
                break;
            //设置为日本语
            case LANG_JP:
                newLocale = Locale.JAPANESE;
                break;
            case LANG_CN:
                newLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case LANG_KO:
                newLocale = Locale.KOREA;
                break;
            //默认为英语
            default:
                newLocale = Locale.ENGLISH;
                break;
        }
        context = getLanContext(context, newLocale);
        return new ContextWrapper(context);
    }

    /**
     * 初始化Context
     *
     * @param context
     * @param pNewLocale
     * @return
     */
    private static Context getLanContext(Context context, Locale pNewLocale) {
        //1、获取Resources
        Resources res = context.getApplicationContext().getResources();
        //2、获取Configuration
        Configuration configuration = res.getConfiguration();
        //3、设置Locale并初始化Context
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(pNewLocale);
            LocaleList localeList = new LocaleList(pNewLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context = context.createConfigurationContext(configuration);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            configuration.setLocale(pNewLocale);
            context = context.createConfigurationContext(configuration);
        }
        return context;
    }


    /**
     * 获取手机设置的语言国家
     *
     * @param context
     * @return
     */
    public static String getCountry(Context context) {
        String country;
        Resources resources = context.getApplicationContext().getResources();
        //在7.0以上和7.0一下获取国家的方式有点不一样
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            country = resources.getConfiguration().getLocales().get(0).getCountry();
        } else {
            country = resources.getConfiguration().locale.getCountry();
        }
        return country;
    }

    public static String getLangTag(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MCache.DEFAULT_SP_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(Constants.CONFIG_LANGUAGE, "en");
    }
}