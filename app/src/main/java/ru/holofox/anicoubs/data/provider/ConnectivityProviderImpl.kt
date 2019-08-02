package ru.holofox.anicoubs.data.provider

import android.content.Context
import android.net.ConnectivityManager
import ru.holofox.anicoubs.R

class ConnectivityProviderImpl(context: Context) : ConnectivityProvider {

    private val appContext = context.applicationContext

    override fun isOnline(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun getNetworkErrorMessage() : String
            = appContext.getString(R.string.dialog_message_no_internet_connection)
}