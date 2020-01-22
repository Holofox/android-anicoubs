package ru.holofox.anicoubs.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.launch
import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.features.data.db.entities.coub.CoubEntry
import ru.holofox.anicoubs.features.data.network.api.vk.builders.VKParametersBuilder
import java.util.*

import ru.holofox.anicoubs.features.data.network.NetworkException
import ru.holofox.anicoubs.features.data.network.await
import ru.holofox.anicoubs.features.data.network.api.coub.models.responses.CoubChannelResponse
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.VKUsersGetResponse
import ru.holofox.anicoubs.features.data.network.api.vk.models.responses.video.VKVideoSaveResponse
import ru.holofox.anicoubs.features.domain.providers.UnitProvider
import ru.holofox.anicoubs.features.domain.repositories.CoubRepository
import ru.holofox.anicoubs.features.domain.repositories.HolofoxRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKUsersRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKVideoRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKWallRepository
import ru.holofox.anicoubs.internal.VKVideoRepositoryError
import ru.holofox.anicoubs.internal.observer.SingleEvent
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class MainViewModel(
    private val coubRepository: CoubRepository,
    private val holofoxRepository: HolofoxRepository,
    private val vkWallRepository: VKWallRepository,
    private val vkVideoRepository: VKVideoRepository,
    private val vkUsersRepository: VKUsersRepository,
    private val unitProvider: UnitProvider,
    private val savedStateHandle: SavedStateHandle
) : ScopedViewModel() {

    private val _isDialogShown = MutableLiveData(SingleEvent(false))
    val isDialogShown: LiveData<SingleEvent<Boolean>>
        get() = _isDialogShown

    private val _coubResponse = MutableLiveData<SingleEvent<CoubEntry>>()
    val coubResponse: LiveData<SingleEvent<CoubEntry>>
        get() = _coubResponse

    private val _channelResponse = MutableLiveData<SingleEvent<CoubChannelResponse>>()
    val channelResponse: LiveData<SingleEvent<CoubChannelResponse>>
        get() = _channelResponse

    private val _userResponse = MutableLiveData<SingleEvent<VKUsersGetResponse>>()
    val userResponse: LiveData<SingleEvent<VKUsersGetResponse>>
        get() = _userResponse

    private val _videoSaveResponse = MutableLiveData<SingleEvent<VKVideoSaveResponse>>()
    val videoSaveResponse: LiveData<SingleEvent<VKVideoSaveResponse>>
        get() = _videoSaveResponse

    private val _channelIsBanned = MutableLiveData<Boolean>()
    val channelIsBanned: LiveData<Boolean>
        get() = _channelIsBanned

    private val _coubLink = savedStateHandle.getLiveData(COUB_LINK, "")
    val coubLink: LiveData<String>
        get() = _coubLink

    private val _isPostponedPublish = savedStateHandle.getLiveData(IS_POSTPONED_PUBLISH, true)
    val isPostponedPublish: LiveData<Boolean>
        get() = _isPostponedPublish

    private val _categoryId = savedStateHandle.getLiveData(CATEGORY_ID, 0)
    val categoryId: LiveData<Int>
        get() = _categoryId

    private val _publishDate = savedStateHandle.getLiveData(PUBLISH_DATE, Calendar.getInstance())
    val publishDate: LiveData<Calendar>
        get() = _publishDate

    private val _snackBar = MutableLiveData<SingleEvent<String>>()
    val snackbar: LiveData<SingleEvent<String>>
        get() = _snackBar

    fun getCoub(permalink: String) = launch {
        try {
            val coub = coubRepository.getCoub(permalink).await()
            when {
                coub.fileVersions.html5.video?.high == null -> {
                    val message = unitProvider.getString(R.string.snackbar_channel_is_blocked)
                    onSnackBarShow(message)
                }
                coub.mediaBlocks.remixedFromCoubs.isNotEmpty() -> {
                    coub.mediaBlocks.remixedFromCoubs[0]?.let {
                        val views = unitProvider.quantityFromRes(
                            R.plurals.quantity_views, it.coubViewsCount, it.coubViewsCount)
                        val message = unitProvider.getString(R.string.snackbar_recoubed_from,
                            it.title, it.coubChannelTitle, views)
                        onSnackBarShow(message)
                    }
                }
                else -> _coubResponse.postValue(SingleEvent(coub))
            }
        } catch (error: NetworkException) {
            onSnackBarShow(error.message)
        }
    }

    fun checkInBlackList(channelId: Int) = launch {
        try {
            val isBanned = holofoxRepository.checkInBlackList(channelId).await()
            _channelIsBanned.value = isBanned
        } catch (error: NetworkException) {
            onSnackBarShow(error.message)
        }
    }

    fun getChannelInfo(channelId: Int) = launch {
        try {
            val channel = coubRepository.getChannel(channelId).await()
            _channelResponse.postValue(SingleEvent(channel))
        } catch (error: NetworkException) {
            onSnackBarShow(error.message)
        }
    }

    fun getUser(userId: String) = launch {
        try {
            val user = vkUsersRepository.usersGet(userId).await()
            _userResponse.postValue(SingleEvent(user))
        } catch (error: NetworkException) {
            onSnackBarShow(error.message)
        }
    }

    fun saveVideo(parameters: VKParametersBuilder) = launch {
        try {
            val result = vkVideoRepository.videoSave(parameters).await()
            _videoSaveResponse.postValue(SingleEvent(result))
        } catch (error: NetworkException) {
            onSnackBarShow(error.message)
        }
    }

    fun publishPost(parameters: VKParametersBuilder, video: VKVideoSaveResponse) = launch {
        fun videoDelete() = launch {
            try {
                val videoParameters = VKParametersBuilder.Builder()
                    .videoId(video.videoId)
                    .ownerId(video.ownerId)
                    .build()

                vkVideoRepository.videoDelete(videoParameters)
            } catch (error: VKVideoRepositoryError) {
                onSnackBarShow(error.message)
            }
        }

        try {
            vkWallRepository.wallPost(parameters)
            vkWallRepository.wallGet(filter = "postponed")
            onResetSavedState()
        } catch (error: NetworkException) {
            videoDelete()
            onSnackBarShow(error.message)
        }
    }

    private fun onSnackBarShow(message: String?) {
        message?.let {
            _snackBar.value = SingleEvent(it)
        }
    }

    fun onAddDialogShown() =
        _isDialogShown.postValue(SingleEvent(true))

    fun onCoubLinkEntered(link: CharSequence) =
        savedStateHandle.set(COUB_LINK, link)

    fun onPostponedPublish(isPostponed: Boolean) =
        savedStateHandle.set(IS_POSTPONED_PUBLISH, isPostponed)

    fun onSelectedCategory(categoryId: Int) =
        savedStateHandle.set(CATEGORY_ID, categoryId)

    fun onPublishDate(calendar: Calendar) =
        savedStateHandle.set(PUBLISH_DATE, calendar)

    private fun onResetSavedState() {
        savedStateHandle.set(COUB_LINK, "")
        savedStateHandle.set(CATEGORY_ID, 0)
    }

   companion object {
        const val COUB_LINK = "coub_link"
        const val IS_POSTPONED_PUBLISH = "is_postponed_publish"
        const val CATEGORY_ID = "category_id"
        const val PUBLISH_DATE = "publish_date"
    }

}