package com.goach.client

import android.app.Application
import android.util.Log
import com.data.lib.ApiService
import com.data.lib.db.City
import com.data.lib.impl.*
import com.utils.lib.AppBridge
import com.utils.lib.api.IAPP
import io.realm.Realm
import timber.log.Timber

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
class App:Application(),IAPP {
    override fun onCreate() {
        super.onCreate()
        AppBridge.Injection(this)
        initLog().initRealm().initCity().initOkHttp()
    }
    private fun initRealm():App{
        Realm.init(this)
        return this
    }
    private fun initCity():App{
        val inStream = this.resources.openRawResource(R.raw.city)
        val mRealm = RealmHelper.getRealmInstance(AppRealmMigrateImpl())
        mRealm.use { it ->
            it.executeTransaction {
                realm ->
                realm.createOrUpdateAllFromJson(City::class.java,inStream)
            }
        }
        return this
    }
    private fun initOkHttp():App{
        ApiService
                .registerClient(DefaultOkHttpClient()
                        .setInterceptors(arrayOf(
                                OkHttpLogInterceptor().getInterceptor(),
                                BasicParamsInterceptor().getInterceptor(),
                                ResponseInterceptor().getInterceptor())))
        return this
    }
    private fun initLog(): App {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        return this
    }
    /*
    *AppBridge使用
     */
    override fun createApp(): Application {
        return this
    }
}