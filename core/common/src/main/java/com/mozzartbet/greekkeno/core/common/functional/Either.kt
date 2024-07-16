package com.mozzartbet.greekkeno.core.common.functional

sealed class Either<out E, out R> {

    data class Error<out E>(val data: E) : Either<E, Nothing>()

    data class Result<out R>(val data: R) : Either<Nothing, R>()

    val isResult get() = this is Result<R>
    val isError get() = this is Error<E>

    fun error(): E = (this as Error<E>).data

    fun result(): R = (this as Result<R>).data

    fun onResult(action: (R) -> Any): Either<E, R> {
        if (this is Result) action(data)
        return this
    }

    suspend fun onResult(action: suspend (R) -> Any): Either<E, R> {
        if (this is Result) action(data)
        return this
    }

    fun onError(action: (E) -> Any): Either<E, R> {
        if (this is Error) action(data)
        return this
    }
}
