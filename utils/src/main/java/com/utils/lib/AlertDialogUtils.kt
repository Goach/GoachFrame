package com.utils.lib

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.widget.TextView
import org.jetbrains.anko.find
import org.jetbrains.anko.layoutInflater

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 */
object AlertDialogUtils {
    private var mAlertDialog:AlertDialog? = null
    @SuppressLint("InflateParams")
    fun showProgressDialog(context: Context,tip:String = ""){
        if(mAlertDialog==null)
            mAlertDialog = AlertDialog.Builder(context,R.style.ProgressDialog).create()
        val loadView = context.layoutInflater.inflate(R.layout.alert_progress,null)
        mAlertDialog!!.setView(loadView,0,0,0,0)
        mAlertDialog!!.setCanceledOnTouchOutside(false)
        mAlertDialog!!.show()
        if(TextUtils.isEmpty(tip).not())
        loadView.find<TextView>(R.id.tv_tip).text = tip
    }
    fun dismiss(){
        if(mAlertDialog!=null&&mAlertDialog!!.isShowing)
            mAlertDialog?.dismiss()
    }
}