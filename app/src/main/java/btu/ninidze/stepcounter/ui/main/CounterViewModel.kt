package btu.ninidze.stepcounter.ui.main

import androidx.lifecycle.ViewModel
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CounterViewModel @Inject constructor(
    private val collectionRepository: ICollectionRepository,
    private val firebaseRepository: IFirebaseRepository
): ViewModel() {

}