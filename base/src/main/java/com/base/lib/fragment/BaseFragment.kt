package com.base.lib.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.activity.BaseActivity
import com.base.lib.api.IProgressDialog
import com.data.lib.impl.AppRealmMigrateImpl
import com.data.lib.impl.RealmHelper
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import io.realm.RealmAsyncTask
import kotlin.properties.Delegates

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class BaseFragment:Fragment(), IProgressDialog {
    private var rootView by Delegates.notNull<View>()
    val fm: FragmentManager by lazy(LazyThreadSafetyMode.NONE) { childFragmentManager }
    val sub = CompositeDisposable()
    val mRealm = RealmHelper.getRealmInstance(AppRealmMigrateImpl())
    val mRealmAsyncList = mutableListOf<RealmAsyncTask>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        checkLayoutConfig()
        rootView = layoutView()?:inflater.inflate(layoutResID(),container,false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFinishing()) {
            initView(view)
        }
        if (!isFinishing()) {
            initListener()
        }
    }
    private fun isFinishing(): Boolean = activity == null
    protected open fun initView(view: View) {
    }

    protected open fun initListener() {
    }
    open fun checkLayoutConfig(){
        if(layoutResID()==0&&layoutView()==null)
            throw RuntimeException("You should call the layoutResID method or the layoutView method")
    }
    open fun layoutResID():Int = 0
    open fun layoutView():View? = null

    override fun showLoading() {
        (activity as? BaseActivity)?.showLoading()
    }
    override fun dismissLoading() {
        (activity as? BaseActivity)?.dismissLoading()
    }
    override fun updateNextPage(haveNext: Boolean) {
    }
    override fun onStop() {
        mRealmAsyncList.forEach {
            if(!it.isCancelled) it.cancel()
        }
        mRealmAsyncList.clear()
        super.onStop()
    }
    override fun onDestroy() {
        sub.clear()
        mRealm.close()
        super.onDestroy()
    }
}