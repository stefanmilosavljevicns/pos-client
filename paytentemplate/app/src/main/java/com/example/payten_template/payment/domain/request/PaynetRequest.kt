package com.example.payten_template.payment.domain.request

import com.example.payten_template.payment.domain.PaynetResponse
import kotlinx.serialization.Serializable

@Serializable
data class PaynetMessage(
    val header: Header,
    val request: PaynetRequest? = null,
    val response: PaynetResponse? = null
)
@Serializable
data class Header(
    val length: Int,
    val hash: String,
    val version: String,
){
    companion object{
        val Default = Header(
            length = 282,
            hash = "XXX",
            version = "01"
        )
    }
}
@Serializable
data class PaynetRequest(
    val financial: Financial? = null,
    //val command: Any? = null
)
@Serializable
data class Financial(
    val transaction: String? = null,
    val id: Id? = null,
    val original: Original? = null,
    val amounts: Amounts? = null,
    val options: Options? = null,
    val services: Services? = null,
)
@Serializable
data class Id(
    val invoice: String? = null,
    val ecr: String? = null,
    val acquirer: String? = null,
    val cashier: String? = null,
    val custom: List<Custom>? = null,
)
@Serializable
data class Custom(
    val type: String,
    val value: String,
)
@Serializable
data class Original(
    val approvalCode: String,
    val reference: String,
)
@Serializable
data class Amounts(
    val base: String? = null,
    val cashback: String? = null,
    val tip: String? = null,
    val currencyCode: String? = null,
)
@Serializable
data class Options(
    val language: String,
    val print: String,
){
    companion object{
        val Default = Options(
            language = "sr",
            print = false.toString()
        )
    }
}
@Serializable
data class Services(
    val installments: Installments,
)
@Serializable
data class Installments(
    val rate: String,
    val fee: String,
    val apr: String,
    val delay: String,
    val amounts: AmountsDelayed,
    val count: String,
    val paymentMsg: String,
    val moreInfoMsg: String,
    val accToIssCondMsg: String,
)
@Serializable
data class AmountsDelayed(
    val due: String,
    val first: String,
    val subs: String,
    val currencyCode: String,
)
