package ru.holofox.anicoubs.features.data.providers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import ru.holofox.anicoubs.R
import ru.holofox.anicoubs.features.domain.providers.ConnectivityProvider

class ConnectivityProviderImpl(context: Context) : ConnectivityProvider {
    private val appContext = context.applicationContext

    override fun isOnline(): Boolean {
        val connectivityManager =
            appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

    override fun getNetworkErrorMessage(): String =
        appContext.getString(R.string.dialog_message_no_internet_connection)
}