
    package org.example.project.base

    import io.ktor.client.statement.HttpResponse
    import io.ktor.client.statement.bodyAsText
    import io.ktor.utils.io.errors.IOException
    import kmm_sample_project.composeapp.generated.resources.Res
    import kmm_sample_project.composeapp.generated.resources.error_invalid_credentials
    import kmm_sample_project.composeapp.generated.resources.error_network
    import kmm_sample_project.composeapp.generated.resources.error_unexpected
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.flow
    import kotlinx.coroutines.flow.flowOn
    import kotlinx.serialization.decodeFromString
    import kotlinx.serialization.json.Json
    import org.jetbrains.compose.resources.getString

    @Suppress("UNCHECKED_CAST")
    abstract class BaseRepository {

        suspend fun <T> safeApiCall(
            apiCall: suspend () -> Pair<HttpResponse, T>
        ): Flow<ApiState<T?>> = flow {
            try {
                emit(ApiState.Loading)

                val (response, data) = apiCall()
                val statusCode = response.status.value

                when (statusCode) {
                    in 200..299 -> {
                        emit(ApiState.Success(data))
                    }
                    400 -> {
                        val responseText: String = response.bodyAsText()
                        val errorResponse: Error = Json.decodeFromString(responseText)
                        emit(ApiState.Failure(errorResponse))
                    }
                    401 -> {
                        emit(ApiState.Failure(Error(getString(Res.string.error_invalid_credentials))))
                    }

                    else -> { // Handle Other Errors (500, etc.)
                        emit(ApiState.Failure(Error(getString(Res.string.error_unexpected)+":"+statusCode)))
                    }
                }

            } catch (e: IOException) { // Handle Network Errors
                emit(ApiState.Failure(Error(getString(Res.string.error_network))))
            } catch (e: Exception) { // Handle Other Exceptions
                emit(ApiState.Failure(Error(getString(Res.string.error_network))))
            }
        }.flowOn(Dispatchers.Default) as Flow<ApiState<T?>>

    }
