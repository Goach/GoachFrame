package com.base.lib.fragment

import android.view.View
import com.base.lib.R
import kotlinx.android.synthetic.main.toolbar_title_layout.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.support.v4.act

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 默认ToolBar头部,通过BaseActivity的toolbarCustomView重写，或者
 */
class TitleFragment : BaseFragment(){
    var onBackListener:BackClickListener? = null
    override fun layoutResID()= R.layout.toolbar_title_layout
    override fun initView(view: View) {
        super.initView(view)
        tvTitle.text = act.title
    }

    override fun initListener() {
        super.initListener()
        ivBack.onClick {
            onBackListener?.onBack(ivBack)
        }
    }
    fun setTitle(title: CharSequence?) {
        tvTitle?.text = title
    }
    interface BackClickListener{
        fun onBack(view:View)
    }
}