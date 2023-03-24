package com.example.payten_template.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@kotlinx.serialization.Serializable
class Order(
    val id: String,
    val description: Array<String>,
    val price: Double,
    val startTime: String,
    val endTime: String,
    val state: OrderState,
    val viberID: String
){
    fun getStartTime(): LocalDateTime? {
        return parseDatetime(startTime)
    }

    fun getEndTime(): LocalDateTime? {
        return parseDatetime(endTime)
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