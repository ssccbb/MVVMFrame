package me.sung.base;

import android.content.Context;
import android.content.SharedPreferences;

import me.sung.base.utils.Log;

/**
 * Create by sung at 2020/8/28
 *
 * @desc: 共用包Api入口
 * @notice:
 */
public class CommonMoudleApi implements BaseWrapper {
    private Context mContext;

    private static class Holder {
        private static CommonMoudleApi holder = new CommonMoudleApi();
    }

    public static CommonMoudleApi getInstance() {
        return Holder.holder;
    }

    @Override
    public void attach(Context context) {
        this.mContext = context;
        Log.d(getAppPackageName()+"---->attach");
    }

    /**
     * @return 日志默认存储路径
     */
    @Override
    public String getLogPath() {
        return mContext.getExternalCacheDir() + "/app_base.log";
    }

    /**
     * @return context
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * @return 获取SP
     */
    @Override
    public SharedPreferences getSharedPreferences() {
        if (mContext == null) {
            throw new NullPointerException("Empty Context!");
        }
        return mContext.getSharedPreferences(Constants.DEFAULT_SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @return packagename
     */
    @Override
    public String getAppPackageName() {
        return Constants.TAG;
    }

}
