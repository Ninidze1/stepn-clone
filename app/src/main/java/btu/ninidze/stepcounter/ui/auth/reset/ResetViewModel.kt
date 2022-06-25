package btu.ninidze.stepcounter.ui.auth.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(private val auth: IFirebaseRepository) : ViewModel() {

    private var _navigateState = MutableLiveData<Boolean>()
    val navigateState: LiveData<Boolean> = _navigateState

    fun resetPassword(email: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                auth.resetUser(email).addOnCompleteListener { process ->
                    _navigateState.postValue(process.isSuccessful)
                }
            }
        }
    }
}