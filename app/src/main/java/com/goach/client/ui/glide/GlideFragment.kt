package com.goach.client.ui.glide

import com.base.lib.fragment.BaseLoaderFragment
import com.base.lib.utils.bindPresenter
import com.goach.client.R

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
class GlideFragment : BaseLoaderFragment<GlidePresenter>() {
    override fun createLoader() = bindPresenter(GlidePresenter())
    override fun layoutResID() = R.layout.fragment_glide
    override fun initListener() {
        super.initListener()
        showLoading()
    }
}