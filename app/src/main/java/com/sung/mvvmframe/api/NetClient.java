package com.sung.mvvmframe.api;

import android.content.Context;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.net.RetrofitUrlManager;
import me.jessyan.net.interceptor.AddCookiesInterceptor;
import me.jessyan.net.interceptor.BaseInterceptor;
import me.jessyan.net.interceptor.ReceivedCookiesInterceptor;
import okhttp3.ConnectionPool;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.sung.mvvmframe.api.Api.DEFAULT_DOMAIN;

/**
 * Create by sung at 2020/9/25
 *
 * @desc:
 * @notice:
 */
public class NetClient {
    private static Context mContext;
    // 超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    private OkHttpClient mOkHttpClient;
    private Retrofit mRetrofit;

    /**
     * API列表
     */
    private DefaultApiService mBaseApi;
    private DoubApiService mDoubApi;

    private static class NetWorkManagerHolder {
        private static final NetClient INSTANCE = new NetClient(mContext);
    }

    public static final NetClient getInstance(Context context) {
        if (context != null) {
            mContext = context;
        }
        return NetWorkManagerHolder.INSTANCE;
    }

    public static final NetClient getInstance(Context context, Map<String, String> headers) {
        if (context != null) {
            mContext = context;
        }
        return new NetClient(mContext, headers);
    }

    private NetClient(Context context) {
        this(context, null);
    }

    private NetClient(Context context, Map<String, String> headers) {
        //RetrofitUrlManager 初始化
        this.mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                //添加拦截器
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                .build();

        this.mRetrofit = new Retrofit.Builder()
                .baseUrl(DEFAULT_DOMAIN)
                //使用rxjava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //使用Gson
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();

        this.mBaseApi = mRetrofit.create(DefaultApiService.class);
        this.mDoubApi = mRetrofit.create(DoubApiService.class);
    }

    public static <T> ObservableTransformer<T, T> getDefaultTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate((Action) () -> {
                });
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }


    /**
     * 检查当前baseurl
     */
    public NetClient checkDoMainUrl(String domain_url, String domain_name) {
        HttpUrl httpUrl = RetrofitUrlManager.getInstance().fetchDomain(domain_name);
        //可以在 App 运行时随意切换某个接口的 BaseUrl
        if (httpUrl == null || !httpUrl.toString().equals(domain_url)) {
            RetrofitUrlManager.getInstance().putDomain(domain_name, domain_url);
        }
        return this;
    }

    /**
     * 获取api
     *
     * @description 有多个baseurl时请补充get方法
     */
    public DefaultApiService getApiService() {
        checkDoMainUrl(Api.Base.DEFAULT_RELEASE_DOMAIN, Api.Base.DEFAULT_DOMAIN_NAME);
        return mBaseApi;
    }

    public DoubApiService getDoubApiService() {
        checkDoMainUrl(Api.Douban.DEFAULT_DOMAIN, Api.Douban.DEFAULT_DOMAIN_NAME);
        return mDoubApi;
    }
}
