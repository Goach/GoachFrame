package com.base.lib.api

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
interface IStateView {
    fun showErrorView()
    fun showNetErrorView()
    fun showEmptyView()
    fun dismissStatusView()
}