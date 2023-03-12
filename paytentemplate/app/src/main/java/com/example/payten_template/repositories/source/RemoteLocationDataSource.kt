package com.example.payten_template.repositories.source

import com.example.payten_template.domain.Location
import com.example.payten_template.utils.baseURL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

class RemoteLocationDataSource {

    companion object{
        private const val getAllLocationsUrl = "$baseURL/location"
    }

    private val client = HttpClient(){
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAllLocations(): List<Location>{
        return client.get(getAllLocationsUrl).body()
    }

}