package com.base.lib.utils

import android.content.Context
import android.graphics.Point
import android.view.View
import org.jetbrains.anko.windowManager

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 提供屏幕的宽和高
 */
fun View.locationOnScreen(): IntArray {
    var loc = intArrayOf(0, 0)

    getLocationOnScreen(loc)

    return loc
}

fun Context.screenWidth(): Int {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.x
}

fun Context.screenHeight(): Int {
    val display = windowManager.defaultDisplay
    val size = Point()
    display.getSize(size)
    return size.y
}