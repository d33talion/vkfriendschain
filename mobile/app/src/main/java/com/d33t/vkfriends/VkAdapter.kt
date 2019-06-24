package com.d33t.vkfriends

import com.beust.klaxon.Klaxon
import com.d33t.vkfriends.models.GetUserResponse
import com.d33t.vkfriends.models.User
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.*

const val VK_API_BASE_URL = "https://api.vk.com/method/"

class VkAdapter(private val accessToken: String ) {
    private val job = Job()
    private val mainScope = CoroutineScope(Dispatchers.Main + job)

    suspend fun getUserInfo(userId: String): User? {
        mainScope.launch {
            val response = withContext(Dispatchers.IO) {
                makeRequest("users.get?user_id=$userId&access_token=$accessToken&v=5.95")
            }

            val result = Klaxon().parse<GetUserResponse>(response)
            if (result?.error != null)
                null

            result!!.response?.get(0)
        }

        return null
    }

    private suspend fun makeRequest(url: String): String {
        val url = URL(VK_API_BASE_URL + url)
        with(url.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            var response = ""
            this.inputStream.bufferedReader().use { it.lines().forEach { line -> response += line } }

            return response
        }
    }
}
