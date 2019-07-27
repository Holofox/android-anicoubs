package ru.holofox.anicoubs.data.provider

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import ru.holofox.anicoubs.internal.enums.UnitSystem

class UnitProviderImpl(context: Context) : UnitProvider {

    private val appContext = context.applicationContext

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    override fun getUnitProvider(): UnitSystem {
        return UnitSystem.valueOf(preferences.getString(KEY_NAME, UnitSystem.EN.name)!!)
    }

    companion object {
        const val KEY_NAME = "key_language"
    }
}