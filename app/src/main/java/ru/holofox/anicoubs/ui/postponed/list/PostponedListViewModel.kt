package ru.holofox.anicoubs.ui.postponed.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

import kotlinx.coroutines.launch
import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import ru.holofox.anicoubs.features.data.network.api.vk.models.wall.VKWallVideo
import ru.holofox.anicoubs.features.domain.db.unitlocalized.vk.UnitSpecificVKWallMinimalEntry
import ru.holofox.anicoubs.features.domain.repositories.vk.VKVideoRepository

import ru.holofox.anicoubs.features.domain.repositories.vk.VKWallRepository
import ru.holofox.anicoubs.internal.Constants.NETWORK_ERROR_SHOWN
import ru.holofox.anicoubs.internal.VKVideoRepositoryError
import ru.holofox.anicoubs.internal.VKWallRepositoryError
import ru.holofox.anicoubs.internal.observer.SingleEvent
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class PostponedListViewModel(
    private val vkWallRepository: VKWallRepository,
    private val vkVideoRepository: VKVideoRepository,
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
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    private val _snackBar = MutableLiveData<SingleEvent<String>>()
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
        } catch (error: VKWallRepositoryError) {
            _eventNetworkError.value = true
        } finally {
            _isLoading.value = false
        }
    }

    fun onItemPublish(item: UnitSpecificVKWallMinimalEntry) = launch {
        try {
            val parameters = VKParametersBuilder.Builder()
                .ownerId(item.ownerId)
                .postId(item.postId)
                .build()

            vkWallRepository.wallPost(parameters)
            vkWallRepository.wallDaoDelete(item.postId)
        } catch (error: VKWallRepositoryError) {
            onSnackBarShow(error.message)
        }
    }

    fun onItemDelete(item: UnitSpecificVKWallMinimalEntry) = launch {

        fun onVideoDelete(video: VKWallVideo) = launch {
            try {
                val parameters = VKParametersBuilder.Builder()
                    .videoId(video.id)
                    .ownerId(video.ownerId)
                    .build()

                vkVideoRepository.videoDelete(parameters)
            } catch (error: VKVideoRepositoryError) {
                onSnackBarShow(error.message)
            }
        }

        try {
            val parameters = VKParametersBuilder.Builder()
                .ownerId(item.ownerId)
                .postId(item.postId)
                .build()

            vkWallRepository.wallDelete(parameters)
            item.attachments?.get(0)?.video?.let { onVideoDelete(it) }
        } catch (error: VKWallRepositoryError) {
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