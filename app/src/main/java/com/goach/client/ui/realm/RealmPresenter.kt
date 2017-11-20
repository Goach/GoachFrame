package com.goach.client.ui.realm

import com.base.lib.presenter.BasePresenter
import com.base.lib.utils.bindTo
import com.data.lib.db.City
import com.data.lib.db.User
import com.data.lib.impl.RealmHelper
import io.realm.RealmResults
import timber.log.Timber
import java.util.concurrent.atomic.AtomicInteger

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
class RealmPresenter:BasePresenter<RealmFragment>() {
    private val names = arrayOf("Android","Java","Kotlin","JS","PHP")
    private val idCount:AtomicInteger = AtomicInteger(0)
    fun syncAddOrUpdateItem(){
        mvpView.mRealm.beginTransaction()
        mvpView.mRealm.copyToRealmOrUpdate(createUser())
        mvpView.mRealm.commitTransaction()
        mvpView.UpdateUI(findAllUser())
      /*  mvpView.mRealm.executeTransaction {
            realm ->
            realm.copyToRealmOrUpdate(createUser())
            mvpView.UpdateUI(findAllUser())
        }*/
    }
    fun asyncAddOrUpdateItem(){
        mvpView.mRealm.executeTransactionAsync({
            it.copyToRealmOrUpdate(createUser())
        },{
            mvpView.UpdateUI(findAllUser())
        },{
        }).bindTo(mvpView.mRealmAsyncList)
    }
    fun asyncQueryItem(){
        val results = mvpView.mRealm.where(User::class.java).equalTo("name","Android").findAllAsync()
        mvpView.UpdateUI(results)
    }
    fun removeItem(){
        val results = findAllUser()
        mvpView.mRealm.executeTransaction {
            _ ->
            results.deleteFirstFromRealm()
            if(idCount.get()>1) idCount.decrementAndGet()
            mvpView.UpdateUI(results)
        }
    }
    fun removeAll(){
        val results = findAllUser()
        mvpView.mRealm.executeTransaction {
            _ ->
            results.deleteAllFromRealm()
            idCount.set(0)
            mvpView.UpdateUI(results)
        }
    }
    fun cityAll(){
        val city = mvpView.mRealm.where(City::class.java).findAll()
        Timber.d("city====${city.size}")
        city.forEach { Timber.d("city====${it.name}") }
    }
    private fun createUser(): User {
        val user = User()
        user.id = idCount.get()
        user.name = names[(Math.random()*4).toInt()]
        user.age = (Math.random()*10).toInt()
        user.sex  = 1
        idCount.incrementAndGet()
        return user
    }
    private fun findAllUser():RealmResults<User> = mvpView.mRealm.where(User::class.java).findAll()
}