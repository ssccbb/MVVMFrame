package me.sung.uikit;

import android.content.Context;
import android.content.SharedPreferences;

import com.tencent.smtt.sdk.QbSdk;

import me.sung.base.BaseWrapper;
import me.sung.base.utils.Log;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class UikitMoudleApi implements BaseWrapper {
    private Context mContext;

    private static class Holder {
        private static UikitMoudleApi holder = new UikitMoudleApi();
    }

    public static UikitMoudleApi getInstance() {
        return Holder.holder;
    }

    @Override
    public void attach(Context context) {
        this.mContext = context;
        X5WebInit();
        Log.d(getAppPackageName()+"---->attach");
    }

    private void X5WebInit(){
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                android.util.Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(mContext,  cb);
    }


    /**
     * @return 日志默认存储路径
     */
    @Override
    public String getLogPath() {
        return mContext.getExternalCacheDir() + "/app_uikit.log";
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
        return mContext.getSharedPreferences(me.sung.base.Constants.DEFAULT_SP_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @return packagename
     */
    @Override
    public String getAppPackageName() {
        return Constants.TAG;
    }

}
