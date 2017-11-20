package com.base.lib.adapter

import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 */
abstract class SimpleAdapter<T>(resource: Int) : SingleTypeAdapter<T>(resource) {

    fun setText(@IdRes viewId: Int, text: CharSequence): TextView {
        val textView = textView(viewId)
        textView.text = text
        return textView
    }

    fun setText(@IdRes viewId: Int, @StringRes resid: Int): TextView {
        val textView = textView(viewId)
        textView.setText(resid)
        return textView
    }

    fun textView(@IdRes viewId: Int): TextView {
        return view(viewId)
    }

    fun imageView(@IdRes viewId: Int): ImageView {
        return view(viewId)
    }

    fun rootView(): View {
        return view(View.NO_ID)
    }
}
