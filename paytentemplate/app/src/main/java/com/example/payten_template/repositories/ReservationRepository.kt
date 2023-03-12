package com.example.payten_template.repositories

import com.example.payten_template.domain.Reservation
import com.example.payten_template.repositories.source.RemoteReservationDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow

class ReservationRepository(
    private val remoteDataSource: RemoteReservationDataSource = RemoteReservationDataSource()
) {

    companion object {
        val Instance = ReservationRepository()
    }

    init {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                while (true) {
                    try {
                        getAllReservations()
                    } catch (ex: Exception) {
                    }
                    delay(5 * 1000)
                }
            }
        }
    }

    val reservations = MutableStateFlow<List<Reservation>>(emptyList())

    suspend fun getAllReservations(): List<Reservation> {
        val data = remoteDataSource.getAllReservations().toList()
        reservations.emit(data)
        return data
    }

    suspend fun addReservation(reservation: Reservation): Reservation {
        reservation.id = null
        return remoteDataSource.addReservation(reservation)
    }

    suspend fun updateReservation(reservation: Reservation): Reservation {
        return remoteDataSource.addReservation(reservation)
    }

}