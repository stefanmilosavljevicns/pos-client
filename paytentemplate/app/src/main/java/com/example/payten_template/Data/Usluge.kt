package com.example.payten_template.Data

val Usluge = mapOf<String, Double>(
    "Sisanje" to 600.0,
    "Sisanje brkova i brade" to 900.0,
    "Bojenje kose" to 2000.0
)

enum class EUsluge(name: String){
    SISANJE("Sisanje"),
    SISANJE_BRIJANJE("Sisanje brkova i brade"),
    BOJENJE_KOSE("Bojenje kose")
}