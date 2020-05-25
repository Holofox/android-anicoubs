package ru.holofox.anicoubs.features.domain.providers

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import ru.holofox.anicoubs.internal.enums.UnitSystem

interface UnitProvider {
    fun getUnitProvider(): UnitSystem

    fun getString(@StringRes resId: Int, vararg format: Any): String
    fun quantityFromRes(@PluralsRes id: Int, quantity: Int, vararg format: Any): String
}