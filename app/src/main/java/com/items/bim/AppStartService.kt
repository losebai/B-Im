package com.items.bim

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.items.bim.viewmodel.MessagesViewModel

class AppStartService() : Service() {

    val sessionBinder: SessionBinder = SessionBinder()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder {
        sessionBinder.messagesViewModel?.messageService?.reconnect()
        return sessionBinder
    }


    override fun onUnbind(intent: Intent?): Boolean {
        sessionBinder.messagesViewModel?.messageService?.close()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }
}

class SessionBinder(): Binder(){

    var messagesViewModel : MessagesViewModel? = null
}