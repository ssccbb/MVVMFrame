package com.sung.uikit;

import android.content.Context;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Create by sung at 2020/8/28
 *
 * @desc:
 * @notice:
 */
public class UikitApi {
    private Context mContext;

    private static class Holder {
        private static UikitApi holder = new UikitApi();
    }

    public static UikitApi getInstance() {
        return Holder.holder;
    }

    public void init(Context context) throws Exception {
        if (context == null) {
            throw new Exception("context can not bean null!");
        }
        this.mContext = context;
        X5WebInit();
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

    public Context getContext() {
        return mContext;
    }
}
