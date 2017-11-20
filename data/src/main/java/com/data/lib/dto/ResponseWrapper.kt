package com.data.lib.dto

import java.io.Serializable


/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 接口返回的基类数据
 */
data class ResponseWrapper<out T>(val code:Int = -1,
                                  val msg:String = "",
                                  val version:String = "",
                                  val timestamp:Long = 0,
                                  @Transient val data: T): Serializable