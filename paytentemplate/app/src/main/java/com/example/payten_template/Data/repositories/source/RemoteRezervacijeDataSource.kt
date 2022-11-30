package com.example.payten_template.Data.repositories.source

import com.example.payten_template.Data.Rezervacija
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.DataConversion.install
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

class RemoteRezervacijeDataSource {

    companion object{
        private const val baseUrl = "http://161.97.170.99:8081/api/v1"
        private const val getAllReservationsUrl = "${baseUrl}/getAllReservations"
        private const val addReservationUrl = "${baseUrl}/addReservation"
    }

    init {
    }

    private val client = HttpClient(){
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAllReservations(): Array<Rezervacija> {
        return client.get(getAllReservationsUrl).body()
    }

    suspend fun addReservation(rezervacija: Rezervacija): Rezervacija {
        return client.post(addReservationUrl){
            contentType(ContentType.Application.Json)
            setBody(rezervacija)
        }.body()
    }

}