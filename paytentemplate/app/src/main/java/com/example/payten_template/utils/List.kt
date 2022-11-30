package com.example.payten_template.utils

import com.example.payten_template.Data.Rezervacija
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.chrono.ChronoLocalDate
import java.time.format.DateTimeFormatter

fun List<Rezervacija>.filterByDate(date: LocalDate): List<Rezervacija> {
    return filter {
        try {
            val rezDate = LocalDateTime.parse(it.reservation, DateTimeFormatter.ofPattern(
                Rezervacija.reservationDateFormat.toPattern()))
            rezDate.toLocalDate().isEqual(date)
        }catch (ex: Exception){
            false
        }
    }
}