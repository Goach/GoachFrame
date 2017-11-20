package com.base.lib.presenter

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 为Activity/Fragment绑定View提供的方法
 */
interface Presenter{
    fun attachView(attach: Any)
    fun detachView()
}