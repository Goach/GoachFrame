package com.base.lib.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.base.lib.R
import com.base.lib.presenter.BasePresenter
import com.base.lib.utils.Load
import com.base.lib.utils.defaultErrorAction
import com.base.lib.utils.hide
import com.base.lib.utils.show
import com.base.lib.view.PageListView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.find

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 封装分页的Fragment
 */
abstract class SingleTypePageListFragment<P: BasePresenter<*>,T> : SingleTypeListFragment<P,T>(), PageListView, Load.Refresh,Load.More {
    private var mFooterView:View? = null
    override val srl by lazy { find<SwipeRefreshLayout>(R.id.srl) }
    override var isMoreLoading = false
    override var haveNext = false
    override val errorAction = defaultErrorAction
    override fun initView(view: View) {
        super.initView(view)
       configFooterView()
    }
    open fun configFooterView(){
        mFooterView = layoutInflater.inflate(R.layout.load_more_layout,mRecyclerView,false)
        adapter.addFooterView(mFooterView!!)
    }
    override fun showLoading() {
        super<PageListView>.showLoading()
    }

    override fun dismissLoading() {
        super<PageListView>.dismissLoading()
    }

    override fun updateNextPage(haveNext: Boolean) {
        if(haveNext.not()){
            mFooterView?.find<TextView>(R.id.loadFull)?.show()
            mFooterView?.find<ProgressBar>(R.id.loading)?.hide()
        }else{
            mFooterView?.find<TextView>(R.id.loadFull)?.hide()
            mFooterView?.find<ProgressBar>(R.id.loading)?.show()
        }
    }
    override fun moreData(list: List<*>) {
        @Suppress("UNCHECKED_CAST")
        addData(list as List<T>)
        notifyDataSetChanged()
    }

    override fun refreshData(list: List<*>) {
        @Suppress("UNCHECKED_CAST")
        setDataAndRefresh(list as List<T>)
    }
}