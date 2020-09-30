package me.jessyan.net.interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import me.sung.base.cache.MTempCache;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Create by sung at 2020-04-21
 *
 * @desc: cookie接收拦截器
 * @notice:
 */
public class ReceivedCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        // 接收服务端Cookies
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            MTempCache.setCookies(cookies);
        }else {
            Log.v("cookies","empty cookie");
        }

        return originalResponse;
    }
}
