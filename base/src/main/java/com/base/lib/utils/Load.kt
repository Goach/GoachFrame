package com.base.lib.utils

import android.support.annotation.CallSuper
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.base.lib.R
import com.base.lib.api.IProgressDialog
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * RecyclerView分页加载
 */
object Load {
    interface More {
        val mRecyclerView: RecyclerView
        var isMoreLoading: Boolean
        var haveNext: Boolean

        fun initMore(onNextPageListener: () -> Unit) {
            val layoutManager = mRecyclerView.layoutManager

            if (layoutManager is LinearLayoutManager) {
                mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    var oldScrollState: Int = 0

                    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                        oldScrollState = newState
                    }

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        try {
                            val visibleItemCount = layoutManager.childCount
                            val totalItemCount = layoutManager.itemCount
                            val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                            if (haveNext && scrollState() && !isMoreLoading && totalItemCount > 0 &&
                                    isLast(totalItemCount, visibleItemCount, firstVisibleItem)) {
                                isMoreLoading = true
                                onNextPageListener()
                            }
                        } catch(e: Exception) {
                            Timber.d(e, "")
                        }
                    }

                    private fun isLast(totalItemCount: Int, visibleItemCount: Int, firstVisibleItem: Int): Boolean {
                        return totalItemCount - visibleItemCount - firstVisibleItem <= 3
                    }

                    private fun scrollState(): Boolean {
                        return RecyclerView.SCROLL_STATE_IDLE != oldScrollState
                    }
                })
            }
        }

        fun moreData(list: List<*>)
    }

    interface Refresh : IProgressDialog {
        val srl: SwipeRefreshLayout

        fun initRefresh(listener: SwipeRefreshLayout.OnRefreshListener) {
            srl.isEnabled = true
            srl.setColorSchemeResources(R.color.colorAccent)
            srl.setOnRefreshListener(listener)
        }

        @CallSuper fun sendRefreshEvent(onRefresh:(() ->Unit)? =null) {
            srl.post {
                showLoading()
                if(onRefresh!=null)
                    onRefresh()
            }
        }

        fun onRefreshFinish() {
            srl.isRefreshing = false
        }

        fun refreshData(list: List<*>)

        override fun showLoading() {
            srl.isRefreshing = true
        }

        override fun dismissLoading() {
            srl.isRefreshing = false
        }
    }
}