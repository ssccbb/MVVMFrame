package com.sung.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by sung at 2020/8/28
 *
 * @desc: 共用包Api入口
 * @notice:
 */
public class CommonApi {
    private Context mContext;

    private static class Holder {
        private static CommonApi holder = new CommonApi();
    }

    public static CommonApi getInstance() {
        return Holder.holder;
    }

    public void init(Context context){
        this.mContext = context;
    }

    /**
     * @return 日志默认存储路径
     */
    public String getLogPath() {
        return mContext.getExternalCacheDir() + "/app.log";
    }

    /**
     * @return context
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * @return packagename
     */
    public String getAppPackageName() {
        return mContext.getPackageName();
    }

}
