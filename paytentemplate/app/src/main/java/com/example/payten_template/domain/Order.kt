package com.example.payten_template.domain

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@kotlinx.serialization.Serializable
class Order(
    val id: String,
    val description: String,
    val price: Double,
    val pickupTime: String,
    val creationTime: String,
    val state: OrderState,
    val viberID: String
){
    fun getPickupDate(): LocalDateTime? {
        return parseDatetime(pickupTime)
    }

    fun getCreationDate(): LocalDateTime? {
        return parseDatetime(creationTime)
    }

    private fun parseDatetime(data: String): LocalDateTime?{
        return try{
            return LocalDateTime.parse(data, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }catch (ex:Exception){
            return null
        }
    }
}
@kotlinx.serialization.Serializable
enum class OrderState(val description: String) {
    PENDING("Na cekanju"),
    IN_PROGRESS("U toku"),
    COMPLETED("Zavrsena"),
    DECLINED("Odbijena")
}