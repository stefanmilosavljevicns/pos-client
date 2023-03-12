package com.example.payten_template.repositories

import android.util.Log
import com.example.payten_template.domain.Order
import com.example.payten_template.domain.Reservation
import com.example.payten_template.repositories.source.RemoteOrderDataSource
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class OrderRepository(
    private val remoteOrderDataSource: RemoteOrderDataSource = RemoteOrderDataSource()
) {

    companion object{
        const val REFRESH_DELAY = 5000L
        const val TAG = "OrderRepository"
    }

    private val networkScope = CoroutineScope(Dispatchers.IO)

    init {
        GlobalScope.launch {
            withContext(Dispatchers.IO){
                while (true) {
                    try {
                        refresh()
                    } catch (ex: Exception) {
                    }
                    delay(5 * 1000)
                }
            }
        }
    }

    val orders = MutableStateFlow<List<Order>>(emptyList())

    suspend fun refresh(){
        val orders = remoteOrderDataSource.getAllOrders().toList()
        this.orders.emit(orders)
    }

    suspend fun acceptOrder(order: Order){
        remoteOrderDataSource.acceptOrder(order)
    }

    suspend fun declineOrder(order: Order){
        remoteOrderDataSource.declineOrder(order)
    }

    suspend fun completeOrder(order: Order){
        remoteOrderDataSource.completeOrder(order)
    }

}