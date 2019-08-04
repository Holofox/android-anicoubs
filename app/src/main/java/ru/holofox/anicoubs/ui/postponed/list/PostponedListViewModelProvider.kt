package ru.holofox.anicoubs.ui.postponed.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.holofox.anicoubs.data.repository.VKVideoRepository
import ru.holofox.anicoubs.data.repository.VKWallRepository

class PostponedListViewModelProvider(
    private val vkWallRepository: VKWallRepository,
    private val vkVideoRepository: VKVideoRepository,
    private val savedStateHandle : SavedStateHandle
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostponedListViewModel(vkWallRepository, vkVideoRepository, savedStateHandle) as T
    }

}