package ru.holofox.anicoubs.ui.postponed.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.launch
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.repository.VKWallRepository
import ru.holofox.anicoubs.internal.Constants.NETWORK_ERROR_SHOWN
import ru.holofox.anicoubs.internal.Constants.TARGET_GROUP_ID
import ru.holofox.anicoubs.internal.NoConnectivityException
import ru.holofox.anicoubs.internal.observer.SingleEvent
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class PostponedListViewModel(
    vkWallRepository: VKWallRepository,
    savedStateHandle: SavedStateHandle
) : ScopedViewModel() {

    private val repository = vkWallRepository
    private val savedState = savedStateHandle

    val wall = repository.wall

    private val _isLoading = MutableLiveData(false)
    private val _eventNetworkError = MutableLiveData(false)
    private val _isNetworkErrorShown : MutableLiveData<Boolean>
            = savedStateHandle.getLiveData(NETWORK_ERROR_SHOWN, false)

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    val isNetworkErrorShown : LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() = launch {

        val parameters = VKParameters.Builder()
            .ownerId(TARGET_GROUP_ID)
            .count(25)
            .filter("postponed")
            .extended(true)
            .build()

        try {
            repository.wallGet(parameters)
            _isLoading.value = true
            _eventNetworkError.value = false
            savedState.set(NETWORK_ERROR_SHOWN, false)
        }
        catch (e: NoConnectivityException) {
            _eventNetworkError.value = true
            Log.e("Connectivity","No internet connection!", e)
        }
        finally {
            _isLoading.value = false
        }
    }

    fun onNetworkErrorShown() {
        savedState.set(NETWORK_ERROR_SHOWN, true)
    }

}
