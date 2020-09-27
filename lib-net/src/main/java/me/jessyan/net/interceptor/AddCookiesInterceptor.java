package me.jessyan.net.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Create by sung at 2020-04-21
 *
 * @desc: cookie添加拦截器
 * @notice:
 */
public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Cookie", "test");
        return chain.proceed(builder.build());
    }
}