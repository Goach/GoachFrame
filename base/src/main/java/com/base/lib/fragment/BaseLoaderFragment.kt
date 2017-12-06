package com.base.lib.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.view.View
import com.base.lib.consts.Consts
import com.base.lib.presenter.BasePresenter
import com.base.lib.utils.bindPresenter
import com.base.lib.view.Mvp
import timber.log.Timber

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class BaseLoaderFragment<P: BasePresenter<*>>:BaseFragment(), Mvp.View, LoaderManager.LoaderCallbacks<P>{
    var presenter:P? = null
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity!!.supportLoaderManager.initLoader(Consts.BASE_ACTIVITY_LOADER_ID,null,this)
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
    override fun onDetach() {
        presenter?.detachView()
        super.onDetach()
    }
    abstract fun createLoader():Loader<P>
}