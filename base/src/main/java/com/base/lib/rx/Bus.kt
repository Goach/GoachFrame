package com.base.lib.rx

import com.base.lib.activity.BaseActivity
import com.base.lib.fragment.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import timber.log.Timber

/**
 * Goach All Rights Reserved
 * User: Goach
 * Email: goach0728@gmail.com
 * 写一个背压处理的RxBus
 */
object RxBus{
    private val bus:FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()
    fun post(t:Any){
        bus.onNext(t)
    }
    fun toFlowable(): FlowableProcessor<Any> {
        return bus
    }
}

class Bus(val sub: CompositeDisposable){
    fun <T : Event> subscribe(classType: Class<T>, onNext:((res: T) ->Unit)?=null,
                              onError:((e: Throwable) ->Unit)?=null,
                              onComplete:(() ->Unit)?=null) {
        val subscribe = RxBus.toFlowable()
                .ofType(classType)
                .subscribe({
                    if(onNext!=null) onNext(it)
                },{
                    if(onError!=null) onError(it)
                },{
                    if(onComplete!=null) onComplete()
                })
        sub.add(subscribe)
    }
}

fun BaseActivity.bus(init: Bus.() -> Unit): Bus {
    val bus = Bus(sub)
    bus.init()
    return bus
}

fun BaseFragment.bus(init: Bus.() -> Unit): Bus {
    val bus = Bus(sub)
    bus.init()
    return bus
}


open class Event
class LogoutEvent : Event()

