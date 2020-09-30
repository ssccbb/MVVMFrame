package me.sung.base;

import android.content.Context;

import me.sung.base.cache.MCache;
import me.sung.base.mgr.AccountManager;
import me.sung.base.utils.Log;
import me.sung.base.utils.SPUtils;

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

        initialize();
    }

    private void initialize(){
        MCache.setGuideShown(false);
        MCache.setNightMode(false);
        SPUtils.init(mContext);
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
     * @return packagename
     */
    @Override
    public String getAppPackageName() {
        return Constants.TAG;
    }

}
