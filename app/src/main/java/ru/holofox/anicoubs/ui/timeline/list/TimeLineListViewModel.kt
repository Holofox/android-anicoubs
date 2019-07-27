package ru.holofox.anicoubs.ui.timeline.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.launch
import ru.holofox.anicoubs.data.provider.UnitProvider
import ru.holofox.anicoubs.data.repository.AnicoubsRepository
import ru.holofox.anicoubs.internal.Constants.NETWORK_ERROR_SHOWN
import ru.holofox.anicoubs.internal.NoConnectivityException
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class TimeLineListViewModel(
    anicoubsRepository: AnicoubsRepository,
    unitProvider: UnitProvider,
    savedStateHandle : SavedStateHandle
) : ScopedViewModel() {

    /* private val unitSystem = unitProvider.getUnitProvider()

    private val isEnglish: Boolean
        get() = unitSystem == UnitSystem.EN */

    private val repository = anicoubsRepository
    private val savedState = savedStateHandle

    val timeline = repository.timeline

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
        try {
            repository.getTimeLine(false)
            _isLoading.value = true
            _eventNetworkError.value = false
            savedState.set(NETWORK_ERROR_SHOWN, false)
        }
        catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection!", e)
            _eventNetworkError.value = true
        }
        finally {
            _isLoading.value = false
        }
    }

    fun onNetworkErrorShown() {
        savedState.set(NETWORK_ERROR_SHOWN, true)
    }

}