package ru.holofox.anicoubs.ui.postponed.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

import kotlinx.coroutines.launch
import ru.holofox.anicoubs.data.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.data.repository.VKWallRefreshError

import ru.holofox.anicoubs.data.repository.VKWallRepository
import ru.holofox.anicoubs.internal.Constants.NETWORK_ERROR_SHOWN
import ru.holofox.anicoubs.internal.observer.SingleEvent
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class PostponedListViewModel(
    private val vkWallRepository: VKWallRepository,
    private val savedStateHandle: SavedStateHandle
) : ScopedViewModel() {

    val wall = vkWallRepository.wall

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _eventNetworkError = MutableLiveData(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private val _isNetworkErrorShown = savedStateHandle.getLiveData(NETWORK_ERROR_SHOWN, false)
    val isNetworkErrorShown : LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _snackBar =MutableLiveData<SingleEvent<String>>()
    val snackbar: LiveData<SingleEvent<String>>
        get() = _snackBar

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() = launch {
        try {
            vkWallRepository.wallGet(filter = "postponed")
            _isLoading.value = true
            _eventNetworkError.value = false
            savedStateHandle.set(NETWORK_ERROR_SHOWN, false)
        } catch (error: VKWallRefreshError) {
            _eventNetworkError.value = true
            onSnackBarShow(error.message)
        }
        finally {
            _isLoading.value = false
        }
    }

    fun onItemPublish(item: UnitSpecificVKWallMinimalEntry) = launch {
        try {
            vkWallRepository.wallPost(item)
        } catch (error: VKWallRefreshError) {
            onSnackBarShow(error.message)
        }
    }

    fun onItemDelete(item: UnitSpecificVKWallMinimalEntry) = launch {
        try {
            vkWallRepository.wallDelete(item)
        } catch (error: VKWallRefreshError) {
            onSnackBarShow(error.message)
        }
    }

    private fun onSnackBarShow(message: String?) {
        message?.let {
            _snackBar.value = SingleEvent(it)
        }
    }

    fun onNetworkErrorShown() =
        savedStateHandle.set(NETWORK_ERROR_SHOWN, true)
}