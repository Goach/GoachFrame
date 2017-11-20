package com.base.lib.api

import com.base.lib.adapter.AbsDelegationAdapter

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
interface ListViewData<T> {
    val list:MutableList<T>
    val adapter:AbsDelegationAdapter<T>
    fun clearData():ListViewData<T>{
        list.clear()
        notifyDataSetChanged()
        return this
    }
    fun setData(array: List<T>):ListViewData<T>{
        list.clear()
        list.addAll(array)
        return this
    }

    fun setDataAndRefresh(array: List<T>): ListViewData<T> {
        setData(array)
        notifyDataSetChanged()
        return this
    }

    fun addData(data: List<T>): ListViewData<T> {
        list.addAll(data)
        return this
    }

    fun remove(index: Int): ListViewData<T> {
        list.removeAt(index)
        notifyItemRemoved(index)
        return this
    }

    fun remove(item: T) = remove(list.indexOf(item))

    fun inserted(item: T): ListViewData<T> {
        list.add(item)
        notifyItemInserted(list.size - 1)
        return this
    }

    fun inserted(index: Int, item: T): ListViewData<T> {
        list.add(index, item)
        notifyItemInserted(index)
        return this
    }

    fun notifyDataSetChanged() = adapter.notifyDataSetChanged()

    fun notifyItemChanged(index: Int) = adapter.notifyItemChanged(index)

    fun notifyItemInserted(index: Int) = adapter.notifyItemInserted(index)

    fun notifyItemRemoved(index: Int) = adapter.notifyItemRemoved(index)
}