package btu.ninidze.stepcounter.data.network.helper

sealed class Resource<out T: Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val message: String? = null) : Resource<Nothing>()
}

@Suppress("SwallowedException")
suspend fun <Dto : Any, Presentation : Any> Resource<Dto>.asResource(
    onSuccess: suspend (Dto) -> Presentation,
    onError: (() -> Resource<Presentation>?)? = null
): Resource<Presentation> {
    return when (this) {
        is Resource.Success -> {
            return try {
                Resource.Success(data = onSuccess.invoke(this.data))
            } catch (throwable: Throwable) {
                Resource.Error(message = "Error Occurred")
            }
        }
        is Resource.Error -> {
            if (onError != null) {
                onError.invoke()!!
            } else {
                Resource.Error(message = this.message)
            }
        }
    }
}