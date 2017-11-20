package com.base.lib.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.view.ViewGroup
import com.base.lib.utils.AdapterExtendsHelper
import timber.log.Timber

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
open class AbsDelegationAdapter<T>(private val list:List<T>,private val adm:AdapterDelegatesManager<T>):RecyclerView.Adapter<AbsDelegationAdapter.ViewHolder>() {
    private var mAdapterExtendsHelper = AdapterExtendsHelper()
    private var mRecyclerView:RecyclerView? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }
    override fun getItemCount() = list.size + mAdapterExtendsHelper.headerSize() + mAdapterExtendsHelper.footerSize()
    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        if(!mAdapterExtendsHelper.isFooterView(position,itemCount)&&!mAdapterExtendsHelper.isHeaderView(position)){
            adm.onBindViewHolder(holder, list[position - mAdapterExtendsHelper.headerSize()], position)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        if(mAdapterExtendsHelper.isItemHeaderType(viewType)){
            return AbsDelegationAdapter.ViewHolder(mAdapterExtendsHelper.getItemHeaderView(viewType))
        }
        if(mAdapterExtendsHelper.isItemFooterType(viewType)){
            return AbsDelegationAdapter.ViewHolder(mAdapterExtendsHelper.getItemFooterView(viewType))
        }
        return adm.onCreateViewHolder(parent,viewType) as AbsDelegationAdapter.ViewHolder
    }
    override fun getItemViewType(position: Int): Int {
        if(mAdapterExtendsHelper.isHeaderView(position)){
            val itemView = mAdapterExtendsHelper.itemHeaderValueOf(position)
            return mAdapterExtendsHelper.getViewType(itemView!!)
        }
        if(mAdapterExtendsHelper.isFooterView(position,itemCount)){
            val itemView = mAdapterExtendsHelper.itemFooterValueOf(position - list.size - mAdapterExtendsHelper.headerSize())
            return mAdapterExtendsHelper.getViewType(itemView!!)
        }
        return adm.getItemViewType(list[position - mAdapterExtendsHelper.headerSize()])
    }
    fun addHeaderView(view:View){
        ifGridLayoutManager()
        mAdapterExtendsHelper.addHeaderView(view)
        notifyItemInserted(mAdapterExtendsHelper.headerSize()-1)
    }
    fun addFooterView(view:View){
        ifGridLayoutManager()
        mAdapterExtendsHelper.addFooterView(view)
        notifyItemInserted(itemCount - mAdapterExtendsHelper.footerSize())
    }
    private fun ifGridLayoutManager() {
        if (mRecyclerView == null) return
        val layoutManager = mRecyclerView!!.layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (mAdapterExtendsHelper.isFooterView(position,itemCount)||mAdapterExtendsHelper.isHeaderView(position))
                        layoutManager.spanCount
                    else
                        1
                }
            }
        }
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val views = SparseArray<View>()
        init {
            views.put(View.NO_ID,itemView)
            if(itemView is ViewGroup){
                loadView(itemView)
            }else {
                val id = itemView.id

                if (id != View.NO_ID && views.indexOfKey(id) < 0) {
                    views.put(id, itemView)
                }
            }
        }
        @Suppress("UNCHECKED_CAST")
        fun <V : View> view(viewId: Int) = views.get(viewId) as V
        fun loadView(viewGroup: ViewGroup){
            val count = viewGroup.childCount
            for(index in 0 ..count -1){
                val childView = viewGroup.getChildAt(index)
                val id = childView.id
                if(id != View.NO_ID && views.indexOfKey(id) < 0){
                    views.put(id,childView)
                }
                if(childView is ViewGroup){
                    loadView(childView)
                }
            }
        }
    }
}