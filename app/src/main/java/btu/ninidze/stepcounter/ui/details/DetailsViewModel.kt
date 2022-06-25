package btu.ninidze.stepcounter.ui.details

import androidx.lifecycle.*
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IDataStoreRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.ui.User
import btu.ninidze.stepcounter.ui.auth.CreateUser
import btu.ninidze.stepcounter.ui.details.model.BuySneakers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val collectionRepository: ICollectionRepository,
    private val firebaseRepository: IFirebaseRepository,
    dataStoreRepository: IDataStoreRepository
): ViewModel() {

    private var _selectedSneaker = MutableLiveData<Resource<CollectionItem>>()
    val selectedSneaker: LiveData<Resource<CollectionItem>> = _selectedSneaker

    private var _buySneakers = MutableLiveData<Resource<User>>()
    val buySneakers: LiveData<Resource<User>> = _buySneakers

    fun getSelectedSneaker(tokenId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _selectedSneaker.postValue(
                    collectionRepository.getSneakersById(tokenId)
                )
            }
        }
    }

    val countedOnce: LiveData<Boolean> = dataStoreRepository.checkIfAlreadyCounted().asLiveData()

    fun buySneakers(tokenId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val request = collectionRepository.buySneakers(
                    BuySneakers(
                        userId = firebaseRepository.getUser()!!.uid,
                        tokenId = tokenId
                    )
                )
                _buySneakers.postValue(request)
            }
        }
    }

    fun createUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                collectionRepository.createUser(
                    CreateUser(
                        userId = firebaseRepository.getUser()!!.uid
                    )
                )
            }
        }
    }

}