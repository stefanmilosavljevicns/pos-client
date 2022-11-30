package com.example.payten_template
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.payten_template.Data.Rezervacija
import com.example.payten_template.Data.Rezervacija.Companion.reservationDateFormat
import com.example.payten_template.ui.core.rezervisi.RezervisiViewModel
import com.example.payten_template.ui.shared.CustomRadioButton
import com.example.payten_template.ui.shared.TerminListItem
import com.example.payten_template.ui.theme.ModalBackground
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Rezervisi(
    navController: NavController,
    rezervisiViewModel: RezervisiViewModel = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var name by remember {
        mutableStateOf("")
    }
    var currentTermin by remember {
        mutableStateOf("")
    }
    var showDialog by remember {
        mutableStateOf(false)
    }
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .blur(radius = if (showDialog) 32.dp else 0.dp)
                .padding(24.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(8.dp), imageVector = Icons.Filled.ArrowBack, contentDescription = "back")
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(text = "Rezervisi termin za",  style = MaterialTheme.typography.h4)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                CustomRadioButton(
                    isSelected = rezervisiViewModel.selectedDate.toLocalDate().equals(LocalDate.now()),
                    onClick = {
                        rezervisiViewModel.selectedDate = LocalDateTime.now()
                        rezervisiViewModel.generisiTermine()
                    }
                ) {
                    Text("Danas")
                }
                Spacer(modifier = Modifier.size(8.dp))
                CustomRadioButton(
                    isSelected = rezervisiViewModel.selectedDate.toLocalDate().equals(LocalDate.now().plusDays(1)),
                    onClick = {
                    rezervisiViewModel.selectedDate = LocalDateTime
                        .now()
                        .withHour(0)
                        .withMinute(0)
                        .plusDays(1)
                    rezervisiViewModel.generisiTermine()
                }) {
                    Text("Sutra")
                }
                Spacer(modifier = Modifier.size(8.dp))
                CustomRadioButton(
                    isSelected = false,
                    onClick = { /*TODO*/ }
                ) {
                    Text("Izaberi")
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Dostupni termini", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.size(16.dp))
            if(rezervisiViewModel.termini.isNotEmpty()){
                LazyColumn(
                    content = {
                    items(rezervisiViewModel.termini){ termin ->
                        TerminListItem(
                            termin = termin.format(DateTimeFormatter.ofPattern("HH:mm dd.MM")),
                            onClick = {
                                if(!showDialog){
                                    currentTermin = termin.format(DateTimeFormatter.ofPattern(
                                        reservationDateFormat.toPattern()))
                                    selectedIndex = 0
                                    showDialog = true
                                }
                            }
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                    }
                })
            }else{
                Text("Nema slobodnih termina za izabrani datum")
            }
        }
        if(showDialog){
            Box(
                Modifier
                    .fillMaxSize()
                    .background(ModalBackground))
            Box(modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ){
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Icon(
                            modifier = Modifier
                                .align(Alignment.End)
                                .clickable {
                                    showDialog = false
                                }
                                .padding(bottom = 8.dp)
                            ,
                            imageVector = Icons.Filled.Close,
                            contentDescription = "close dialog",
                        )
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text("Unesite ime")
                            },
                            value = name,
                            onValueChange = {
                                name = it
                            },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    keyboardController?.hide()
                                }
                            )
                        )
                        Box{
                            val dropDownItems = rezervisiViewModel.usluge.filter {
                                rezervisiViewModel.checkTermin(currentTermin, it)
                            }
                            OutlinedButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    expanded = true
                                },
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = rezervisiViewModel.usluge[selectedIndex],
                                        textAlign = TextAlign.Start
                                    )
                                    if (dropDownItems.size > 1){
                                        Icon(
                                            imageVector = Icons.Filled.ArrowDropDown,
                                            contentDescription = "show menu",
                                        )
                                    }
                                }
                            }
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false},
                            ) {
                                dropDownItems.forEachIndexed { index, usluga ->
                                    DropdownMenuItem(onClick = {
                                        selectedIndex = index
                                        expanded = false
                                    }) {
                                        Text(text = usluga)
                                    }
                                }
                            }
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            enabled = name.isNotBlank(),
                            onClick = {
                                rezervisiViewModel.rezervisi(
                                    Rezervacija(
                                        name = name,
                                        reservation = currentTermin,
                                        worker = "worker",
                                        telegram = false,
                                        placeno = false,
                                        services = rezervisiViewModel.usluge[selectedIndex]
                                    )
                                )
                                navController.popBackStack()
                        }) {
                            Text("Rezervisi", style = MaterialTheme.typography.button)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun RezervisiPreview(){
    val viewModel = RezervisiViewModel()
    viewModel.generisiTermine()
    Rezervisi(
        navController = NavController(LocalContext.current),
        rezervisiViewModel = viewModel
    )
}