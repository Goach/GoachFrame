package com.utils.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.SimpleTarget
import java.io.File

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 加载图片封装
 */
object GlideUtils {

    fun loadNormalImageView(context: Context,imageView: ImageView,url:String,loadingResId:Int ? = Color.GRAY,
                            errorResId:Int? =Color.GRAY,listener: RequestListener<Drawable>? = null){
        baseGlide(context)
                .load(url)
                .listener(listener)
                .placeholder(loadingResId!!.toInt())
                .error(errorResId!!.toInt())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
                .into(imageView)
    }
    fun loadCircleImageView(context: Context,imageView: ImageView,url:String,loadingResId:Int ? = Color.GRAY,errorResId:Int? =Color.GRAY){
        baseGlide(context)
                .load(url)
                .placeholder(loadingResId!!.toInt())
                .error(errorResId!!.toInt())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop()
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(imageView)
    }
    fun loadBorderCircleImageView(context: Context,imageView: ImageView,url:String,loadingResId:Int ? = Color.GRAY,errorResId:Int? =Color.GRAY){
        baseGlide(context)
                .load(url)
                .placeholder(loadingResId!!.toInt())
                .error(errorResId!!.toInt())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(bitmapTransform(BorderCircleTransformation(AppBridge.AppContext(), 2, Color.WHITE)))
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(imageView)
    }
    fun loadNormalFileImageView(context: Context,imageView:ImageView,localFile: File){
        baseGlide(context)
                .load(localFile.absolutePath)
                .priority(Priority.HIGH)
                .into(imageView)
    }

    fun loadBitmap(context: Context,url: String, simpleTarget: SimpleTarget<Bitmap>) {
        baseGlide(context)
                .asBitmap()
                .load(url)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate()
                .into(simpleTarget)
    }
    private fun baseGlide(context: Context): GlideRequests {
        return GlideApp.with(context)
    }
}