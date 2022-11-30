package com.example.payten_template.payment.domain

import com.example.payten_template.payment.domain.request.Financial
import kotlinx.serialization.Serializable

@Serializable
data class PaynetResponse(
    var financial: ResponseFinancial
)

@Serializable
data class ResponseFinancial(
    var result: PaymentResult
)

@Serializable
data class PaymentResult(
    val code: String,
    val message: String,
)
