package com.base.lib.utils

import android.support.v4.content.Loader
import com.base.lib.activity.BaseActivity
import com.base.lib.api.IProgressDialog
import com.base.lib.fragment.BaseFragment
import com.base.lib.presenter.BasePresenter
import com.base.lib.presenter.PresenterFactory
import com.base.lib.presenter.PresenterLoader
import com.data.lib.exception.NetworkException
import com.utils.lib.AppBridge
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.observers.DisposableObserver
import io.realm.Realm
import io.realm.RealmAsyncTask
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 一些帮助扩展类
 */
inline fun <reified P: BasePresenter<*>> BaseFragment.bindPresenter(presenter:P): Loader<P> {
    return PresenterLoader(AppBridge.AppContext(),object:PresenterFactory<P>{
        override fun create(): P {
            return  presenter
        }
    })
}
inline fun <reified P: BasePresenter<*>> BaseActivity.bindPresenter(presenter:P): Loader<P> {
    return PresenterLoader(AppBridge.AppContext(),object:PresenterFactory<P>{
        override fun create(): P {
            return  presenter
        }
    })
}

fun <T> loadingTransformer(loading: IProgressDialog): ObservableTransformer<T, T> {
    return ObservableTransformer { observable ->
        observable.doOnSubscribe { loading.showLoading() }
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { loading.dismissLoading() }
                .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> Observable<T>.subscribeWith(onNext:((res: T) ->Unit)?=null,
                                    onError:((e: Throwable) ->Unit)?=null,
                                    onComplete:(() ->Unit)?=null):DisposableObserver<T>{
    return this.subscribeWith(object : DisposableObserver<T>() {
        override fun onError(e: Throwable) {
            if(onError!=null) onError(e)
        }
        override fun onComplete() {
            if(onComplete!=null) onComplete()
        }

        override fun onNext(res: T) {
            if(onNext!=null) onNext(res)
        }
    })
}

fun  <T> DisposableObserver<T>.bindTo(sub: CompositeDisposable) {
    sub.add(this)
}

fun RealmAsyncTask.bindTo(realmAsyncList:MutableList<RealmAsyncTask>){
    realmAsyncList.add(this)
}

fun Collection<*>?.isNullOrEmpty(): Boolean {
    return this == null || isEmpty()
}

val defaultErrorAction = Consumer<Throwable> {
    if (it !is NetworkException) Timber.d(it, "")
}