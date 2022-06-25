package btu.ninidze.stepcounter.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.util.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseRepository: IFirebaseRepository) : ViewModel() {

    private var _navigateState = MutableLiveData<Boolean>()
    val navigateState: LiveData<Boolean> = _navigateState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (email.isEmpty() || password.isEmpty()) {
                    return@withContext
                }
                firebaseRepository.signInUser(email, password).addOnCompleteListener { process ->
                    _navigateState.postValue(process.isSuccessful)
                }
            }
        }
    }

    suspend fun getUID(): String? {
        return withContext(viewModelScope.coroutineContext) {
            firebaseRepository.getUser()?.uid
        }
    }

    fun validateFieldsAndLogin(
        email: String,
        onEmailError: (() -> Unit)? = null,
        password: String,
        onPasswordError: (() -> Unit)? = null,
    ) {

        if (email.isEmpty()) {
            onEmailError?.invoke()
        }

        if (password.isEmpty()) {
            onPasswordError?.invoke()
            return
        }

        if (!ValidationUtil.isEmailValid(email)) {
            onEmailError?.invoke()
        }

        if (!ValidationUtil.isPasswordValid(password)) {
            onPasswordError?.invoke()
        }

        if (email.isNotEmpty() || password.isNotEmpty()) {
            login(email, password)
        }
    }
}