package com.base.lib.activity

import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import com.base.lib.consts.Consts
import com.base.lib.presenter.BasePresenter
import com.base.lib.view.Mvp

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class BaseLoaderActivity<P:BasePresenter<*>>:SingleFragmentActivity(),Mvp.View,LoaderManager.LoaderCallbacks<P>{
    protected var presenter:P? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportLoaderManager.initLoader(Consts.BASE_ACTIVITY_LOADER_ID,null,this)
    }
    override fun onLoadFinished(loader: Loader<P>?, data: P) {
        presenter = data
        presenter?.attachView(this)
    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<P>? {
        return createLoader()
    }

    override fun onLoaderReset(loader: Loader<P>?) {
        presenter = null
    }
    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }
    abstract fun createLoader():Loader<P>
}