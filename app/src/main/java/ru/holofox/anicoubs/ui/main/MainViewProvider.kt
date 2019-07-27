package ru.holofox.anicoubs.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.data.repository.VKWallRepository

class MainViewProvider(
    private val vkWallRepository: VKWallRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(vkWallRepository) as T
    }

}