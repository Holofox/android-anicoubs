package ru.holofox.anicoubs.ui.timeline.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.data.provider.UnitProvider
import ru.holofox.anicoubs.data.repository.AnicoubsRepository

class TimeLineListViewModelProvider(
    private val acRepository: AnicoubsRepository,
    private val unitProvider: UnitProvider,
    private val savedStateHandle : SavedStateHandle
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimeLineListViewModel(acRepository, unitProvider, savedStateHandle) as T
    }
}