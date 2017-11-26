package com.utils.lib

import android.content.Context
import android.os.Environment
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader

/**
 * Goach All Rights Reserved
 *User: Goach
 *Email: goach0728@gmail.com
 *Des:
 */
object AssetsUtils {

    fun getStringForAssets(context: Context,name:String):String{
        try {
            val inputStream = context.assets.open(name)
            val br = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuffer()
            var len = br.readLine()
            while(len != null){
                sb.append(len)
                len = br.readLine()
            }
            inputStream.close()
            return sb.toString()
        }catch (e:FileNotFoundException){
            e.printStackTrace()
        }
        return ""
    }
    fun stringToFile(context: Context,fileName: String,saveStr:String){
        val file = File(getSaveDir(context,"City"),fileName)
        if(!file.exists()||file.length() == 0L){
            file.deleteOnExit()
            file.createNewFile()
            file.outputStream().use { it.write(saveStr.toByteArray(Charsets.UTF_8)) }
        }
    }
    private fun getSaveDir(context: Context,uniqueName:String):File{
        val cachePath: String
        val file = context.getExternalFilesDir(null)
        cachePath = if (file != null && (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable())) {
            file.path
        } else {
            context.filesDir.path
        }
        val targetFile = File(cachePath + File.separator + uniqueName)
        if(!targetFile.exists()) targetFile.mkdirs()
        return targetFile
    }
}