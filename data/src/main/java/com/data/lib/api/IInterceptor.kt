package com.data.lib.api

import okhttp3.Interceptor

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 抽象拦截器对象
 */
interface IInterceptor {
    fun getInterceptor():Interceptor
}