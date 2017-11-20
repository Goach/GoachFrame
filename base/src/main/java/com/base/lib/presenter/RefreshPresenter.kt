package com.base.lib.presenter

import android.support.v4.widget.SwipeRefreshLayout
import com.base.lib.view.RefreshView
import com.data.lib.dto.BaseResponse

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 刷新Presenter
 */
 abstract class RefreshPresenter<in Res : BaseResponse,V:RefreshView>:BasePresenter<V>() {
    override fun initHandler() {
        mvpView.initRefresh(SwipeRefreshLayout.OnRefreshListener { onRefresh() })
    }
    abstract fun onRefresh()
    open protected fun onRefreshFinish(res:Res){
        mvpView.onRefreshFinish()
    }
}