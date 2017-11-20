package com.base.lib.presenter

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 创建Presenter的作用
 */
interface PresenterFactory<out P:BasePresenter<*>> {
        fun create() :P
}