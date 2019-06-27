package com.example.day_02_zuoyeone;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by DELL on 2019/6/27.
 */

public interface ApIService {
    String url="http://www.qubaobei.com/";

    @GET("ios/cf/dish_list.php?stage_id=1&limit=20&page=1")
    Observable<Shipingbean> initCai();
}
