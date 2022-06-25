package btu.ninidze.stepcounter.ui.gift

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
class GiftViewModel @Inject constructor(private val collectionRepository: ICollectionRepository) : ViewModel() {

    private var _giftedSneakers = MutableLiveData<Resource<List<CollectionItem>>>()
    val giftedSneakers: LiveData<Resource<List<CollectionItem>>> = _giftedSneakers

    fun getGiftedSneakers() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _giftedSneakers.postValue(
                    collectionRepository.getGiftedSneakers()
                )
            }
        }
    }
}