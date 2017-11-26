package com.goach.client.activity

import com.base.lib.activity.SingleFragmentActivity
import com.base.lib.rx.LogoutEvent
import com.base.lib.rx.bus
import com.goach.client.R
import com.goach.client.ui.city.CityFragment
import com.goach.client.ui.glide.GlideFragment
import com.goach.client.ui.news.KnowItemFragment
import com.goach.client.ui.realm.RealmFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.onClick
/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */

class MainActivity : SingleFragmentActivity() {

    override fun layoutResID() = R.layout.activity_main
    override fun initView() {
        bus {
            subscribe(LogoutEvent::class.java,{
                //进行退出登录操作
            })
        }
    }
    override fun initListener() {
        super.initListener()
        btnKnow.onClick{
            startFragmentActivity<KnowItemFragment>("育婴知识")
        }
        btnRealm.onClick {
            startFragmentActivity<RealmFragment>("Realm使用")
        }
        btnGlide.onClick {
            startFragmentActivity<GlideFragment>("加载框")
        }
        btnCity.onClick {
            startFragmentActivity<CityFragment>("json转换为db")
        }
    }
}
