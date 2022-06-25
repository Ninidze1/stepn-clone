package btu.ninidze.stepcounter.ui.main.shelf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import btu.ninidze.stepcounter.data.repository.abstraction.IFirebaseRepository
import btu.ninidze.stepcounter.data.repository.impl.CollectionRepository
import btu.ninidze.stepcounter.data.repository.impl.FirebaseRepository
import btu.ninidze.stepcounter.ui.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShelfViewModel @Inject constructor(
    private var collectionRepository: ICollectionRepository,
    private var firebaseRepository: IFirebaseRepository
    ) : ViewModel() {

    private var _getUsersSneakers = MutableLiveData<Resource<List<CollectionItem>>>()
    val getUsersSneakers: LiveData<Resource<List<CollectionItem>>> = _getUsersSneakers

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