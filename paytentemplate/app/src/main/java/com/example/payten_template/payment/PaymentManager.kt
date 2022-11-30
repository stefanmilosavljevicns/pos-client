package com.example.payten_template.payment

import android.content.Context
import android.content.Intent
import com.example.payten_template.payment.domain.request.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PaymentManager(
    private val context: Context
) {

    fun requestPayment(id: String, amount: Double, cashier: String){
        val saleRequest = PaynetMessage(
            header = Header.Default,
            request = PaynetRequest(
                financial = Financial(
                    transaction = "sale",
                    id = Id(
                        cashier = cashier
                    ),
                    amounts = Amounts(
                        base = "%.2f".format(amount),
                        currencyCode = "RSD"
                    ),
                    options = Options.Default,
                )
            )
        )
        PaynetBroadcastReceiver.reservationId = id
        saleRequest.send()
    }

    private fun PaynetMessage.send(){
        val json = Json.encodeToString(this)
        val intent = Intent("com.payten.ecr.action")
        intent.setPackage("com.payten.paytenapos")
        intent.putExtra("ecrJson",json)
        intent.putExtra("senderIntentFilter","senderIntentFilter")
        intent.putExtra("senderPackage",context.packageName)
        context.sendBroadcast(intent)
    }
}