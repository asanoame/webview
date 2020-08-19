package com.xiaoyu.webview.utils

import java.lang.RuntimeException
import java.util.*

/**
 * XiaoYu
 * 2020/8/20 00:09
 */
object AutoServiceLoader {
    fun <S> load(clazz: Class<S>): S {
        val iterator = ServiceLoader.load(clazz).iterator()
        if (iterator.hasNext()) {
            return iterator.next()
        } else {
            throw ServiceImplNotFoundException(clazz.simpleName)
        }
    }

    class ServiceImplNotFoundException(serviceName: String) :
        RuntimeException("$serviceName is not implemented.")


}