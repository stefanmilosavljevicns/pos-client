package com.example.payten_template.ui.core.orders

import android.util.Log
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
        ords.filter { it.getEndTime() != null }.filter {
            it.getStartTime()!!.isAfter(LocalDateTime.now())
        }.sortedBy {
            it.getStartTime()
        }
    }

    val archivedOrders = ordersRepository.orders.map { ords ->
        ords.filter { it.getEndTime() != null }.filter {
            it.getStartTime()!!.isBefore(LocalDateTime.now())
        }.sortedBy {
            it.getStartTime()
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