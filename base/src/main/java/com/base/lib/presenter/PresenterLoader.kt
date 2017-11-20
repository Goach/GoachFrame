package com.base.lib.presenter

import android.content.Context
import android.support.v4.content.Loader

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
class PresenterLoader<P :BasePresenter<*>>(context: Context, val factory:PresenterFactory<P>): Loader<P>(context) {
    private var presenter:P? = null
    override fun onStartLoading() {
        if(presenter != null){
            deliverResult(presenter)
            return
        }
        forceLoad()
    }

    override fun onForceLoad() {
        presenter = factory.create()
        deliverResult(presenter)
    }
    override fun onReset() {
        presenter = null
    }
}