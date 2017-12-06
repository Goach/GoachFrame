package com.base.lib.utils

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.util.SparseArrayCompat
import com.base.lib.activity.BridgeActivity
import org.jetbrains.anko.support.v4.act
import java.util.concurrent.atomic.AtomicInteger

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 封装onActivityResult的方法
 */
object ActivityResultHelp {
    private val req = AtomicInteger(1)
    private val resultArray = SparseArrayCompat<Result>()
    fun start(activity:Activity,intent: Intent,result:Result){
        val requestCode = findRequest()
        resultArray.put(requestCode,result)
        activity.startActivity(Intent(activity,BridgeActivity::class.java).putExtra("intent",intent).putExtra("requestCode",requestCode))
    }
    fun onActivityResult(requestCode:Int,resultCode:Int,data:Intent?){
        val result = resultArray.get(requestCode)
        if(result!=null){
            resultArray.remove(resultCode)
            result.onActivityResultAll(resultCode,data)
            when(resultCode){
                Activity.RESULT_OK -> result.onActivityResultOk(data!!)
            }
        }
    }
    fun findRequest():Int{
       val request =  req.andIncrement
        if(request == 0xFFF){
            req.set(1)
        }
        return request
    }
    interface Result{
        fun onActivityResultAll(resultCode:Int,data:Intent?)
        fun onActivityResultOk(data:Intent)
    }
    open class ResultAdapter : Result {
        override fun onActivityResultAll(resultCode: Int, data: Intent?) {
        }

        override fun onActivityResultOk(data: Intent) {
        }
    }
}
fun Activity.start(intent: Intent, resultOk: (Intent) -> Unit?) {
    ActivityResultHelp.start(this, intent, object : ActivityResultHelp.ResultAdapter() {
        override fun onActivityResultOk(data: Intent) {
            resultOk(data)
        }
    })
}
fun Activity.start(intent: Intent, result: ActivityResultHelp.Result) {
    ActivityResultHelp.start(this, intent, result)
}

fun Fragment.start(intent: Intent, resultOk: (Intent) -> Unit?) {
    act.start(intent, resultOk)
}

fun Fragment.start(intent: Intent, result: ActivityResultHelp.Result) {
    act.start(intent, result)
}