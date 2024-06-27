package com.example.myapplication.event

import com.example.myapplication.common.util.ThreadPoolManager

object GlobalInitEvent {

    private val initUnit = arrayListOf<Runnable>()

    fun addUnit(run : Runnable){
        initUnit.add(run)
    }

    fun run(){
        initUnit.forEach { it ->
            ThreadPoolManager.getInstance().addTask("init", it)
        }
    }

}