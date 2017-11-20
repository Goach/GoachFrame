package com.base.lib.activity

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import com.base.lib.R
import com.base.lib.api.IProgressDialog
import com.base.lib.fragment.TitleFragment
import com.data.lib.impl.AppRealmMigrateImpl
import com.data.lib.impl.RealmHelper
import com.utils.lib.AlertDialogUtils
import io.reactivex.disposables.CompositeDisposable
import io.realm.Realm
import io.realm.RealmAsyncTask
import org.jetbrains.anko.findOptional
import timber.log.Timber

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class BaseActivity:AppCompatActivity(), IProgressDialog {
    val fm: FragmentManager by lazy(LazyThreadSafetyMode.NONE) { supportFragmentManager }
    val sub = CompositeDisposable()
    val mRealm = RealmHelper.getRealmInstance(AppRealmMigrateImpl())
    val mRealmAsyncList = mutableListOf<RealmAsyncTask>()
    private val mToolBar by lazy { findOptional<Toolbar>(R.id.tool_bar) }
    private var mToolBarLayout:Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutResID = layoutResID()
        if(layoutResID>0){
            setContentView(layoutResID)
        }
        val layoutView = layoutView()
        if(layoutView!=null){
            setContentView(layoutView)
        }
        if(mToolBar!=null){
            setSupportActionBar(mToolBar)
            configToolBar()
        }
        if (!isFinishing) {
            initView()
        }
        if (!isFinishing) {
            initListener()
        }
    }
    protected open fun initView() {
    }

    protected open fun initListener() {
    }
    @LayoutRes open fun layoutResID():Int = R.layout.act_single_fragment
    open fun layoutView(): View? = null
    open fun toolbarCustomView(): Fragment? = TitleFragment()
    open fun onBackClick(v: View?) = finish()
    private fun configToolBar(){
        supportActionBar?.setDisplayShowTitleEnabled(false)
         toolbarCustomView()?.apply{
             mToolBarLayout = this
             fm.beginTransaction().replace(R.id.custom_view, this).commitAllowingStateLoss()
             (this as? TitleFragment)?.apply {
                 this.onBackListener = object:TitleFragment.BackClickListener{
                     override fun onBack(view: View) {
                         onBackClick(view)
                     }
                 }
             }
         }
    }

    override fun showLoading() {
        AlertDialogUtils.showProgressDialog(this)
    }

    override fun dismissLoading() {
        AlertDialogUtils.dismiss()
    }

    override fun updateNextPage(haveNext: Boolean) {

    }
    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
    }

    override fun setTitle(titleId: Int) {
        title = getString(titleId)
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
        super.onDestroy()
    }
}