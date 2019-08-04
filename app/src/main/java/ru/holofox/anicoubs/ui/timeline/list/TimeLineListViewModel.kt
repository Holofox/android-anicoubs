package ru.holofox.anicoubs.ui.timeline.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.launch
import ru.holofox.anicoubs.data.provider.UnitProvider
import ru.holofox.anicoubs.data.repository.CoubRepository
import ru.holofox.anicoubs.internal.Constants.NETWORK_ERROR_SHOWN
import ru.holofox.anicoubs.internal.CoubRepositoryError
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class TimeLineListViewModel(
    private val coubRepository: CoubRepository,
    private val unitProvider: UnitProvider,
    private val savedStateHandle : SavedStateHandle
) : ScopedViewModel() {

    /* private val unitSystem = unitProvider.getUnitProvider()

    private val isEnglish: Boolean
        get() = unitSystem == UnitSystem.EN */

    val timeline = coubRepository.timeline

    private val _isLoading = MutableLiveData(false)
    private val _eventNetworkError = MutableLiveData(false)
    private val _isNetworkErrorShown = savedStateHandle.getLiveData(NETWORK_ERROR_SHOWN, false)

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
        try {
            coubRepository.getTimeLine(false)
            _isLoading.value = true
            _eventNetworkError.value = false
            savedStateHandle.set(NETWORK_ERROR_SHOWN, false)
        }
        catch (error: CoubRepositoryError) {
            _eventNetworkError.value = true
        }
        finally {
            _isLoading.value = false
        }
    }

    fun onNetworkErrorShown() =
        savedStateHandle.set(NETWORK_ERROR_SHOWN, true)

}