package com.base.lib.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.base.lib.R
import com.base.lib.adapter.AbsDelegationAdapter
import com.base.lib.adapter.AdapterDelegate
import com.base.lib.adapter.AdapterDelegatesManager
import com.base.lib.api.ListViewData
import com.base.lib.api.OnItemClickListener
import com.base.lib.api.OnItemLongClickListener
import com.base.lib.presenter.BasePresenter
import com.base.lib.utils.StatusViewController
import com.base.lib.view.Mvp
import com.base.lib.view.PageListView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.find

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * T泛型指的是返回的数据类型，P指的是Presenter
 */
abstract class SingleTypeListFragment<P: BasePresenter<*>,T>:BaseLoaderFragment<P>(), ListViewData<T>,OnItemClickListener<T>,OnItemLongClickListener<T> {
    override val list by lazy(LazyThreadSafetyMode.NONE) { arrayListOf<T>() }
    val mRecyclerView by lazy { find<RecyclerView>(android.R.id.list) }
    val mStatusView by lazy { StatusViewController(ctx,mRecyclerView)}
    override val adapter by lazy(LazyThreadSafetyMode.NONE) { createAdapter() }
    override fun layoutResID() = R.layout.list_layout
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        configureList(mRecyclerView)
        super.onViewCreated(view, savedInstanceState)
    }
    private fun createAdapter(): AbsDelegationAdapter<T> {
        val manager = AdapterDelegatesManager<T>()

        val adapterDelegate = createAdapterDelegate()
        if (adapterDelegate != null) {
            settingAdapterDelegate(adapterDelegate)
            manager.addDelegate(adapterDelegate)
        } else {
            createAdapterDelegate2().forEach {
                settingAdapterDelegate(it)
                manager.addDelegate(it)
            }
        }
        return AbsDelegationAdapter(list, manager)
    }
    private fun settingAdapterDelegate(adapterDelegate: AdapterDelegate<T>) {
        adapterDelegate.onItemClickListener = this
        adapterDelegate.onItemLongClickListener = this
    }

    private fun configureList(recyclerView: RecyclerView) {
        configListView(recyclerView)

        recyclerView.adapter = adapter
        configAdapter(adapter)
    }
    override fun onItemClick(position: Int, item: T) {
    }
    override fun onItemLongClick(position: Int, item: T): Boolean {
        return false
    }

    open fun configListView(recyclerView: RecyclerView) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        defaultItemDecoration()?.let { recyclerView.addItemDecoration(it) }
    }

    open fun defaultItemDecoration(): RecyclerView.ItemDecoration? = null

    open fun configAdapter(adapter: AbsDelegationAdapter<T>) {
    }

    abstract fun createAdapterDelegate(): AdapterDelegate<T>?

    open fun createAdapterDelegate2() = arrayOf<AdapterDelegate<T>>()

}