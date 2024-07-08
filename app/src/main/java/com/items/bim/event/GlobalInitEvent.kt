package com.items.bim.event

import com.items.bim.common.util.ThreadPoolManager
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

object GlobalInitEvent {

    private val initUnit = arrayListOf<Runnable>()

    private val array = ArrayList<Future<*>?>()

    fun addUnit(run : Runnable){
        initUnit.add(run)
    }

    var onFinish : () -> Unit = {}

    fun run(){
        while (initUnit.isNotEmpty()){
            array.add(ThreadPoolManager.getInstance().addTask("init", initUnit.removeAt(0)))
        }
        try {
            for (f:Future<*>? in array){
                f?.get(100, TimeUnit.SECONDS)
            }
            onFinish()
        }catch (e: Exception){
            e.printStackTrace()
        }
        array.clear()
    }
}