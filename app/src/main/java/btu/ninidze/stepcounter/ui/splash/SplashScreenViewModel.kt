package btu.ninidze.stepcounter.ui.splash

import androidx.lifecycle.*
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IDataStoreRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.ui.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val firebaseRepository: IFirebaseRepository,
    private val collectionRepository: ICollectionRepository,
    dataStoreRepository: IDataStoreRepository
    ): ViewModel() {

    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _getUser.postValue(
                    collectionRepository.getUserById(
                        firebaseRepository.getUser()?.uid ?: "null"
                    )
                )
            }
        }
    }

    private var _getUser = MutableLiveData<Resource<User>>()
    val getUser: LiveData<Resource<User>> = _getUser

    val countedOnce: LiveData<Boolean> = dataStoreRepository.checkIfAlreadyCounted().asLiveData()

    fun runOnMain(block: suspend () -> Unit) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) { block.invoke() }
        }
    }

    suspend fun getUID(): String? {
        return withContext(viewModelScope.coroutineContext) {
            firebaseRepository.getUser()?.uid
        }
    }
}