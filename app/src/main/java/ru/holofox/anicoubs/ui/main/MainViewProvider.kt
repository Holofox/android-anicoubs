package ru.holofox.anicoubs.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.features.domain.providers.UnitProvider

import ru.holofox.anicoubs.features.domain.repositories.CoubRepository
import ru.holofox.anicoubs.features.domain.repositories.HolofoxRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKUsersRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKWallRepository
import ru.holofox.anicoubs.features.domain.repositories.vk.VKVideoRepository

class MainViewProvider(
    private val coubRepository: CoubRepository,
    private val holofoxRepository: HolofoxRepository,
    private val vkWallRepository: VKWallRepository,
    private val vkVideoRepository: VKVideoRepository,
    private val vkUsersRepository: VKUsersRepository,
    private val unitProvider: UnitProvider,
    private val savedStateHandle : SavedStateHandle
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(coubRepository, holofoxRepository, vkWallRepository,
            vkVideoRepository, vkUsersRepository, unitProvider, savedStateHandle) as T
    }

}