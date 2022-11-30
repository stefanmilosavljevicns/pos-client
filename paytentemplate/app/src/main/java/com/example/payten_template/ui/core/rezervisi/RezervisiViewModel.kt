package com.example.payten_template.ui.core.rezervisi

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.payten_template.Data.Rezervacija
import com.example.payten_template.Data.Usluge
import com.example.payten_template.Data.repositories.RezervacijeRepository
import com.example.payten_template.utils.filterByDate
import com.example.payten_template.utils.rezervationDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class RezervisiViewModel: ViewModel() {

    private val rezervacijeRepository = RezervacijeRepository.Instance
    var selectedDate = LocalDateTime.now()

    companion object{
        const val startWorkHour = 15
        const val endWorkHour = 21
    }

    val usluge = Usluge.keys.toList()

    val termini = mutableStateListOf<LocalDateTime>()

    private val networkScope = CoroutineScope(Dispatchers.IO)

    init {
        generisiTermine()
    }

    fun generisiTermine(){
        val today = selectedDate
        val now = LocalDateTime.now()
        if(selectedDate.toLocalDate().isBefore(now.toLocalDate())){
            termini.clear()
            return
        }
        val startHour = if(selectedDate.toLocalDate().isEqual(now.toLocalDate())){
            if(selectedDate.hour > startWorkHour) selectedDate.hour else startWorkHour
        } else {
            startWorkHour
        }
        val endHours = endWorkHour
        val fakeTermini = mutableListOf<LocalDateTime>()
        val rezervacijeNaDan = rezervacijeRepository.rezervacije.value.filterByDate(selectedDate.toLocalDate())
        val zauzetiTermini = rezervacijeNaDan.mapNotNull { it.reservation.rezervationDate() }.toMutableList()
        zauzetiTermini.addAll(
            rezervacijeNaDan.filter {
                it.services == usluge[1] ||  it.services == usluge[2]
            }.map {
                it.reservation.rezervationDate()!!.plusMinutes(30)
            }
        )
        for(i in startHour until endHours){
            val firstDate = selectedDate.toLocalDate().atStartOfDay().withHour(i).withMinute(0).withSecond(0)
            val secondDate = selectedDate.toLocalDate().atStartOfDay().withHour(i).withMinute(30).withSecond(0)
            if(firstDate.isAfter(now.plusMinutes(5))){
                if(zauzetiTermini.find { it.isEqual(firstDate) } == null)
                    fakeTermini.add(firstDate)
            }
            if(secondDate.isAfter(now.plusMinutes(5))){
                if(zauzetiTermini.find { it.isEqual(secondDate) } == null)
                    fakeTermini.add(secondDate)
            }
        }
        termini.clear()
        termini.addAll(fakeTermini)
    }

    fun checkTermin(termin: String, service: String): Boolean{
        return try{
            termin.rezervationDate()?.let { date ->
                val index = termini.indexOfFirst { it.isEqual(date) }
                termini.getOrNull(index + 1)?.let { nextDate ->
                    if(date.plusMinutes(30).isEqual(nextDate)){
                        return true
                    }
                }
            }
            false
        }catch (ex: Exception){
            false
        }
    }

    fun rezervisi(rezervacija: Rezervacija){
        networkScope.launch {
            rezervacijeRepository.addReservation(rezervacija)
            rezervacijeRepository.getAllReservations()
        }
    }
}