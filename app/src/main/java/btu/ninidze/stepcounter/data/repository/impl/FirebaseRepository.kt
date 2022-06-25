package btu.ninidze.stepcounter.data.repository.impl

import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    IFirebaseRepository {

    override suspend fun getUser() = firebaseAuth.currentUser
    override suspend fun signOut() = firebaseAuth.signOut()

    override suspend fun signUpUser(email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    override suspend fun signInUser(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    override suspend fun resetUser(mail: String) = firebaseAuth.sendPasswordResetEmail(mail)

}