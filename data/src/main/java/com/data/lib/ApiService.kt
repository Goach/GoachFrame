package com.data.lib

import com.data.lib.api.IClient
import com.data.lib.dto.GsonConverter
import com.data.lib.impl.DefaultOkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 使用Application里面使用ApiService.registerClient(DefaultOkHttpClient().)
 */
object ApiService {
    private var mIClient:IClient = DefaultOkHttpClient()
    private val mRetrofitMap:MutableMap<String,Retrofit> = mutableMapOf()
    fun <T> get(baseUrl: String, service: Class<T>): T {
        return this.getRetrofit<T>(baseUrl).create(service)
    }

    private fun <T> getRetrofit(baseUrl: String):Retrofit{
        if(baseUrl.isEmpty()){
            throw IllegalArgumentException("baseUrl can not be empty")
        }
        if(mRetrofitMap[baseUrl] != null){
            return mRetrofitMap[baseUrl]!!
        }
        val mRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonConverter().createGson<T>()))
                .client(mIClient.getClient()).build()
        mRetrofitMap.put(baseUrl,mRetrofit)
        return mRetrofit
    }
    fun registerClient(client:IClient){
        this.mIClient = client
    }

}