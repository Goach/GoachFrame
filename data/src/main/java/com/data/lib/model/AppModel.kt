package com.data.lib.model

import com.data.lib.ApiService
import com.data.lib.BuildConfig
import com.data.lib.api.LoginService
import com.data.lib.dto.ArticleListResponse
import com.data.lib.dto.LoginResponse
import com.data.lib.dto.PageInfo
import com.data.lib.dto.ResponseWrapper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 提供给外部接口请求的调用方法
 */
object AppModel {

    fun login():Observable<LoginResponse>{
        return ApiClient(LoginService::class.java).login<LoginResponse>().responseWrapperLogic()
    }
    fun articleList(pageInfo: PageInfo,category_id:Long = 0):Observable<ArticleListResponse>{
        return ApiClient(LoginService::class.java).articleList(pageInfo.page,pageInfo.size,category_id).responseWrapperLogic()
    }

    fun <T> ApiClient(service: Class<T>):T{
        return ApiService.get(BuildConfig.BASE_URL,service)
    }

    private fun <T> Observable<ResponseWrapper<T>>.responseWrapperLogic() =
            map { it.data}.compose{it.subscribeOn(Schedulers.io())}.observeOn(AndroidSchedulers.mainThread())
}