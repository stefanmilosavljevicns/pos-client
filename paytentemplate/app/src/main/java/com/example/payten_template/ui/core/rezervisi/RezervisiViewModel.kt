package com.example.payten_template.ui.core.rezervisi

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.payten_template.Data.Termin
import java.util.*

class RezervisiViewModel: ViewModel() {

    val termini = mutableStateListOf<String>()

    init {
        generisiFakeTermine()
    }

    fun generisiFakeTermine(){
        val fakeTermini = listOf(
            "24.10 10:30",
            "24.10 11:00",
            "24.10 12:30",
            "24.10 14:00",
            "25.10 09:30",
            "25.10 10:00",
            "25.10 11:00",
        )
        termini.clear()
        termini.addAll(fakeTermini)
    }

    fun rezervisi(){

    }

}