package ru.holofox.anicoubs.data.network.api.vk

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters

abstract class VKBaseApiCommand<T: Any>(
    private val parameters: VKParameters = VKParameters.Builder().build(),
    private val method: String,
    private val parser: VKApiResponseParser<T>
) : ApiCommand<T>() {

    companion object {
        const val API_VERSION = "5.101"
        const val RETRY_COUNT = 3
    }

    override fun onExecute(manager: VKApiManager): T {
        val call = VKMethodCall.Builder()
            .method(method)
            .args(parameters.args)
            .version(API_VERSION) // or default - manager.config.version
            .retryCount(RETRY_COUNT)
            .build()

        return manager.execute(call, parser)
    }
}