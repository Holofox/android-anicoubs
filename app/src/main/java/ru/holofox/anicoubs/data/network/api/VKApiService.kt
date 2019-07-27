package ru.holofox.anicoubs.data.network.api

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import com.vk.api.sdk.internal.ApiCommand

import com.google.gson.GsonBuilder

import org.json.JSONException
import org.json.JSONObject

import ru.holofox.anicoubs.data.network.response.VKWallGetResponse
import ru.holofox.anicoubs.data.db.entity.vk.builder.VKParameters
import ru.holofox.anicoubs.data.network.response.VKVideoSaveResponse
import ru.holofox.anicoubs.data.network.response.VKWallGetByIdResponse

class VKApiService {

    class WallGet(private val parameters: VKParameters): ApiCommand<VKWallGetResponse>() {

        override fun onExecute(manager: VKApiManager): VKWallGetResponse {
            val call = VKMethodCall.Builder()
                .method("wall.get")
                .args(parameters.args)
                .version(API_VERSION) // manager.config.version
                .build()

            return manager.execute(call, ResponseApiParser())
        }

        private class ResponseApiParser : VKApiResponseParser<VKWallGetResponse> {
            override fun parse(response: String): VKWallGetResponse {
                try {
                    val joResponse = JSONObject(response).getJSONObject("response")
                    val gson = GsonBuilder().setPrettyPrinting().create()

                    return gson.fromJson(joResponse.toString(), VKWallGetResponse::class.java)
                } catch (ex: JSONException) {
                    throw VKApiIllegalResponseException(ex)
                }
            }
        }
    }

    class VideoGetById(private val parameters: VKParameters) : ApiCommand<VKWallGetByIdResponse>() {

        override fun onExecute(manager: VKApiManager): VKWallGetByIdResponse {
            val call = VKMethodCall.Builder()
                .method("wall.getById")
                .args(parameters.args)
                .version(API_VERSION)
                .build()

            return manager.execute(call, ResponseApiParser())
        }

        private class ResponseApiParser : VKApiResponseParser<VKWallGetByIdResponse> {
            override fun parse(response: String): VKWallGetByIdResponse {
                try {
                    val joResponse = JSONObject(response).getJSONObject("response")
                    val gson = GsonBuilder().setPrettyPrinting().create()

                    return gson.fromJson(joResponse.toString(), VKWallGetByIdResponse::class.java)

                } catch (ex: JSONException) {
                    throw VKApiIllegalResponseException(ex)
                }
            }
        }
    }

    class VideoSave(private val parameters: VKParameters) : ApiCommand<VKVideoSaveResponse>() {
        override fun onExecute(manager: VKApiManager): VKVideoSaveResponse {
            val call = VKMethodCall.Builder()
                .method("video.save")
                .args(parameters.args)
                .version(API_VERSION)
                .build()

            return manager.execute(call, ResponseApiParser())
        }

        private class ResponseApiParser : VKApiResponseParser<VKVideoSaveResponse> {
            override fun parse(response: String): VKVideoSaveResponse {
                try {
                    val joResponse = JSONObject(response).getJSONObject("response")
                    val gson = GsonBuilder().setPrettyPrinting().create()

                    return gson.fromJson(joResponse.toString(), VKVideoSaveResponse::class.java)

                } catch (ex: JSONException) {
                    throw VKApiIllegalResponseException(ex)
                }
            }
        }
    }

    companion object {
        private const val API_VERSION = "5.101"
    }

}