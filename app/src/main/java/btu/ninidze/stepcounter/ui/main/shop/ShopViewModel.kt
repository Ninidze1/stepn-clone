package btu.ninidze.stepcounter.ui.main.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import btu.ninidze.stepcounter.data.network.helper.Resource
import btu.ninidze.stepcounter.data.network.models.collection.CollectionItem
import btu.ninidze.stepcounter.data.repository.abstraction.ICollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(private val collectionRepository: ICollectionRepository) : ViewModel() {

    private var _collection = MutableLiveData<Resource<List<CollectionItem>>>()
    val collection: LiveData<Resource<List<CollectionItem>>> = _collection

    fun getCollection() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _collection.postValue(
                    collectionRepository.getSneakerCollection()
                )
            }
        }
    }


}