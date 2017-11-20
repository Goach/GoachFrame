package com.base.lib.presenter

import com.base.lib.api.IProgressDialog
import com.base.lib.view.Mvp



/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * Presenter的基类,一定要实现Mvp.View，以及IProgressDialog
 */
@Suppress("UNCHECKED_CAST")
abstract class BasePresenter<V : Mvp.View> : Presenter,IProgressDialog{

    private var attachView:V? = null
    private var dialog:IProgressDialog ?= null
    lateinit var mvpView:V
    override fun attachView(attach: Any) {
        this.attachView = attach as V
        this.dialog = attach as IProgressDialog
        mvpView = mvpView()
        initHandler()
    }

    override fun detachView() {
        this.attachView = null
    }

    private fun mvpView(): V {
        checkAttachView()
        return attachView!!
    }
    fun checkAttachView(){
        if(this.attachView==null) throw RuntimeException("Call the attachView method before using the Mvp.View")
    }

    open fun initHandler() {
    }

    override fun showLoading() {
        dialog?.showLoading()
    }

    override fun dismissLoading() {
        dialog?.dismissLoading()
    }

    override fun updateNextPage(haveNext: Boolean) {
        dialog?.updateNextPage(haveNext)
    }
}