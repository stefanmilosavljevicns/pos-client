package com.example.payten_template
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.payten_template.ui.core.rezervisi.RezervisiViewModel
import com.example.payten_template.ui.shared.TerminListItem
import com.example.payten_template.ui.theme.ModalBackground

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
    var showDialog by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .blur(radius = if(showDialog) 32.dp else 0.dp)
                .padding(24.dp)
        ) {
            Row(horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Icon(modifier = Modifier
                    .clickable {
                        navController.popBackStack()
                    }
                    .padding(8.dp), imageVector = Icons.Filled.ArrowBack, contentDescription = "add reservation")
                Spacer(modifier = Modifier.size(8.dp))
                Column {
                    Text(text = "Rezervisi termin",  style = MaterialTheme.typography.h4)
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Dostupni termini", style = MaterialTheme.typography.subtitle1)
            Spacer(modifier = Modifier.size(16.dp))
            LazyColumn(
                content = {
                items(rezervisiViewModel.termini){ termin ->
                    TerminListItem(
                        termin = termin,
                        onClick = {
                            if(!showDialog){
                                showDialog = true
                            }
                        }
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            })
        }
        if(showDialog){
            Box(Modifier.fillMaxSize().background(ModalBackground))
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
                                    rezervisiViewModel.rezervisi()
                                    navController.popBackStack()
                                }
                            )
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                rezervisiViewModel.rezervisi()
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
    viewModel.generisiFakeTermine()
    Rezervisi(
        navController = NavController(LocalContext.current),
        rezervisiViewModel = viewModel
    )
}