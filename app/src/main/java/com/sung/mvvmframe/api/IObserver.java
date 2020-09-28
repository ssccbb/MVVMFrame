package com.sung.mvvmframe.api;

import android.text.TextUtils;

import com.sung.mvvmframe.Router;
import com.sung.mvvmframe.config.Error;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.jessyan.net.NetMoudleApi;
import me.sung.base.bean.response.BaseResponse;
import me.sung.base.mgr.AppManager;
import me.sung.base.utils.JsonParser;
import me.sung.base.utils.Log;
import okhttp3.ResponseBody;

/**
 * Create by sung at 2020-04-22
 *
 * @desc: Retrofit Observer 封装
 * @notice:
 */
public abstract class IObserver<T extends BaseResponse> implements Observer<ResponseBody> {
    private Class clazz;
    private String log;

    public IObserver(Class clazz) {
        this.clazz = clazz;
        this.log = NetMoudleApi.getInstance().getAppPackageName();
    }

    @Override
    public void onSubscribe(Disposable d) {
        Log.d(log, "onSubscribe()");
    }

    @Override
    public void onNext(ResponseBody response) {
        try {
            String json = response.string();
            Log.d(log, "onNext() response --> " + json);
            if (TextUtils.isEmpty(json)) {
                onError(Error.ResponseException.getCode(), new NullPointerException(Error.ResponseException.getMessage()));
                return;
            }
            // TODO: 2020/9/28(add by sung) 以下代码根据业务修改对应逻辑
            // ----------------------------------------------
            T t = (T) JsonParser.gson.fromJson(json, clazz);
            // 反馈码
            int code = t.getCode();
            if (code == 0) {
                // 数据返回成功
                onNext(code, t);
            } else if (code == 1000) {
                // 需要重新登陆
                // TODO: 2020/9/28(add by sung) ReLogin Error
                Router.goLoginActivity(AppManager.getAppManager().currentActivity());
                return;
            } else {
                // 数据返回错误
                onError(t.getCode(), new Exception(t.getMessage()));
            }
            // ----------------------------------------------
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(log, "onNext() exception --> " + e.getMessage());
            onError(Error.ParserException.getCode(), e);
        }
    }

    @Override
    public void onComplete() {
        Log.d(log, "onComplete()");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(log, "onError() --> " + e.getMessage());
        onError(Error.ServiceException.getCode(), new Exception(e.getMessage()));
    }

    public abstract void onNext(int code, T t);

    public abstract void onError(int code, Exception e);
}
