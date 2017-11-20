package com.base.lib.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
open class AdapterDelegatesManager<T> {
    private val delegates:SparseArrayCompat<AdapterDelegate<T>> = SparseArrayCompat()

    open fun addDelegate(delegate: AdapterDelegate<T>):AdapterDelegatesManager<T>{
        this.delegates.put(getViewType(delegate),delegate)
        return this
    }
    open fun getViewType(delegate:AdapterDelegate<T>):Int{
        return delegate.hashCode()
    }
    open fun removeDelegate(delegate: AdapterDelegate<T>):AdapterDelegatesManager<T>{
        this.delegates.remove(getViewType(delegate))
        return this
    }
    open fun removeDelegate(viewType:Int):AdapterDelegatesManager<T>{
        this.delegates.remove(viewType)
        return this
    }
    open fun getItemViewType(item:T):Int{
        val delegateCount = delegates.size()
        (0 .. delegateCount -1)
                .map { delegates.valueAt(it) }
                .filter { it.isForViewType(item) }
                .forEach { return getViewType(it) }
        throw IllegalArgumentException("No AdapterDelegate added that matches")
    }
    open fun onCreateViewHolder(parent:ViewGroup,viewType: Int): RecyclerView.ViewHolder{
        return delegates.get(viewType).onCreateViewHolder(parent)
    }
    open fun onBindViewHolder(viewHolder:AbsDelegationAdapter.ViewHolder,item:T,position:Int){
        val delegate = delegates.get(viewHolder.itemViewType)
        delegate.onBindViewHolder(viewHolder,item)
        val itemView = viewHolder.itemView
        itemView.setOnClickListener {
            _ ->
            val listener = delegate.onItemClickListener
            listener?.onItemClick(position, item)
        }
        itemView.setOnLongClickListener {
            _ ->
            val listener = delegate.onItemLongClickListener
            listener?.onItemLongClick(position, item)
            false
        }
    }
}