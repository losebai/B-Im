package com.example.myapplication

import android.app.Application
//import org.noear.solon.Solon


class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        if(Solon.app() == null){
//            Solon.start(this.javaClass, arrayOf())
//        }
    }
}

