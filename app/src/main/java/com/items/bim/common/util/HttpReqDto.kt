package com.items.bim.common.util

import org.noear.snack.ONode
import java.io.File

class HttpReqDto {

    /**
     * 是否使用代理
     */
     var userProxy = false

    /**
     * 目标url
     */
     var url: String? = null

    /**
     * 请求参数
     */
     var params: Map<String, String>? = null

    /**
     * body参数
     */
     var body: Map<String, String>? = null

    /**
     * 请求头
     */
     var headers: Map<String, String>? = null

    /**
     * 文件
     */
     var file: File? = null
     var uid: Long? = null
     var type: Int? = null

    private constructor()
    private constructor(
        userProxy: Boolean, url: String?,
        params: Map<String, String>?, body: Map<String, String>?,
        headers: Map<String, String>?, file: File?, uid: Long?, type: Int
    ) {
        this.userProxy = userProxy
        this.url = url
        this.params = params
        this.body = body
        this.headers = headers
        this.file = file
        this.uid = uid
        this.type = type
    }

    class Build {
        private var userProxy = false
        private var url: String? = null
        private var params: MutableMap<String, String>? = null
        private var body: MutableMap<String, String>? = null
        private var headers: MutableMap<String, String>? = null
        private var file: File? = null
        private var uid: Long? = null
        private var type = 0
        fun setUserProxy(userProxy: Boolean): Build {
            this.userProxy = userProxy
            return this
        }

        fun setUrl(url: String?): Build {
            this.url = url
            return this
        }

        fun addHeaders(headers: Map<String, String>?): Build {
            if (null == this.headers) {
                this.headers = HashMap()
            }
            this.headers!!.putAll(headers!!)
            return this
        }

        fun addHeaders(headerName: String, headerValue: String): Build {
            if (null == headers) {
                headers = HashMap()
            }
            headers!![headerName] = headerValue
            return this
        }

        fun addHeadersObj(headers: Map<String, Any>): Build {
            val map: MutableMap<String, String> = HashMap(headers.size)
            for ((key, value) in headers) {
                map[key] = value.toString()
            }
            addHeaders(map)
            return this
        }

        fun addParams(params: Map<String, String>?): Build {
            if (null == this.params) {
                this.params = HashMap()
            }
            this.params!!.putAll(params!!)
            return this
        }

        fun addParams(paramName: String, paramValue: String): Build {
            if (null == params) {
                params = HashMap()
            }
            params!![paramName] = paramValue
            return this
        }

        fun addParamsObj(headers: Map<String, Any?>): Build {
            val map: MutableMap<String, String> = HashMap(headers.size)
            for ((key, value) in headers) {
                if (null == value) {
                    map[key] = ""
                } else {
                    map[key] = value.toString()
                }
            }
            addParams(map)
            return this
        }

        fun body(body: Any): Build {
            if (null == this.body) {
                this.body = HashMap()
            }
            val oNode = ONode.load(body)
            oNode.nodeData().`object`().forEach(){
                this.body!![it.key] = it.value.toString()
            }
            return this
        }

        fun addBody(body: Map<String, String>?): Build {
            if (null == this.body) {
                this.body = HashMap()
            }
            this.body!!.putAll(body!!)
            return this
        }



        fun addBody(bodyName: String, bodyValue: String): Build {
            if (null == body) {
                body = HashMap()
            }
            body!![bodyName] = bodyValue
            return this
        }

        fun addBodyObj(headers: Map<String, Any>): Build {
            val map: MutableMap<String, String> = HashMap(headers.size)
            for ((key, value) in headers) {
                map[key] = value.toString()
            }
            addBody(map)
            return this
        }

        fun uid(uid: Long?): Build {
            this.uid = uid
            return this
        }

        fun type(type: Int): Build {
            this.type = type
            return this
        }

        fun setFile(file: File?): Build {
            this.file = file
            return this
        }

        fun build(): HttpReqDto {
            return HttpReqDto(userProxy, url, params, body, headers, file, uid, type)
        }
    }

    companion object {
        fun toBuild(): Build {
            return Build()
        }
    }
}
