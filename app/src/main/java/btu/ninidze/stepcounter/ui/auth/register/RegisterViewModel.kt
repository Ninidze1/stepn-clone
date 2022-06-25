package btu.ninidze.stepcounter.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.ui.auth.CreateUser
import btu.ninidze.stepcounter.util.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepository: IFirebaseRepository,
    private val collectionRepository: ICollectionRepository
    ) : ViewModel() {

    private var _navigateState = MutableLiveData<Boolean>()
    val navigateState: LiveData<Boolean> = _navigateState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (email.isEmpty() || password.isEmpty()) return@withContext
                firebaseRepository.signUpUser(email, password).addOnCompleteListener { process ->
                    if (process.isSuccessful) {
                        _navigateState.postValue(true)
                    }
                }
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

    fun validateFieldsAndRegister(
        name: String,
        onNameError: (() -> Unit)? = null,
        email: String,
        onEmailError: (() -> Unit)? = null,
        password: String,
        onPasswordError: (() -> Unit)? = null,
        repPassword: String,
        onRepPasswordError: (() -> Unit)? = null,
        ) {

        if (email.isEmpty()) {
            onEmailError?.invoke()
        }
        if (password.isEmpty()) {
            onPasswordError?.invoke()
        }
        if (!ValidationUtil.isPasswordValid(password)) {
            onPasswordError?.invoke()
        }
        if (!ValidationUtil.isEmailValid(email)) {
            onEmailError?.invoke()
        }
        if (!ValidationUtil.isNameValid(name)) {
            onNameError?.invoke()
        }
        if (!ValidationUtil.doesPasswordMatch(password, repPassword)) {
            onRepPasswordError?.invoke()
        }

        if (email.isNotEmpty() || password.isNotEmpty()) {
            register(email, password)
        }

    }
}