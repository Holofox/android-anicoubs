package ru.holofox.anicoubs.data.network.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.api.VKApiService
import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.provider.ConnectivityProvider
import ru.holofox.anicoubs.internal.NoConnectivityException

class VKNetworkDataSourceImpl(
    private val connectivityProvider: ConnectivityProvider
): VKWallDataSource {

    private val _vkWallGetResponse = MutableLiveData<VKWallGetResponse>()

    override val vkWallGetResponse: LiveData<VKWallGetResponse>
        get() = _vkWallGetResponse

    override suspend fun wallGet(parameters: VKParameters) {
        if (connectivityProvider.isOnline()) {
            VK.execute(VKApiService.WallGet(parameters), object : VKApiCallback<VKWallGetResponse> {
                override fun success(result: VKWallGetResponse) {
                    _vkWallGetResponse.postValue(result)
                }

                override fun fail(error: VKApiExecutionException) {
                    Log.e("VKApiExecutionException", error.detailMessage)
                }
            })
        } else throw NoConnectivityException()
    }

    override suspend fun wallPost(parameters: VKParameters) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}