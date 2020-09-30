package me.jessyan.net;

import android.content.Context;
import me.sung.base.BaseWrapper;
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
     * @return packagename
     */
    @Override
    public String getAppPackageName() {
        return "me.jessyan.net";
    }
}
