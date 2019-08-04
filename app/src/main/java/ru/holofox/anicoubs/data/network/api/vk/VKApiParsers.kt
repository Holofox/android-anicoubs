package ru.holofox.anicoubs.data.network.api.vk

import com.google.gson.GsonBuilder
import com.vk.api.sdk.VKApiResponseParser
import com.vk.api.sdk.exceptions.VKApiIllegalResponseException
import org.json.JSONException
import org.json.JSONObject

class ResponseApiParser<T: Any>(val type: Class<T>?): VKApiResponseParser<T> {
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

class ResponseApiParserToBoolean: VKApiResponseParser<Boolean> {
    override fun parse(response: String): Boolean {
        try {
            return JSONObject(response).get("response") == 1
        } catch (ex: JSONException) {
            throw VKApiIllegalResponseException(ex)
        }
    }
}

class ResponseApiParserToLong: VKApiResponseParser<Long> {
    override fun parse(response: String): Long {
        try {
            return JSONObject(response).getLong("response")
        } catch (ex: JSONException) {
            throw VKApiIllegalResponseException(ex)
        }
    }
}