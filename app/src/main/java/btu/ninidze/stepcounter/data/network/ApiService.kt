package btu.ninidze.stepcounter.data.network

import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.ui.GetMoney
import btu.ninidze.stepcounter.ui.User
import btu.ninidze.stepcounter.ui.auth.CreateUser
import btu.ninidze.stepcounter.ui.details.model.BuySneakers
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("collection/fetch")
    suspend fun getCollection(): Response<List<CollectionItem>>

    @GET("collection/gift")
    suspend fun getGiftedSneakers(): Response<List<CollectionItem>>

    @GET("collection/{tokenId}")
    suspend fun getSneakersById (@Path("tokenId") tokenId: String): Response<CollectionItem>

    @POST("user/add")
    suspend fun createUser (@Body user: CreateUser): Response<Unit>

    @POST("user/buy")
    suspend fun buySneakers (@Body request: BuySneakers): Response<User>

    @GET("user/{userId}")
    suspend fun getUserById (@Path("userId") userId: String): Response<User>

    @GET("collection/user/{userId}")
    suspend fun getUsersSneakers (@Path("userId") userId: String): Response<List<CollectionItem>>

    @POST("user/money")
    suspend fun getMoney (@Body request: GetMoney): Response<User>
}