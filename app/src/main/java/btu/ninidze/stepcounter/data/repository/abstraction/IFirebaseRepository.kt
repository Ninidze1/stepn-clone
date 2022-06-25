package btu.ninidze.stepcounter.data.repository.abstraction

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface IFirebaseRepository {

    suspend fun getUser(): FirebaseUser?
    suspend fun signOut()
    suspend fun signUpUser(email: String, password: String): Task<AuthResult>
    suspend fun signInUser(email: String, password: String): Task<AuthResult>
    suspend fun resetUser(mail: String): Task<Void>

}