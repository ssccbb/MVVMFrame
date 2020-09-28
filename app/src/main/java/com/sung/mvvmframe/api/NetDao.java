package com.sung.mvvmframe.api;

import android.content.Context;

import me.sung.base.bean.response.TopNewsResponse;

/**
 * Create by sung at 2020/9/28
 *
 * @desc: 接口请求公共类
 * @notice: 统一入口
 */
public class NetDao {
    private static Context mContext;

    private static class Holder {
        private static NetDao holder = new NetDao();
    }

    public static NetDao getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return Holder.holder;
    }

    public void getTopNews(IObserver<TopNewsResponse> observer){
        NetWorkManager.getInstance(mContext)
                // 获取指定的Api
                .getNewsApiService()
                // 访问接口
                .getTopNews("top","735174c341ded04b80ce9bba51c845e8")
                // Transformer订阅（默认主线程）
                .compose(NetWorkManager.getDefaultTransformer())
                // 回调
                .subscribe(observer);
    }
}
