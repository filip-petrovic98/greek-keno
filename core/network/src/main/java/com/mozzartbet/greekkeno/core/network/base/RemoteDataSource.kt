package com.mozzartbet.greekkeno.core.network.base

import com.mozzartbet.greekkeno.core.common.functional.Either
import com.mozzartbet.greekkeno.core.common.functional.IMapper
import retrofit2.Response

internal abstract class RemoteDataSource {

    protected suspend fun <T, R> getResult(
        mapper: IMapper<T, R>,
        call: suspend () -> Response<T>
    ): Either<Exception, R> {
        val response = try {
            call.invoke()
        } catch (exception: Exception) {
            return Either.Error(exception)
        }
        return handleResponse(response, mapper)
    }

    private fun <T, R> handleResponse(
        response: Response<T>,
        mapper: IMapper<T, R>
    ): Either<Exception, R> {
        val statusCode = response.code()

        if (statusCode < 200 || statusCode >= 400) {
            return Either.Error(Exception(statusCode.toString()))
        }
        return Either.Result(mapper.map(response.body()!!))
    }
}
