package com.example.app.data.remote

import com.example.neshanfinalproject.domain.model.Location
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class NeshanApiService {

    private val apiKey = "e27867bc5e004c7083a11699a2cde14d"
    private val client = OkHttpClient()

    suspend fun searchPlaces(query: String, latitude: Double, longitude: Double): List<Location> {
        val url = "https://api.neshan.org/v4/search?term=$query&lat=$latitude&lng=$longitude"
        val request = Request.Builder()
            .url(url)
            .addHeader("Api-Key", apiKey)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw Exception("Failed to search places")
            val body = response.body()?.string() ?: throw Exception("Empty response")
            val json = JSONObject(body)
            val items = json.getJSONArray("items")

            val locations = mutableListOf<Location>()
            for (i in 0 until items.length()) {
                val item = items.getJSONObject(i)
                val name = item.getString("title")
                val location = item.getJSONObject("location")
                val lat = location.getDouble("lat")
                val lng = location.getDouble("lng")
                locations.add(Location(lat, lng, name))
            }
            return locations
        }
    }
}
