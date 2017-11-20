package com.data.lib.api

import okhttp3.OkHttpClient

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 抽象出Client对象，方便外部重写OkHttpClient
 */
interface IClient {
    fun getClient():OkHttpClient
}