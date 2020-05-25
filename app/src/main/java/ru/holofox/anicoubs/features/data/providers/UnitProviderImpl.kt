package ru.holofox.anicoubs.features.data.providers

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.preference.PreferenceManager
import ru.holofox.anicoubs.features.domain.providers.UnitProvider
import ru.holofox.anicoubs.internal.enums.UnitSystem

class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitProvider(): UnitSystem {
        return UnitSystem.valueOf(preferences.getString(KEY_NAME, UnitSystem.EN.name)!!)
    }

    override fun getString(@StringRes resId: Int, vararg format: Any): String {
        return appContext.getString(resId, *format)
    }

    override fun quantityFromRes(@PluralsRes id: Int, quantity: Int, vararg format: Any): String {
        return appContext.resources.getQuantityString(id, quantity, *format)
    }

    companion object {
        const val KEY_NAME = "key_language"
    }
}