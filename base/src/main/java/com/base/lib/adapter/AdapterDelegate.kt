package com.base.lib.adapter

import android.view.ViewGroup
import com.base.lib.api.OnItemClickListener
import com.base.lib.api.OnItemLongClickListener

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class AdapterDelegate<T> {
    var onItemClickListener: OnItemClickListener<T> ?= null
    var onItemLongClickListener: OnItemLongClickListener<T> ?= null

    abstract fun isForViewType(item:T):Boolean
    abstract fun onCreateViewHolder(parent:ViewGroup):AbsDelegationAdapter.ViewHolder
    abstract fun onBindViewHolder(holder: AbsDelegationAdapter.ViewHolder, item: T)
}