package com.base.lib.utils

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 添加头部和尾部功能
 */
class AdapterExtendsHelper {
    private val mHeaderViews: SparseArrayCompat<View> = SparseArrayCompat()
    private val mFooterViews: SparseArrayCompat<View> = SparseArrayCompat()
    fun addHeaderView(view:View){
        if(mHeaderViews.indexOfKey(getViewType(view))<0)
            mHeaderViews.put(getViewType(view),view)
    }
    fun removeHeaderView(view:View){
        if(mHeaderViews.indexOfKey(getViewType(view))>=0)
            mHeaderViews.remove(getViewType(view))
    }
    fun addFooterView(view:View){
        if(mFooterViews.indexOfKey(getViewType(view))<0)
            mFooterViews.put(getViewType(view),view)
    }
    fun headerSize() = mHeaderViews.size()
    fun footerSize() = mFooterViews.size()
    fun isHeaderView(position:Int) = position < headerSize()
    fun isFooterView(position:Int,count:Int) = position >= count - footerSize() && position < count
    fun isItemHeaderType(viewType:Int) = mHeaderViews.indexOfKey(viewType)>= 0
    fun isItemFooterType(viewType:Int) = mFooterViews.indexOfKey(viewType)>=0
    fun getItemHeaderView(viewType: Int) = mHeaderViews[viewType]!!
    fun getItemFooterView(viewType: Int) = mFooterViews[viewType]!!
    fun itemHeaderValueOf(position: Int):View? = mHeaderViews.valueAt(position)
    fun itemFooterValueOf(position: Int):View?{
        Timber.d("size====${mFooterViews.size()}")
        return mFooterViews.valueAt(position)
    }
    fun getViewType(view:View):Int = view.hashCode()

}