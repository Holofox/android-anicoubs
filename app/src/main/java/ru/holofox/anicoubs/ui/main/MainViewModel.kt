package ru.holofox.anicoubs.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.holofox.anicoubs.data.repository.VKWallRepository
import ru.holofox.anicoubs.internal.enums.UnitDialog
import ru.holofox.anicoubs.internal.observer.SingleEvent
import ru.holofox.anicoubs.ui.base.ScopedViewModel

class MainViewModel(
    vkWallRepository: VKWallRepository
) : ScopedViewModel() {

    private val _dialog = MutableLiveData(SingleEvent(UnitDialog.NO_DIALOG))

    val dialog: LiveData<SingleEvent<UnitDialog>>
        get() = _dialog

    fun onDialogShown(dialog: UnitDialog) {
        _dialog.value = SingleEvent(dialog)
    }

    fun publishPost() {

    }

}