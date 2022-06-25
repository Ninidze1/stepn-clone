package btu.ninidze.stepcounter.ui.total

import androidx.lifecycle.*
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IDataStoreRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IStepRepository
import btu.ninidze.stepcounter.data.sensor.MeasurableSensor
import btu.ninidze.stepcounter.ui.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WearViewModel @Inject constructor(
    activitySensor: MeasurableSensor,
    private val stepRepository: IStepRepository,
    private val collectionRepository: ICollectionRepository,
    private val dataStoreRepository: IDataStoreRepository,
    private val firebaseRepository: IFirebaseRepository
): ViewModel() {

    private var _getUser = MutableLiveData<Resource<User>>()
    val getUser: LiveData<Resource<User>> = _getUser

    private var _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    var previousTotalStep = 0
        private set

    private var totalSteps = 0f

    private val countedOnce: LiveData<Boolean> = dataStoreRepository.checkIfAlreadyCounted().asLiveData()

    fun initPreviousStep(value: Int) {
        if (countedOnce.value == false) {
            previousTotalStep = value
            storePreviousTotalSteps(value)
            return
        }
    }

    fun getUser() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _getUser.postValue(
                    collectionRepository.getUserById(
                        firebaseRepository.getUser()!!.uid
                    )
                )
            }
        }
    }

    private fun storePreviousTotalSteps(previousTotalStep: Int) {
        viewModelScope.launch {
            println("asdf")
            stepRepository.insert(previousTotalStep)
            dataStoreRepository.saveCountedOnce()
        }
    }

    fun delayAndRun(time: Long = 1500, block: () -> Unit) {
        viewModelScope.launch {
            delay(time)
            withContext(Dispatchers.Main) {
                block.invoke()
            }
        }
    }

    init {
        activitySensor.startListening()
        activitySensor.setOnSensorValuesChangedListener { values ->
            totalSteps = values[0]
            val currentSteps = totalSteps.toInt() - previousTotalStep
            _count.postValue(currentSteps)
        }
    }
}