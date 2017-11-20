package com.base.lib.utils

import android.content.res.ColorStateList
import android.support.v4.view.ViewCompat
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.math.BigDecimal
import android.support.v4.app.Fragment as SupportFragment
/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
fun <T : View> T.show(): T {
    visibility = View.VISIBLE
    return this
}

fun <T : View> T.hide(): T {
    visibility = View.GONE
    return this
}

fun <T : View> T.invisible(): T {
    visibility = View.INVISIBLE
    return this
}

fun <T : View> T.toggleVisibility(): T = if (visibility == View.VISIBLE) hide() else show()

fun <T : View> T.addTo(view: ViewGroup): T {
    view.addView(this)
    return this
}

fun <T : View> T.addTo(view: ViewGroup, index: Int): T {
    view.addView(this, index)
    return this
}

fun VerifyNotEmpty(vararg v: TextView): Boolean {
    for (item in v) {
        if (TextUtils.isEmpty(item.text)) {
            return false
        }
    }

    return true
}

fun hasNotEmpty(vararg v: TextView) = v.none { TextUtils.isEmpty(it.text) }

val <T : TextView> T.string get() = text.toString()

val <T : TextView> T.double get() = try {
    BigDecimal(string).setScale(2, BigDecimal.ROUND_DOWN).toDouble()
} catch(e: Exception) {
    0.0
}

fun <T : View> T.setBackgroundTint(color: Int): T {

    ViewCompat.setBackgroundTintList(this, ColorStateList.valueOf(color))

    return this
}
