package btu.ninidze.stepcounter.ui.main.count

import androidx.lifecycle.*
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IStepRepository
import btu.ninidze.stepcounter.data.repository.impl.StepRepository
import btu.ninidze.stepcounter.ui.GetMoney
import btu.ninidze.stepcounter.ui.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StepViewModel @Inject constructor(
    private val collectionRepository: ICollectionRepository,
    private val firebaseRepository: IFirebaseRepository,
    private val stepRepository: IStepRepository

) : ViewModel() {

    private var _getUser = MutableLiveData<Resource<User>>()
    val getUser: LiveData<Resource<User>> = _getUser

    private var _getUsersSneakers = MutableLiveData<Resource<List<CollectionItem>>>()
    val getUsersSneakers: LiveData<Resource<List<CollectionItem>>> = _getUsersSneakers

    val totalSteps = stepRepository.getTotalSteps().asLiveData()

    private var _getMoney = MutableLiveData<Resource<User>>()
    val getMoney: LiveData<Resource<User>> = _getMoney

    fun getMoney(money: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _getMoney.postValue(
                    collectionRepository.getMoney(
                        GetMoney(
                            userId = firebaseRepository.getUser()?.uid ?: "null",
                            money = money
                        )
                    )
                )
            }
        }
    }

    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                println(firebaseRepository.getUser()?.uid ?: "null")
                _getUser.postValue(
                    collectionRepository.getUserById(
                        firebaseRepository.getUser()?.uid ?: "null"
                    )
                )
            }
        }
    }

    fun getUsersSneakers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _getUsersSneakers.postValue(
                    collectionRepository.getUsersCollection(
                        firebaseRepository.getUser()!!.uid
                    )
                )
            }
        }
    }
}