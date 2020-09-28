package com.sung.mvvmframe.api;

import io.reactivex.Observable;
import me.jessyan.net.RetrofitUrlManager;
import me.sung.base.bean.request.TopNewsRequest;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Create by sung at 2020/9/25
 *
 * @desc:
 * @notice:
 */
public interface WeatherApiService {

    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.Weather.DEFAULT_DOMAIN_NAME)
    @POST("/toutiao/index")
    Observable<ResponseBody> getTopNews(@Body TopNewsRequest request);

    @Headers(RetrofitUrlManager.DOMAIN_NAME_HEADER + Api.Weather.DEFAULT_DOMAIN_NAME)
    @GET("/toutiao/index")
    Observable<ResponseBody> getTopNews2(@Query("type") String type,@Query("key") String key);

}
