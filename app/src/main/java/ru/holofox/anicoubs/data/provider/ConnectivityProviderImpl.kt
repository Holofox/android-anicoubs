package ru.holofox.anicoubs.data.provider

import android.content.Context
import android.net.ConnectivityManager

class ConnectivityProviderImpl(context: Context) : ConnectivityProvider {

    private val appContext = context.applicationContext

    override fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}