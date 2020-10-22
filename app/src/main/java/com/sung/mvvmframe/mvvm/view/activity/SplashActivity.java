package com.sung.mvvmframe.mvvm.view.activity;

import android.view.KeyEvent;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import me.sung.base.Constants;
import me.sung.base.bean.config.ActionBarConfig;
import me.sung.base.cache.MCache;
import me.sung.base.utils.Log;

import com.sung.mvvmframe.R;
import com.sung.mvvmframe.Router;
import com.sung.mvvmframe.SplashActivityBinder;
import com.sung.mvvmframe.base.BaseActivity;
import com.sung.mvvmframe.mvvm.viewmodel.BaseViewModel;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends BaseActivity<BaseViewModel, SplashActivityBinder> implements View.OnClickListener {
    private CompositeDisposable mSkipComposite = new CompositeDisposable();
    private int mSkipTime = Constants.CONFIG_SPLASH_SKIP_TIME;

    private DisposableObserver mObserver = new DisposableObserver<Long>() {
        @Override
        public void onNext(Long timeStamp) {
            Log.d("onNext:" + timeStamp + "s");
            mSkipTime--;
            if (mSkipTime < 0) {
                mSkipTime = 0;
            }
            mBinder.setTips(String.format(getString(R.string.sys_skip_mills_hint), mSkipTime));
        }

        @Override
        public void onError(Throwable e) {
            Log.d("onError!");
            doNext();
        }

        @Override
        public void onComplete() {
            Log.d("onComplete.");
            doNext();
        }
    };

    @Override
    protected void set() {
        mBinder.setListener(this);
        mBinder.setTips(String.format(getString(R.string.sys_skip_mills_hint), mSkipTime));
        mSkipComposite.add(
                // 开始次数 轮询次数 首次延时 间隔时长 时间单位
                Observable.intervalRange(0, Constants.CONFIG_SPLASH_SKIP_TIME, 1, 1, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(mObserver));
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    protected ActionBarConfig getActionbarConfig() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSkipComposite != null && !mSkipComposite.isDisposed()) {
            mSkipComposite.dispose();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPress() {
    }

    @Override
    public void onClick(View v) {
        doNext();
    }

    private void doNext() {
        boolean hasGuideShown = MCache.isGuideShown();
        if (hasGuideShown) {
            Router.goIndexActivity(getContext());
        } else {
            Router.goGuideActivity(getContext());
        }
        finish();
    }
}