    package org.example.project.base


    import kotlinx.coroutines.flow.Flow
    import kotlinx.coroutines.flow.transform
    import kotlinx.serialization.SerialName
    import kotlinx.serialization.Serializable

    // Sealed class to represent different states of an API call.
    sealed class ApiState<out T> {

        data class Success<out T>(val data: T) : ApiState<T>()
        data class Failure(val msg: Error?) : ApiState<Error>()
        object Loading : ApiState<Nothing>()

        override fun toString(): String {
            return when (this) {
                is Success<*> -> "Success $data"
                is Failure -> "Failure $msg"
                Loading -> "Loading"
            }
        }
    }

    @Serializable
    data class Error(
        @SerialName("error")
       var  error:String
    )



    // Extension function to perform an action when the API call is successful.
    fun <T> Flow<ApiState<T>>.doOnSuccess(action: suspend (T) -> Unit): Flow<ApiState<T>> =
        transform { result ->
            if (result is ApiState.Success) {
                action(result.data)
            }
            return@transform emit(result)
        }

    // Extension function to perform an action when the API call fails.
    fun <T> Flow<ApiState<T>>.doOnFailure(action: suspend (Error?) -> Unit): Flow<ApiState<T>> =
        transform { result ->
            if (result is ApiState.Failure) {
                action(result.msg)
            }
            return@transform emit(result)
        }

    // Extension function to perform an action when the API call is in the loading state.
    fun <T> Flow<ApiState<T>>.doOnLoading(action: suspend () -> Unit): Flow<ApiState<T>> =
        transform { result ->
            if (result is ApiState.Loading) {
                action()
            }
            return@transform emit(result)
        }