package com.goach.client.ui.realm

import android.widget.ScrollView
import com.base.lib.fragment.BaseLoaderFragment
import com.base.lib.utils.bindPresenter
import com.data.lib.db.User
import com.goach.client.R
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_realm_layout.*
import org.jetbrains.anko.onClick

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
class RealmFragment:BaseLoaderFragment<RealmPresenter>(),RealmView{
    override fun createLoader() = bindPresenter(RealmPresenter())
    override fun layoutResID() = R.layout.fragment_realm_layout
    override fun initListener() {
        super.initListener()
        btnSyncAddOrUpdate.onClick {
            presenter?.syncAddOrUpdateItem()
        }
        btnAsyncAddOrUpdate.onClick {
            presenter?.asyncAddOrUpdateItem()
        }
        btnAsyncQuery.onClick {
            presenter?.asyncQueryItem()
        }
        btnRemove.onClick {
            presenter?.removeItem()
        }
        btnRemoveAll.onClick {
            presenter?.removeAll()
        }
        btnCity.onClick {
            presenter?.cityAll()
        }
    }
    override fun UpdateUI(results: RealmResults<User>) {
        tvShow.text = results.joinToString(";\n")
        tvScrollView.post {
            tvScrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

}