package com.example.payten_template.utils

import com.example.payten_template.domain.Reservation
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun List<Reservation>.filterByDate(date: LocalDate): List<Reservation> {
    return filter {
        try {
            val rezDate = LocalDateTime.parse(it.reservation, DateTimeFormatter.ofPattern(
                Reservation.reservationDateFormat.toPattern()))
            rezDate.toLocalDate().isEqual(date)
        }catch (ex: Exception){
            false
        }
    }
}