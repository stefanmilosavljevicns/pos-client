package com.example.payten_template.ui.core.orders

import android.util.Log
import androidx.compose.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.payten_template.domain.Order
import com.example.payten_template.repositories.OrderRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class OrdersViewModel: ViewModel() {
    private val ordersRepository = OrderRepository()
    val orders = ordersRepository.orders.map { ords ->
        ords.filter { it.getCreationDate() != null }.filter {
            it.getPickupDate()!!.isAfter(LocalDateTime.now())
        }.sortedBy {
            it.getPickupDate()
        }
    }

    val archivedOrders = ordersRepository.orders.map { ords ->
        ords.filter { it.getCreationDate() != null }.filter {
            it.getPickupDate()!!.isBefore(LocalDateTime.now())
        }.sortedBy {
            it.getPickupDate()
        }
    }


    var isLoading by mutableStateOf(false)

    init {
        refresh()
    }

    fun refresh(){
        viewModelScope.launch {
            isLoading = true
            try{
                ordersRepository.refresh()
            }catch (ex: Exception){
                Log.e("OrdersViewModel", "Unable to fetch orders", ex)
            }finally {
                isLoading = false
            }
        }
    }

    suspend fun declineOrder(order: Order){
        ordersRepository.declineOrder(order)
    }

    suspend fun acceptOrder(order: Order){
        ordersRepository.acceptOrder(order)
    }
}