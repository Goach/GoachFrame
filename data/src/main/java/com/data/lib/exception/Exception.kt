package com.data.lib.exception

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 处理接口返回异常
 */
class NetworkException(val msg: String = "网络错误") : Exception(msg)

open class RequestException(val code: Int, val msg: String) : Exception(msg)

class TokenExpiredException(code: Int, msg: String) : RequestException(code, msg)

class NotAuthenticationException : Exception("无登录信息")