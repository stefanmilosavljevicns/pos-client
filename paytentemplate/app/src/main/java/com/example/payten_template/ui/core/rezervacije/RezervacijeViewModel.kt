package com.example.payten_template.ui.core.rezervacije

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.payten_template.Data.Rezervacija
import com.example.payten_template.Data.Rezervacija.Companion.reservationDateFormat
import com.example.payten_template.Data.repositories.RezervacijeRepository
import com.example.payten_template.utils.filterByDate
import com.example.payten_template.utils.rezervationDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RezervacijeViewModel: ViewModel() {

    val rezervacije = mutableStateListOf<Rezervacija>()
    var isLoading by mutableStateOf(false)

    //private var _isLoading = false
    private val rezervacijeRepository = RezervacijeRepository.Instance
    private val networkScope = CoroutineScope(Dispatchers.IO)


    init {
        refresh()
        viewModelScope.launch {
            rezervacijeRepository.rezervacije.collect{
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
                val rezs = rezervacijeRepository.getAllReservations()
            }catch (ex: Exception){ }
            withContext(Dispatchers.Main){
                isLoading = false
            }
        }
    }


}