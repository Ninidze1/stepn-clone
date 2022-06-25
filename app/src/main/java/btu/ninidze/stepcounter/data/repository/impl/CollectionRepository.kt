package btu.ninidze.stepcounter.data.repository.impl

import btu.ninidze.stepcounter.data.network.ApiService
import btu.ninidze.stepcounter.data.network.helper.ApiHelper
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.network.helper.asResource
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.ui.User
import btu.ninidze.stepcounter.ui.auth.CreateUser
import btu.ninidze.stepcounter.ui.details.model.BuySneakers
import javax.inject.Inject

class CollectionRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiHelper: ApiHelper
) : ICollectionRepository {

    override suspend fun getSneakerCollection(): Resource<List<CollectionItem>> {
        return apiHelper
            .safeApiCall { apiService.getCollection() }
            .asResource(onSuccess = { it })
    }

    override suspend fun getGiftedSneakers(): Resource<List<CollectionItem>> {
        return apiHelper
            .safeApiCall { apiService.getGiftedSneakers() }
            .asResource(onSuccess = { it })
    }

    override suspend fun getSneakersById(tokenId: String): Resource<CollectionItem> {
        return apiHelper
            .safeApiCall { apiService.getSneakersById(tokenId) }
            .asResource(onSuccess = { it })
    }

    override suspend fun createUser(user: CreateUser): Resource<Unit> {
        return apiHelper
            .safeApiCall { apiService.createUser(user) }
    }

    override suspend fun buySneakers(request: BuySneakers): Resource<User> {
        return apiHelper
            .safeApiCall { apiService.buySneakers(request) }
            .asResource(onSuccess = { it })
    }

    override suspend fun getUserById(userId: String): Resource<User> {
        return apiHelper
            .safeApiCall { apiService.getUserById(userId) }
            .asResource(onSuccess = { it })
    }

    override suspend fun getUsersCollection(userId: String): Resource<List<CollectionItem>> {
        return apiHelper
            .safeApiCall { apiService.getUsersSneakers(userId) }
            .asResource(onSuccess = { it })
    }
}