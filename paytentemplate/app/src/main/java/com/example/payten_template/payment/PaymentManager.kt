package com.example.payten_template.payment

import android.content.Context
import android.content.Intent
import com.example.payten_template.Data.Rezervacija
import com.example.payten_template.payment.domain.request.*
import com.example.payten_template.utils.Base64Image
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

    fun requestPrintBill(rezervacija: Rezervacija, amount: Double){
        val printRequest = PaynetMessage(
            header = Header.Default,
            request = PaynetRequest(
                command = Command(
                    printer = Printer(
                        type = "JSON",
                        printLines = listOf(
                            PrintLine(
                                type = "text",
                                style = "CONDENSED",
                                content = "============= Fiskalni račun ==============="
                            ),
                            PrintLine(
                                type = "text",
                                style = "TITLE",
                                content = "Frizerski salon"
                            ),
                            PrintLine(
                                type = "text",
                                style = "TITLE",
                                content = "LIFE IS SHORT"
                            ),
                            PrintLine(
                                type = "text",
                                style = "TITLE",
                                content = "TO HAVE A BAD HAIRCUT"
                            ),
                            PrintLine(
                                type = "text",
                                style = "TITLE",
                                content = "------------------------"
                            ),
                            PrintLine(
                                type = "text",
                                style = "CONDENSED",
                                content = "Vrsta usluge:\t\t${rezervacija.services?:"Sisanje"}"
                            ),
                            PrintLine(
                                type = "text",
                                style = "CONDENSED",
                                content = "Cena usluge:\t\t%.2f RSD".format(amount)
                            ),
                            PrintLine(
                                type = "text",
                                style = "CONDENSED",
                                content = "========================================="
                            ),
                            PrintLine(
                                type = "image",
                                content = Base64Image.haircut
                            ),
                            PrintLine(
                                type = "text",
                                style = "CONDENSED",
                                content = "========== Kraj fiskalnog računa =========="
                            ),
                            PrintLine(
                                type = "text",
                                style = "CONDENSED",
                                content = "========= Powered by team Sputnik ========="
                            ),
                        )
                    )
                )
            )
        )
        printRequest.send()
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