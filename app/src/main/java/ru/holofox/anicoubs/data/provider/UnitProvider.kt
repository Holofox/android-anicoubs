package ru.holofox.anicoubs.data.provider

import androidx.annotation.StringRes
import androidx.annotation.PluralsRes

import ru.holofox.anicoubs.internal.enums.UnitSystem

interface UnitProvider {
    fun getUnitProvider(): UnitSystem

    fun getString(@StringRes resId: Int, vararg format: Any) : String
    fun quantityFromRes(@PluralsRes id: Int, quantity: Int, vararg format: Any): String
}