package com.data.lib.impl

import com.data.lib.api.IInterceptor
import com.data.lib.consts.Consts
import okhttp3.HttpUrl
import okhttp3.Interceptor

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 添加基本参数
 */
class BasicParamsInterceptor(val mQueryParameters : MutableMap<String,String>? = null):IInterceptor {
    override fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url()
            val newUrl = originalHttpUrl
                    .newBuilder()
                    .addQueryParameters(mQueryParameters?:defaultBaseParameters(originalHttpUrl))
                    .build()
            val newRequest = originalRequest
                    .newBuilder()
                    .url(newUrl)
                    .method(originalRequest.method(),originalRequest.body())
                    .build()
            chain.proceed(newRequest)
        }
    }
    fun HttpUrl.Builder.addQueryParameters(mQueryParameters : MutableMap<String,String>):HttpUrl.Builder{
        if(mQueryParameters.isNotEmpty()){
            mQueryParameters.forEach {
                this.addQueryParameter(it.key,it.value)
            }
        }
        return this
    }
    fun defaultBaseParameters(originalHttpUrl:HttpUrl):MutableMap<String,String>{
        return mutableMapOf("version" to Consts.API_VERSION,
                            "platform" to Consts.API_PLATFORM,
                            "methodName" to originalHttpUrl.encodedPath().split("/").last(),
                            "token" to "",
                            "citycode" to "103212")
    }
}