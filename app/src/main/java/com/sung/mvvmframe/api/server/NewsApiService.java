package com.sung.mvvmframe.api.server;

import io.reactivex.Observable;
import me.jessyan.net.RetrofitUrlManager;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Create by sung at 2020/9/25
 *
 * @desc:
 * @notice:
 */
public interface NewsApiService {

    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + ApiConfig.News.DEFAULT_DOMAIN_NAME)
    @GET("/toutiao/index")
    Observable<ResponseBody> getTopNews(@Query("type") String type, @Query("key") String key);

}
