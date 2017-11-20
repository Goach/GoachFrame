package com.base.lib.utils

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.base.lib.R
import com.base.lib.api.IStateView
import org.jetbrains.anko.find
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater
import org.jetbrains.anko.onClick

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 实现IStateView，实现相应的方法,kotlin的apply应用就是通过建造者模式
 * mBindView  比如RecyclerView
 * 这种写法有问题不能覆盖在上面
 */
class StatusViewController(private var mContext: Context, private var mBindView:View) : IStateView {
    private var mParentView:ViewGroup? = null
    private var mCurrentIndex = 0
    private var mEmptyView: View? = null
    private var mErrorView:View? = null
    private var mNetErrorView:View? = null
    private var mBindViewParams:ViewGroup.LayoutParams? = null

    private var netErrorLayoutResId:Int = 0
    private var errorLayoutResId:Int = 0
    private var emptyLayoutResId:Int = 0
    private var mEmptyStatus:StatusInfo = StatusInfo(R.drawable.icon_empty,mContext.getString(R.string.LoadingController_empty),mContext.getString(R.string.LoadingController_empty_retry))
    private var mErrorStatus:StatusInfo = StatusInfo(R.drawable.icon_error,mContext.getString(R.string.LoadingController_error),mContext.getString(R.string.LoadingController_error_retry))
    private var mNetErrorStatus:StatusInfo = StatusInfo(R.drawable.icon_net_error,mContext.getString(R.string.LoadingController_net_error),mContext.getString(R.string.LoadingController_net_error_retry))
    private var mEmptyBtnRetryListener:BtnRetryListener? = null
    private var mNetErrorBtnRetryListener:BtnRetryListener? = null
    private var mErrorBtnRetryListener:BtnRetryListener? = null
    init {
        mBindViewParams = mBindView.layoutParams
        mParentView = if(mBindView.parent != null)
            mBindView.parent as ViewGroup
        else
            mBindView.rootView.find(android.R.id.content)
        val childCount = mParentView!!.childCount
        (0 until childCount)
                .filter { mParentView!!.getChildAt(it) == mBindView }
                .forEach { mCurrentIndex = it }
    }

    override fun showErrorView() {
        if(mErrorView!=null){
            showView(mErrorView!!)
            return
        }
        if(errorLayoutResId>0){
            mErrorView = mContext.layoutInflater.inflate(errorLayoutResId,mParentView,false)
        }else{
            mErrorView = mContext.layoutInflater.inflate(R.layout.error,mParentView,false)
            updateDefaultView(mErrorView!!,mErrorStatus,mErrorBtnRetryListener)
        }
        showView(mErrorView!!)
    }
    override fun showNetErrorView() {
        if(mNetErrorView!=null){
            showView(mNetErrorView!!)
            return
        }
        if(errorLayoutResId>0){
            mNetErrorView = mContext.layoutInflater.inflate(netErrorLayoutResId,mParentView,false)
        }else{
            mNetErrorView = mContext.layoutInflater.inflate(R.layout.error,mParentView,false)
            updateDefaultView(mNetErrorView!!,mNetErrorStatus,mNetErrorBtnRetryListener)
        }
        showView(mNetErrorView!!)
    }

    override fun showEmptyView() {
        if(mEmptyView!=null){
            showView(mEmptyView!!)
            return
        }
        if(emptyLayoutResId>0){
            mEmptyView = mContext.layoutInflater.inflate(emptyLayoutResId,mParentView,false)
        }else{
            mEmptyView = mContext.layoutInflater.inflate(R.layout.error,mParentView,false)
            updateDefaultView(mEmptyView!!,mEmptyStatus,mEmptyBtnRetryListener)
        }
        showView(mEmptyView!!)
    }

    override fun dismissStatusView() {
        showView(mBindView)
    }
    private fun showView(statusView:View){
        if(mParentView!!.getChildAt(mCurrentIndex) != statusView){
            statusView.parent?:(statusView.parent as? ViewGroup)?.removeView(statusView)
            mParentView!!.removeViewAt(mCurrentIndex)
            mParentView!!.addView(statusView,mCurrentIndex,mBindViewParams)
        }
    }
    private fun updateDefaultView(updateView:View,statusInfo:StatusInfo,listener:BtnRetryListener?=null){
        val errorView = updateView.find<ImageView>(R.id.iv_error)
        val msgView = updateView.find<TextView>(R.id.tv_errorMessage)
        val btnRetryView = updateView.find<TextView>(R.id.btn_retry)
        if(statusInfo.icon>0){
            errorView.show()
            errorView.imageResource = statusInfo.icon
        }else errorView.hide()
        if(!statusInfo.msg.isNullOrEmpty()){
            msgView.show()
            msgView.text = statusInfo.msg
        }else msgView.hide()
        if(!statusInfo.btnText.isNullOrEmpty()){
            btnRetryView.show()
            btnRetryView.text = statusInfo.btnText
            btnRetryView.onClick {
                listener?.onRetryClick(btnRetryView)
            }
        }else btnRetryView.hide()
    }

    fun setErrorLayoutResId(resId: Int):StatusViewController{
        this.errorLayoutResId = resId
        return this
    }
    fun setNetErrorLayoutResId(resId: Int):StatusViewController{
        this.netErrorLayoutResId = resId
        return this
    }
    fun setEmptyLayoutResId(resId: Int):StatusViewController{
        this.emptyLayoutResId = resId
        return this
    }
    fun setEmptyStatusInfo(statusInfo:StatusInfo):StatusViewController{
        this.mEmptyStatus = statusInfo
        return this
    }
    fun setErrorStatusInfo(statusInfo:StatusInfo):StatusViewController{
        this.mErrorStatus = statusInfo
        return this
    }
    fun setNetErrorStatusInfo(statusInfo:StatusInfo):StatusViewController{
        this.mNetErrorStatus = statusInfo
        return this
    }
    fun setEmptyBtnClickListener(listener:BtnRetryListener):StatusViewController{
        this.mEmptyBtnRetryListener = listener
        return this
    }
    fun setErrorBtnClickListener(listener:BtnRetryListener):StatusViewController{
        this.mErrorBtnRetryListener = listener
        return this
    }
    fun setNetErrorBtnClickListener(listener:BtnRetryListener):StatusViewController{
        this.mNetErrorBtnRetryListener = listener
        return this
    }
    interface BtnRetryListener{
        fun onRetryClick(view:View)
    }
    class StatusInfo(val icon:Int,val msg:CharSequence?,val btnText:CharSequence?)
}