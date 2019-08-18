package ru.holofox.anicoubs.ui.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

import ru.holofox.anicoubs.data.provider.LocaleManagerProviderImpl

@SuppressLint("Registered")
open class LocaleAppCombatActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleManagerProviderImpl.setLocale(newBase))
    }
}