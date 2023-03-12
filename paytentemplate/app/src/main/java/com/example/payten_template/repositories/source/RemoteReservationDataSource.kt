package com.example.payten_template.repositories.source

import com.example.payten_template.domain.Reservation
import com.example.payten_template.utils.baseURL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class RemoteReservationDataSource {

    companion object{
        private const val getAllReservationsUrl = "$baseURL/getAllReservations"
        private const val addReservationUrl = "$baseURL/addReservation"
    }

    private val client = HttpClient(){
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAllReservations(): Array<Reservation> {
        return client.get(getAllReservationsUrl).body()
    }

    suspend fun addReservation(rezervacija: Reservation): Reservation {
        return client.post(addReservationUrl){
            contentType(ContentType.Application.Json)
            setBody(rezervacija)
        }.body()
    }

}