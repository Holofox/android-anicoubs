package ru.holofox.anicoubs.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import java.util.*
import android.preference.PreferenceManager
import android.util.Log

import ru.holofox.anicoubs.internal.enums.UnitSystem

object LocaleManagerProviderImpl : LocaleManagerProvider {

    private const val KEY_NAME = "key_language"

    private fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun setLocale(context: Context): Context {
        return updateResources(context, UnitSystem.valueOf(getLanguage(context)))
    }

    override fun setNewLocale(context: Context, language: UnitSystem): Context {
        persistLanguage(context, language)
        return updateResources(context, language)
    }

    @SuppressLint("DefaultLocale")
    override fun getLanguage(context: Context): String {
        val preferences = getPreferences(context)
        val currentLocale = getLocale(context.resources)

        return when (currentLocale.language.toUpperCase()) {
            UnitSystem.RU.name ->
                preferences.getString(KEY_NAME, UnitSystem.RU.name)!!
            else ->
                preferences.getString(KEY_NAME, UnitSystem.EN.name)!!
        }
    }

    private fun persistLanguage(context: Context, language: UnitSystem) {
        val preferences = getPreferences(context)
            preferences.edit().putString(KEY_NAME, language.name).apply()
    }

    private fun updateResources(context: Context, language: UnitSystem): Context {
        val locale = Locale(language.name)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

    private fun getLocale(resources: Resources): Locale {
        val config = resources.configuration
        return if (Build.VERSION.SDK_INT >= 24) config.locales.get(0) else config.locale
    }

}