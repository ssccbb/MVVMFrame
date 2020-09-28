package me.jessyan.net.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import me.jessyan.net.NetMoudleApi;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * BaseInterceptor
 * Created by Tamic on 2016-06-30.
 * {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public class BaseInterceptor implements Interceptor{
    private Map<String, String> headers;
    public BaseInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
                .newBuilder();

        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey, headers.get(headerKey)).build();
            }
        }

        try {
            SharedPreferences sp = NetMoudleApi.getInstance().getContext().getSharedPreferences("sp_login", Context.MODE_PRIVATE);
            if (sp != null) {
                String json = sp.getString("login_info", "");
                if (!TextUtils.isEmpty(json)) {
                    JSONObject object = new JSONObject(json);
                    String token = object.getString("login_token");
                    if (!TextUtils.isEmpty(token)) {
                        String cookie = "token=" + token;
//                    Log.e("cookies", cookie);
                        builder.addHeader("Cookie", cookie);
                    } else {
//                    Log.e("cookies", "token null");
                    }
                }
            } else {
                Log.e("cookies", "sp null");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("cookies",e.toString());
        }

//        Log.d(Retrofit.NET_TAG,  "Okhttp url:" + builder.build().url());
        return chain.proceed(builder.build());

    }
}