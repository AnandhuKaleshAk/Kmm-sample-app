package org.example.project.base

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Pair<HttpResponse, T>
    ): Flow<ApiState<T?>> = flow {
        try {
            emit(ApiState.Loading)

            val (response, data) = apiCall()
            val statusCode = response.status.value

            if (statusCode in 200..299) {
                emit(ApiState.Success(data))
            } else if (statusCode == 400) {
                val responseText: String = response.bodyAsText()
                val errorResponse = Error(responseText)
                emit(ApiState.Failure(errorResponse))
            }

        } catch (e: Exception) {

        }
    }.flowOn(Dispatchers.Default) as Flow<ApiState<T?>>

}
