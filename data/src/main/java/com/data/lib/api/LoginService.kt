package com.data.lib.api

import com.data.lib.dto.ArticleListResponse
import com.data.lib.dto.ResponseWrapper
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 供Retrofit使用的接口定义
 */
interface LoginService {
    fun <T> login(): Observable<ResponseWrapper<T>>

    @FormUrlEncoded
    @POST("ArticleList")
    fun articleList(@Field("page") page:Int = 0,@Field("size") size:Int = 15,@Field("category_id") category_id:Long): Observable<ResponseWrapper<ArticleListResponse>>
}