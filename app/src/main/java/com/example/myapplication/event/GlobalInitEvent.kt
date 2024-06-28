package com.example.myapplication.event

import com.example.myapplication.common.util.ThreadPoolManager

object GlobalInitEvent {

    private val initUnit = arrayListOf<Runnable>()

    fun addUnit(run : Runnable){
        initUnit.add(run)
    }

    fun run(){
        while (initUnit.isNotEmpty()){
            ThreadPoolManager.getInstance().addTask("init", initUnit.removeAt(0))
        }
    }
}