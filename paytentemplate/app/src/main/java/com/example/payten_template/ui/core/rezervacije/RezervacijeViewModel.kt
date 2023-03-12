package com.example.payten_template.ui.core.rezervacije

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_template.domain.Reservation
import com.example.payten_template.repositories.ReservationRepository
import com.example.payten_template.utils.rezervationDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class RezervacijeViewModel: ViewModel() {

    val rezervacije = mutableStateListOf<Reservation>()
    var isLoading by mutableStateOf(false)

    //private var _isLoading = false
    private val reservationRepository = ReservationRepository.Instance
    private val networkScope = CoroutineScope(Dispatchers.IO)


    init {
        refresh()
        viewModelScope.launch {
            reservationRepository.reservations.collect{
                rezervacije.clear()
                rezervacije.addAll(it.filter { rez ->
                        rez.reservation.rezervationDate()?.let{ date ->
                            return@filter date.toLocalDate().isEqual(LocalDate.now()) || date.toLocalDate().isAfter(LocalDate.now())
                        }
                        false
                    }
                    .sortedBy { rez ->
                        rez.reservation.rezervationDate()!!
                    }
                )//.filterByDate(selectedDate))
            }
        }
    }

    fun refresh(){
        networkScope.launch {
            withContext(Dispatchers.Main){
                isLoading = true
            }
            try{
                val rezs = reservationRepository.getAllReservations()
            }catch (ex: Exception){ }
            withContext(Dispatchers.Main){
                isLoading = false
            }
        }
    }


}