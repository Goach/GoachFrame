package com.base.lib.api
/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
interface IProgressDialog {
    fun showLoading()
    fun dismissLoading()
    fun updateNextPage(haveNext:Boolean)
}
