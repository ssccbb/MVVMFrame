package com.sung.net;

import android.content.Context;

/**
 * Create by sung at 2020/8/28
 *
 * @desc: 网络框架包Api入口
 * @notice:
 */
public class NetApi {
    private Context mContext;

    private static class Holder {
        private static NetApi holder = new NetApi();
    }

    public static NetApi getInstance() {
        return Holder.holder;
    }

    public void init(Context context){
        this.mContext = context;
    }
}
