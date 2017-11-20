package com.goach.client.ui.news

import com.base.lib.presenter.SimpleListPresenter
import com.base.lib.utils.bindTo
import com.base.lib.utils.isNullOrEmpty
import com.base.lib.utils.subscribeWith
import com.data.lib.dto.ArticleListResponse
import com.data.lib.model.AppModel
import io.reactivex.observers.DisposableObserver
import java.util.*

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
class KnowItemPresenter : SimpleListPresenter<ArticleListResponse, KnowItemFragment>() {
    override fun createObservable() = AppModel.articleList(page,19)

    override fun initHandler() {
        super.initHandler()
    }
    override fun onRefreshFinish(res: ArticleListResponse) {
        super.onRefreshFinish(res)
        if(res.data.isNullOrEmpty()){
            mvpView.mStatusView.showEmptyView()
            return
        }
        mvpView.list.clear()
        mvpView.addData(listOf(ArticleListResponse.Item(0,0,"","","","","", Date(0),1)))
        mvpView.addData(res.data)
        mvpView.notifyDataSetChanged()
    }

    override fun onPageFinish(res: ArticleListResponse) {
        super.onPageFinish(res)
        mvpView.moreData(res.data)
    }
}