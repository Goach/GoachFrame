package com.utils.lib

import android.app.Application
import android.content.Context
import com.utils.lib.api.IAPP


/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * Application调用AppBridge.Injection(this),同时Application实现IApp接口
 */
object AppBridge {
    private var mApp: IAPP? = null
    fun Injection(app: IAPP) {
        mApp = app
    }

    fun App(): Application {
        checkApp()
        return mApp!!.createApp()
    }

    fun AppContext(): Context {
        checkApp()
        return mApp!!.createApp().applicationContext
    }
    fun checkApp(){
        if(mApp==null) throw RuntimeException("Call the Injection method first")
    }
}