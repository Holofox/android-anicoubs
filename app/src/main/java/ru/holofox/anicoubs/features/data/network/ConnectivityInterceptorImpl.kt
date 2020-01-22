package ru.holofox.anicoubs.features.data.network

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response
import ru.holofox.anicoubs.features.domain.network.ConnectivityInterceptor
import ru.holofox.anicoubs.internal.NoConnectivityException
import java.util.concurrent.TimeUnit

class ConnectivityInterceptorImpl(
    context: Context
) : ConnectivityInterceptor {

    private val appContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isOnline())
            throw NoConnectivityException()
        return chain
            .withConnectTimeout(15, TimeUnit.SECONDS)
            .withReadTimeout(15, TimeUnit.SECONDS)
            .withWriteTimeout(15, TimeUnit.SECONDS)
            .proceed(chain.request())
    }

    private fun isOnline() : Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}