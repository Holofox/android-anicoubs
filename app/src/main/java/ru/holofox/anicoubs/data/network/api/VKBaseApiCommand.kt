package ru.holofox.anicoubs.data.network.api

import com.google.gson.GsonBuilder
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand

import org.json.JSONException
import org.json.JSONObject

import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters

abstract class VKBaseApiCommand<T: Any>(
    private val parameters: VKParameters,
    private val method: String,
    private val classToken: Class<T>
) : ApiCommand<T>() {

    companion object {
        private const val API_VERSION = "5.101"
    }

    override fun onExecute(manager: VKApiManager): T {
        val call = VKMethodCall.Builder()
            .method(method)
            .args(parameters.args)
            .version(API_VERSION) // or default - manager.config.version
            .retryCount(3)
            .build()

        return manager.execute(call, ResponseApiParser(classToken))
    }

    protected class ResponseApiParser<T: Any>(val type: Class<T>): VKApiResponseParser<T> {
        override fun parse(response: String): T {
            try {
                val joResponse = JSONObject(response).getJSONObject("response").toString()
                val gson = GsonBuilder().setPrettyPrinting().create()

                return gson.fromJson(joResponse, type)

            } catch (ex: JSONException) {
                throw VKApiIllegalResponseException(ex)
            }
        }
    }
}