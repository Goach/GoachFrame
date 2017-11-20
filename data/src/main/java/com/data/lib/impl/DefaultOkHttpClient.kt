package com.data.lib.impl

import com.data.lib.api.IClient
import com.data.lib.consts.Consts
import okhttp3.*
import java.util.concurrent.TimeUnit

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
class DefaultOkHttpClient:IClient {
    private var mConnectionTimeOut = Consts.CONNECTION_TIMEOUT
    private var mWriteTimeOut = Consts.CONNECTION_TIMEOUT
    private var mReadTimeOut = Consts.CONNECTION_TIMEOUT
    private var isRetryOnConnectionFailure = Consts.IS_RETRY_ON_CONNECTION_FAILURE
    private var mCookieJar = getCookieJar()
    private var mInterceptors : Array<Interceptor> = emptyArray()
    override fun getClient(): OkHttpClient {
        return OkHttpClient()
                .newBuilder()
                .connectTimeout(mConnectionTimeOut,TimeUnit.SECONDS)
                .writeTimeout(mWriteTimeOut,TimeUnit.SECONDS)
                .readTimeout(mReadTimeOut,TimeUnit.SECONDS)
                .retryOnConnectionFailure(isRetryOnConnectionFailure)
                .cookieJar(mCookieJar)
                .addInterceptors(mInterceptors)
                .build()
    }

    fun getCookieJar():CookieJar{
        return object:CookieJar{
            var mCookieStore:MutableMap<String,MutableList<Cookie>> = mutableMapOf()
            override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                mCookieStore.put(url.host(),cookies)
            }

            override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                val cookies = mCookieStore[url.host()]
                return cookies?: mutableListOf()
            }
        }
    }
    fun OkHttpClient.Builder.addInterceptors(mInterceptors : Array<Interceptor>):OkHttpClient.Builder{
        if(mInterceptors.isNotEmpty()){
            mInterceptors.forEach {
                this.addInterceptor(it)
            }
        }
        return this
    }

    fun setConnectionTimeOut(time:Long):DefaultOkHttpClient{
        this.mConnectionTimeOut = time
        return this
    }
    fun setWriteTimeOut(time:Long):DefaultOkHttpClient{
        this.mWriteTimeOut = time
        return this
    }

    fun setReaderTimeOut(time:Long):DefaultOkHttpClient{
        this.mReadTimeOut = time
        return this
    }
    fun isRetryOnConnectionFailure(isRetry:Boolean):DefaultOkHttpClient{
        this.isRetryOnConnectionFailure = isRetry
        return this
    }
    fun setCookieJar(cookieJar:CookieJar):DefaultOkHttpClient{
        this.mCookieJar = cookieJar
        return this
    }
    fun setInterceptors(interceptors:Array<Interceptor>):DefaultOkHttpClient{
        this.mInterceptors = interceptors
        return this
    }
}