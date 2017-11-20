package com.base.lib.api

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
interface OnItemClickListener<T> {
    fun onItemClick(position: Int, item: T)
}

interface OnItemLongClickListener<T> {
    fun onItemLongClick(position: Int, item: T): Boolean
}