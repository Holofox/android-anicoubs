package ru.holofox.anicoubs.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.data.repository.VKWallRepository

class MainViewProvider(
    private val vkWallRepository: VKWallRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(vkWallRepository, savedStateHandle) as T
    }

}