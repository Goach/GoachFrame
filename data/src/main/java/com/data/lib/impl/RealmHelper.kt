package com.data.lib.impl

import android.support.v4.util.SparseArrayCompat
import com.data.lib.api.IRealmMigrate
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
object RealmHelper {
    private val mMigrationMap:SparseArrayCompat<Realm> = SparseArrayCompat()
    fun getRealmInstance(migration:IRealmMigrate):Realm{
        val key = migration.realmName().hashCode()
        var mRealm:Realm? = mMigrationMap.get(key)
        if(mRealm==null||mRealm.isClosed||mMigrationMap.indexOfKey(key)<0){
            val migrationConfig = RealmConfiguration.Builder()
                    .name(migration.realmName())
                    .schemaVersion(migration.schemaVersion().toLong())
                    .migration(migration.migration())
                    .build()
            if(migration.src()!=null){
                val file = File(migrationConfig!!.path)
                if (!file.exists()||file.length() == 0L) {
                    file.delete()
                    file.createNewFile()
                    file.outputStream().use { out -> migration.src()!!.use { it.copyTo(out) } }
                }
            }
            mRealm = Realm.getInstance(migrationConfig)

            mMigrationMap.put(key,mRealm)
        }
       return mRealm!!

    }
    fun clear(){
        mMigrationMap.clear()
    }
}