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
import ru.holofox.anicoubs.data.network.response.VKWallPostResponse
import ru.holofox.anicoubs.data.provider.ConnectivityProvider
import ru.holofox.anicoubs.internal.NoConnectivityException
import ru.holofox.anicoubs.internal.dto.NetworkResult

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

    private val _vkWallPostResponse = MutableLiveData<NetworkResult<VKWallPostResponse>>()
    override val vkWallPostResponse: LiveData<NetworkResult<VKWallPostResponse>>
        get() = _vkWallPostResponse

    override suspend fun wallPost(parameters: VKParameters)  {
        VK.execute(VKApiService.WallPost(parameters), object : VKApiCallback<VKWallPostResponse> {
            override fun success(result: VKWallPostResponse) {
                _vkWallPostResponse.postValue(NetworkResult.success(result))
            }

            override fun fail(error: VKApiExecutionException) {
                Log.e("VKApiExecutionException", error.detailMessage)
                _vkWallPostResponse.postValue(NetworkResult.error(error.detailMessage))
            }
        })
    }

}