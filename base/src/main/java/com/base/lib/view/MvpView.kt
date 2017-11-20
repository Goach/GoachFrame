package com.base.lib.view

import com.base.lib.utils.Load
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.realm.RealmAsyncTask

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * View层操作基类
 */
object Mvp {
    interface View {
        val sub: CompositeDisposable
        val mRealmAsyncList: MutableList<RealmAsyncTask>
    }
}

interface RefreshView:Mvp.View, Load.Refresh

interface PageListView : RefreshView, Load.More {
    val errorAction: Consumer<Throwable>
}
interface LoginView:Mvp.View