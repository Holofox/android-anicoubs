package ru.holofox.anicoubs.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ru.holofox.anicoubs.data.repository.VKWallRepository
import ru.holofox.anicoubs.internal.observer.SingleEvent
import ru.holofox.anicoubs.ui.base.ScopedViewModel
import java.util.*

class MainViewModel(
    private val vkWallRepository: VKWallRepository,
    private val savedStateHandle: SavedStateHandle
) : ScopedViewModel() {

    private val _isDialogShown = MutableLiveData(SingleEvent(false))
    val isDialogShown: LiveData<SingleEvent<Boolean>>
        get() = _isDialogShown

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

    companion object STATE {
        const val COUB_LINK = "coub_link"
        const val IS_POSTPONED_PUBLISH = "is_postponed_publish"
        const val CATEGORY_ID = "category_id"
        const val PUBLISH_DATE = "publish_date"
    }

}