package ru.holofox.anicoubs.ui.timeline.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.features.domain.providers.UnitProvider
import ru.holofox.anicoubs.features.domain.repositories.CoubRepository

class TimeLineListViewModelProvider(
    private val acRepository: CoubRepository,
    private val unitProvider: UnitProvider,
    private val savedStateHandle: SavedStateHandle
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TimeLineListViewModel(acRepository, unitProvider, savedStateHandle) as T
    }
}