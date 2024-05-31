package com.example.myapplication

import com.example.myapplication.common.consts.SystemApp
import com.example.myapplication.service.UserListener
import org.junit.Test
import org.noear.socketd.SocketD
import org.noear.socketd.transport.core.entity.StringEntity
import org.noear.socketd.transport.core.listener.EventListener

class SocketDTest {



    @Test
    fun test1(){
        val session =
            SocketD.createClient("${BuildConfig.SOCKET_URL}?@=bai&id=${SystemApp.UserId}")
                .listen(EventListener().doOn("hello"){ _,m ->
                    println(m)
                })
                .open();
        session.send("hello", StringEntity("123").at("测试"))
        session.close()
    }

}