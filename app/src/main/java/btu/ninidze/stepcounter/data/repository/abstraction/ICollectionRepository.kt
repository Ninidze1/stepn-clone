package btu.ninidze.stepcounter.data.repository.abstraction

import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.ui.GetMoney
import btu.ninidze.stepcounter.ui.User
import btu.ninidze.stepcounter.ui.auth.CreateUser
import btu.ninidze.stepcounter.ui.details.model.BuySneakers

interface ICollectionRepository {

    suspend fun getSneakerCollection(): Resource<List<CollectionItem>>
    suspend fun getGiftedSneakers(): Resource<List<CollectionItem>>
    suspend fun getSneakersById(tokenId: String): Resource<CollectionItem>
    suspend fun createUser(user: CreateUser): Resource<Unit>
    suspend fun buySneakers(request: BuySneakers): Resource<User>
    suspend fun getUserById(userId: String): Resource<User>
    suspend fun getUsersCollection(userId: String): Resource<List<CollectionItem>>
    suspend fun getMoney(request: GetMoney): Resource<User>

}