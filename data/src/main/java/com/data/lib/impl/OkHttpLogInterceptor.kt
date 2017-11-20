package com.data.lib.impl

import com.data.lib.api.IInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 打印请求log日志
 */
class OkHttpLogInterceptor:IInterceptor {
    override fun getInterceptor(): Interceptor {
        val mHttpLogInter = HttpLoggingInterceptor{
            message ->
            Timber.d("HttpLogging=====$message")
        }
        mHttpLogInter.level = HttpLoggingInterceptor.Level.BODY
        return mHttpLogInter
    }
}