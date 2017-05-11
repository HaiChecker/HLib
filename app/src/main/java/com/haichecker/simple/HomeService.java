package com.haichecker.simple;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Part;
import rx.Observable;

/**
 * 作   者 ： HaiChecker.Dev@gmail.com ON 17-3-2 18:27
 */

public interface HomeService {
    @GET("/Index/getIndexCarouselAds")
    Observable<String> getIndexCarouselAds();

//    @GET("Outsourcing/index.php/Home/Index/upload")
//    Observable<String> upload(@Part(""))
}
