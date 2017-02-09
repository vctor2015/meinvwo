package com.meinvwo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 描述:
 * <p>
 * Created by ruanzb on 2017/2/8.
 */

public interface GankApi {
    @GET("data/福利/{number}/{page}")
    Observable<GankBo> getGankBos(@Path("number") int num,
                                  @Path("page") int page);
}
