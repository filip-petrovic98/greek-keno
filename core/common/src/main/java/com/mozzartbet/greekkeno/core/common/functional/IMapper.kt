package com.mozzartbet.greekkeno.core.common.functional

interface IMapper<F, T> {
    fun map(from: F?): T
}
