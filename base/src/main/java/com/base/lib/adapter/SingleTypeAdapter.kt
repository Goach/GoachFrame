package com.base.lib.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.properties.Delegates

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class SingleTypeAdapter<T>(private val resource: Int) : AdapterDelegate<T>() {

    private var inflater: LayoutInflater? = null
    var bindViewHolder by Delegates.notNull<AbsDelegationAdapter.ViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup): AbsDelegationAdapter.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }

        val viewHolder = AbsDelegationAdapter.ViewHolder(inflater!!.inflate(resource, parent, false))

        bindViewHolder = viewHolder

        initView()

        return viewHolder
    }

    override fun onBindViewHolder(holder: AbsDelegationAdapter.ViewHolder, item: T) {
        bindViewHolder = holder
        updateView(item)
        updateListener(item)
    }

    protected open fun initView() {
    }

    protected open fun updateListener(item: T) {
    }

    protected fun <V : View> view(viewId: Int = View.NO_ID): V {
        return bindViewHolder.view<V>(viewId)
    }

    abstract protected fun updateView(item: T)
}
