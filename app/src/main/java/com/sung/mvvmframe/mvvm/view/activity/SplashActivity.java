package com.sung.mvvmframe.mvvm.view.activity;

import me.sung.base.bean.config.ToolbarConfig;
import me.sung.base.bean.response.TopNewsResponse;
import me.sung.base.utils.Log;

import com.sung.mvvmframe.R;
import com.sung.mvvmframe.api.IObserver;
import com.sung.mvvmframe.api.NetDao;
import com.sung.mvvmframe.base.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void set() {
        requestData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected ToolbarConfig getToolbarConfig() {
        return null;
    }

    private void requestData() {
        NetDao.getInstance(this).getTopNews(new IObserver<TopNewsResponse>(TopNewsResponse.class) {
            @Override
            public void onNext(int code, TopNewsResponse topNewsResponse) {
                for (TopNewsResponse.ResultBean.DataBean bean : topNewsResponse.result.data) {
                    Log.d(bean.title);
                }
            }

            @Override
            public void onError(int code, Exception e) {
                Log.d("request data succ!" + e.getMessage());
            }
        });
    }
}