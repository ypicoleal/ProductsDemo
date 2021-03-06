package com.example.infrastructure.network

import android.util.Log
import com.example.domain.models.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ResponseHandler(private val dispatcher: CoroutineDispatcher) {

    suspend operator fun <T : Any> invoke(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                Log.e(javaClass.simpleName, "Response Error", throwable)
                when (throwable) {
                    is IOException ->  ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorBody = throwable.getErrorBody()
                        ResultWrapper.Error(code, errorBody)
                    }
                    else -> {
                        ResultWrapper.Error(null, null)
                    }
                }
            }
        }
    }
}

private fun HttpException.getErrorBody(): String {
    return response()?.errorBody()?.string() ?: ""
}

