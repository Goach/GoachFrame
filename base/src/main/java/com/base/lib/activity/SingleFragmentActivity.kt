package com.base.lib.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.base.lib.R
import com.base.lib.presenter.BasePresenter
import com.base.lib.utils.ActivityResultHelp
import com.base.lib.view.Mvp
import com.base.lib.view.PageListView
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.support.v4.act
import org.jetbrains.anko.support.v4.ctx
import timber.log.Timber
import java.io.Serializable

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
open class SingleFragmentActivity:BaseActivity() {
    open val options by lazy{ intent.getSerializableExtra("extra") as? SingleFragmentActivity.Option}
    override fun initView() {
        super.initView()
        options?.title?.let { title = it }
        val fragment = options?.fragment?.newInstance()!!
        if(options is SingleFragmentActivity.Option)
            fragment.arguments  = bundleOf(*options!!.args)
        fm.beginTransaction().replace(R.id.content,fragment).commitAllowingStateLoss()
    }
    companion object {
        fun newInstance(ctx: Context, option: SingleFragmentActivity.Option): Intent {
            return Intent(ctx, SingleFragmentActivity::class.java).putExtra("extra", option)
        }
    }
    class Option(val title: String? = null, val fragment: Class<out Fragment>, val args: Array<out Pair<String, Any>>) : Serializable

    inline fun <reified T : Fragment> Context.startFragmentActivity(title: String? = null, vararg args: Pair<String, Any>) {
        startActivity(SingleFragmentActivity.newInstance(this, SingleFragmentActivity.Option(title, T::class.java, args)))
    }

    inline fun <reified T : Fragment> Fragment.startFragmentActivity(title: String? = null, vararg args: Pair<String, Any>) {
        startActivity(SingleFragmentActivity.newInstance(ctx, SingleFragmentActivity.Option(title, T::class.java, args)))
    }

    inline fun <reified T : Fragment> Activity.startFragmentActivity(title: String? = null, vararg args: Pair<String, Any>, crossinline resultOk: (Intent) -> Unit) {
        ActivityResultHelp.start(
                this,
                SingleFragmentActivity.newInstance(this, SingleFragmentActivity.Option(title, T::class.java, args)),
                object : ActivityResultHelp.ResultAdapter() {
                    override fun onActivityResultOk(data: Intent) {
                        resultOk(data)
                    }
                }
        )
    }

    inline fun <reified T : Fragment> Fragment.startFragmentActivity(title: String? = null, vararg args: Pair<String, Any>, crossinline resultOk: (Intent) -> Unit) {
        ActivityResultHelp.start(
                act,
                SingleFragmentActivity.newInstance(ctx, SingleFragmentActivity.Option(title, T::class.java, args)),
                object : ActivityResultHelp.ResultAdapter() {
                    override fun onActivityResultOk(data: Intent) {
                        resultOk(data)
                    }
                }
        )
    }
}