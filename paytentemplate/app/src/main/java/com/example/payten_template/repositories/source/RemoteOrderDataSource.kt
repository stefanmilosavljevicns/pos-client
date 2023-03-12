package com.example.payten_template.repositories.source

import com.example.payten_template.domain.Order
import com.example.payten_template.utils.baseURL
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*

class RemoteOrderDataSource {
    companion object{
        private const val getAllOrdersUrl = "$baseURL/getOrders"
        private const val declineOrderUrl = "$baseURL/declineOrder"
        private const val acceptOrderUrl = "$baseURL/acceptOrder"
        private const val completeOrderUrl = "$baseURL/payForOrder"
    }

    private val client = HttpClient(){
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getAllOrders(): Array<Order>{
        return client.get(getAllOrdersUrl).body()
    }

    suspend fun declineOrder(order: Order){
        client.put("$declineOrderUrl/${order.id}")
    }

    suspend fun acceptOrder(order: Order){
        client.put("$acceptOrderUrl/${order.id}")
    }

    suspend fun completeOrder(order: Order){
        client.put("$completeOrderUrl/${order.id}")
    }


}