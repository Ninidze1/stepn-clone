package btu.ninidze.stepcounter.data.network.helper

import retrofit2.Response
import javax.inject.Inject

open class ApiHelper @Inject constructor() {

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>) : Resource<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(response.message())
            }
        } catch (throwable: Throwable) {
            Resource.Error(throwable.message)
        }
    }

}