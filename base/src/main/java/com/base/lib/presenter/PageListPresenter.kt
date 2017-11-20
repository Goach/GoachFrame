package com.base.lib.presenter

import android.support.annotation.CallSuper
import com.base.lib.utils.bindTo
import com.base.lib.utils.loadingTransformer
import com.base.lib.utils.subscribeWith
import com.base.lib.view.PageListView
import com.data.lib.dto.PageInfo
import com.data.lib.dto.PageResponse
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 封装分页Presenter
 */
abstract class PageListPresenter<in Res: PageResponse,V : PageListView>: RefreshPresenter<Res,V>() {
    protected lateinit var page:PageInfo
    override fun initHandler() {
        super.initHandler()
        mvpView.initMore { onNextPage() }
        mvpView.srl.post {
            mvpView.srl.isRefreshing = true
            onRefresh()
        }
    }
    override fun onRefresh() {
        page = PageInfo(1)
    }
    abstract fun onNextPage()

    @CallSuper override fun onRefreshFinish(res: Res) {
        super.onRefreshFinish(res)
        updateNext(res)
    }
    @CallSuper open fun onPageFinish(res: Res) {
        updateNext(res)
    }
    private fun updateNext(res: Res) {
        mvpView.haveNext = haveNext(res)
        page.page += 1
        mvpView.isMoreLoading = false
        if(res.total.toFloat()>0)
            updateNextPage(mvpView.haveNext)
    }
    private fun haveNext(res: Res) = res.total.toFloat() / res.size > res.page
}

abstract class SimpleListPresenter<Res : PageResponse, V : PageListView> : PageListPresenter<Res, V>() {

    final override fun onRefresh() {
        super.onRefresh()
        createObservable()?.compose(loadingTransformer<Res>(this))
                ?.subscribeWith(
                        {
                            onRefreshFinish(it)},
                        { mvpView.errorAction.accept(it)})?.bindTo(mvpView.sub)
    }

    final override fun onNextPage() {
        createObservable()?.subscribeWith(
                { onPageFinish(it)},
                {
                  mvpView.errorAction.accept(it)
                  mvpView.isMoreLoading = false
                })?.bindTo(mvpView.sub)
    }

    abstract fun createObservable(): Observable<Res>?
}
