package com.goach.client.ui.news

import android.view.View
import com.base.lib.adapter.AdapterDelegate
import com.base.lib.adapter.SimpleAdapter
import com.base.lib.fragment.SingleTypePageListFragment
import com.base.lib.rx.LogoutEvent
import com.base.lib.rx.RxBus
import com.base.lib.utils.bindPresenter
import com.base.lib.view.LoginView
import com.data.lib.dto.ArticleListResponse
import com.goach.client.R

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
open class KnowItemFragment : SingleTypePageListFragment<KnowItemPresenter,Any>(), LoginView {
    override fun createLoader()  = bindPresenter(KnowItemPresenter())
    override fun createAdapterDelegate() = null
    override fun createAdapterDelegate2():Array<AdapterDelegate<Any>> = arrayOf(TitleAdapter(), Adapter())
    override fun initView(view: View) {
        super.initView(view)
        mStatusView.showEmptyView()
    }
    override fun onItemClick(position: Int, item: Any) {
        RxBus.post(LogoutEvent())
    }
    open class TitleAdapter : SimpleAdapter<Any>(R.layout.fragment_know_item_header) {
        override fun isForViewType(item: Any) = item is ArticleListResponse.Item && item.type ==1

        override fun updateView(item: Any) {
        }
    }
    open class Adapter : SimpleAdapter<Any>(R.layout.index_know_item_layout) {
        override fun isForViewType(item: Any) = item is ArticleListResponse.Item && item.type ==0

        override fun updateView(item: Any) {
            val newItem = item as ArticleListResponse.Item
           // GlideUtils.loadNormalImageView(view<SquareImageView>(R.id.sdvImage),item.image)
            setText(R.id.tvTitle,newItem.title)
            setText(R.id.tvDes,newItem.desp)
        }
        override fun updateListener(item: Any) {

        }
    }
}