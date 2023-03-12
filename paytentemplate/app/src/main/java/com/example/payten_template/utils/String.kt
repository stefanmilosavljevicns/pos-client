package com.example.payten_template.utils

import com.example.payten_template.domain.Reservation
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.rezervationDate(): LocalDateTime? {
    return try {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern(
            Reservation.reservationDateFormat.toPattern()))
    }catch (ex: Exception){
        null
    }
}