package com.data.lib.api

import io.realm.Realm
import io.realm.RealmMigration
import java.io.InputStream

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 迁移数据抽象出来的变量
 */
interface IRealmMigrate {
    fun src():InputStream?
    fun realmName():String
    fun schemaVersion():Int
    fun migration(): RealmMigration
}