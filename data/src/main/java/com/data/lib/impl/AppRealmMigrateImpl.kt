package com.data.lib.impl

import com.data.lib.R
import com.data.lib.api.IRealmMigrate
import com.data.lib.db.User
import com.utils.lib.AppBridge
import io.realm.DynamicRealm
import io.realm.FieldAttribute
import io.realm.RealmMigration
import io.realm.RealmSchema
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
class AppRealmMigrateImpl : IRealmMigrate {
    override fun src() = null
    override fun realmName() = "goach.realm"
    override fun schemaVersion() = 1
    override fun migration() = AppMigration()
}

@Suppress("INACCESSIBLE_TYPE")
class AppMigration: RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        val schema = realm.schema as RealmSchema
        if(oldVersion==0L&&newVersion==1L){
            val userSchema = schema.get(User::class.java.simpleName)
            if(userSchema!=null&&!userSchema.hasField("sex")){
                userSchema.addField("sex",Int::class.java,FieldAttribute.REQUIRED)
            }
            oldVersion.plus(1)
        }
    }
}