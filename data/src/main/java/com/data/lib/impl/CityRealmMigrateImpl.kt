package com.data.lib.impl

import com.data.lib.R
import com.data.lib.api.IRealmMigrate
import com.utils.lib.AppBridge
import io.realm.DynamicRealm
import io.realm.RealmMigration
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
class CityRealmMigrateImpl : IRealmMigrate {
    override fun src() = AppBridge.AppContext().resources.openRawResource(R.raw.city)!!
    override fun realmName() = "city.realm"
    override fun schemaVersion() = 1
    override fun migration() = CityMigration()
}

class CityMigration: RealmMigration {
    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        Timber.d("oldVersion=====$oldVersion")
        Timber.d("newVersion=====$newVersion")
    }
}