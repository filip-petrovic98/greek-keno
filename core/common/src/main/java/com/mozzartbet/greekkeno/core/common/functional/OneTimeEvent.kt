package com.mozzartbet.greekkeno.core.common.functional

data class OneTimeEvent<T>(private var value: T? = null) {

    fun consume(): T? {
        val toConsume = value
        value = null
        return toConsume
    }
    fun getValue(): T? {
        return value
    }
}
