package com.utils.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.SimpleTarget
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 加载图片封装
 */
object GlideUtils {
    fun loadNormalImageView(imageView: ImageView,url:String,loadingResId:Int ? = Color.GRAY,
                            errorResId:Int? =Color.GRAY,listener: RequestListener<String, GlideDrawable>? = null){
        baseGlide()
                .load(url)
                .listener(listener)
                .placeholder(loadingResId!!.toInt())
                .error(errorResId!!.toInt())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(imageView)
    }
    fun loadCircleImageView(imageView: ImageView,url:String,loadingResId:Int ? = Color.GRAY,errorResId:Int? =Color.GRAY){
        baseGlide()
                .load(url)
                .placeholder(loadingResId!!.toInt())
                .error(errorResId!!.toInt())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(CropCircleTransformation(AppBridge.AppContext()))
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(imageView)
    }
    fun loadBorderCircleImageView(imageView: ImageView,url:String,loadingResId:Int ? = Color.GRAY,errorResId:Int? =Color.GRAY){
        baseGlide()
                .load(url)
                .placeholder(loadingResId!!.toInt())
                .error(errorResId!!.toInt())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .bitmapTransform(BorderCircleTransformation(AppBridge.AppContext(), 2, Color.WHITE))
                .dontAnimate()
                .priority(Priority.HIGH)
                .into(imageView)
    }
    fun loadNormalFileImageView(imageView:ImageView,localFile: File){
        baseGlide()
                .load(localFile.absolutePath)
                .crossFade()
                .priority(Priority.HIGH)
                .into(imageView)
    }

    fun loadBlur(context: Context, imageView: ImageView, url: String) {
        baseGlide().load(url)
                .bitmapTransform(BlurTransformation(context, 20)).into(imageView)
    }

    fun loadBlur(context: Context, imageView: ImageView, file: File) {
        baseGlide().load(file)
                .bitmapTransform(BlurTransformation(context, 20)).into(imageView)
    }

    fun loadBlur(context: Context, imageView: ImageView, res: Int) {
        baseGlide().load(res).bitmapTransform(BlurTransformation(context, 20)).into(imageView)
    }

    fun loadBlur(context: Context, imageView: ImageView, drawable: Drawable) {
        baseGlide().load(drawable).bitmapTransform(BlurTransformation(context, 20)).into(imageView)
    }

    fun loadBlur(context: Context, imageView: ImageView, bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        baseGlide().load(baos.toByteArray()).bitmapTransform(BlurTransformation(context, 20)).into(imageView)
    }

    fun loadBitmap(url: String, simpleTarget: SimpleTarget<Bitmap>) {
        Glide.with(AppBridge.AppContext())
                .load(url)
                .asBitmap()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(simpleTarget)
    }
    fun baseGlide(): RequestManager {
        return Glide.with(AppBridge.AppContext())
    }
}