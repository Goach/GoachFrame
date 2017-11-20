package com.data.lib.dto

import java.io.Serializable
import java.util.*

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
open class BaseResponse:Serializable

open class PageResponse : BaseResponse() {
    var page = 0
    var size = 0
    var total = 0
}
open class LoginResponse:BaseResponse()

class ArticleListResponse : PageResponse() {
    val data: List<Item> = emptyList()

    class Item(
            val id: Long,
            val category_id: Long,
            val keyword: String,
            val category_name: String,
            val title: String,
            val image: String,
            val desp: String,
            val create_at: Date,
            val type:Int = 0,
            var isNice:Boolean = false
    ) : Serializable
    class Title(var id:Long,var title: String):Serializable
}