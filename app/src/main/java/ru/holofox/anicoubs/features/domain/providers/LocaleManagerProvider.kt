package ru.holofox.anicoubs.features.domain.providers

import android.content.Context
import ru.holofox.anicoubs.internal.enums.UnitSystem

interface LocaleManagerProvider {
    fun setLocale(context: Context): Context
    fun setNewLocale(context: Context, language: UnitSystem): Context
    fun getLanguage(context: Context): String
}