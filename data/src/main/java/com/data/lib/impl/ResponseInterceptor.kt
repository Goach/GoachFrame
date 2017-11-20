package com.data.lib.impl

import com.data.lib.api.IInterceptor
import com.data.lib.exception.RequestException
import com.data.lib.exception.TokenExpiredException
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 处理接口返回异常
 */
class ResponseInterceptor(val handlerResponseException:((response:Response)->Unit)?=null):IInterceptor {
    override fun getInterceptor(): Interceptor {
        return Interceptor {
            chain ->
            val response = chain.proceed(chain.request())
            handlerResponseException?.invoke(response)
            when(response.code()){
                200 -> response
                10001 -> throw TokenExpiredException(response.code(), response.message())
                else -> throw RequestException(response.code(), response.message())
            }
        }
    }
}