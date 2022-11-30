package com.example.payten_template.Data.repositories

import com.example.payten_template.Data.Rezervacija
import com.example.payten_template.Data.repositories.source.RemoteRezervacijeDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class RezervacijeRepository {

    companion object {
        val Instance = RezervacijeRepository()
    }

    init {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                while (true) {
                    try {
                        getAllReservations()
                    } catch (ex: Exception) {
                    }
                    delay(10 * 1000)
                }
            }
        }
    }

    private val remoteDataSource = RemoteRezervacijeDataSource()

    val rezervacije = MutableStateFlow<List<Rezervacija>>(emptyList())

    suspend fun getAllReservations(): List<Rezervacija> {
        val rezs = remoteDataSource.getAllReservations().toList()
        rezervacije.emit(rezs)
        return rezs
    }

    suspend fun addReservation(rezervacija: Rezervacija): Rezervacija {
        rezervacija.id = null
        return remoteDataSource.addReservation(rezervacija)
    }

    suspend fun updateReservation(rezervacija: Rezervacija): Rezervacija {
        return remoteDataSource.addReservation(rezervacija)
    }

}