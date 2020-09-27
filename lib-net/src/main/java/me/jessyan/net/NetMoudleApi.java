package me.jessyan.net;

import android.content.Context;
import android.content.SharedPreferences;

import me.sung.base.BaseWrapper;
import me.sung.base.Constants;
import me.sung.base.utils.Log;

/**
 * Create by sung at 2020/9/27
 *
 * @desc:
 * @notice:
 */
public class NetMoudleApi implements BaseWrapper {
    private Context mContext;

    private static class Holder {
        private static NetMoudleApi holder = new NetMoudleApi();
    }

    public static NetMoudleApi getInstance() {
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
        return mContext.getExternalCacheDir() + "/app_net.log";
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
        return "me.jessyan.netmanager";
    }
}
