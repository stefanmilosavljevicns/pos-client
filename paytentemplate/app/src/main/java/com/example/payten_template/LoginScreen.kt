package com.example.payten_template


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.payten_template.Data.User
import com.example.payten_template.Data.UserDB
import com.example.payten_template.Data.UserRepository
import com.example.payten_template.navigation.Screen
import com.example.payten_template.ui.theme.ButtonColor
import com.example.payten_template.ui.theme.primaryColor
import com.example.payten_template.ui.theme.whiteBackground
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(navController: NavController) {
    var repository: UserRepository
    val customerDb = UserDB.getInstance(LocalContext.current.applicationContext)
    val customerDao = customerDb.todoDao()
    repository = UserRepository(customerDao)
    var mText by remember { mutableStateOf("") }
    val openDialogLogin = remember { mutableStateOf(false) }
    val openDialogSync = remember { mutableStateOf(false) }
    val image = painterResource(id = R.drawable.loginpayten)
    val focusRequester = remember { FocusRequester() }


    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Image(
                image,
                null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()

            )

        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(20.dp))
                .fillMaxHeight(0.60f)
                .background(whiteBackground)
                .padding(20.dp)
        ) {
            Text(
                text = "Dobrodošli, ulogujte se!",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                fontSize = 30.sp
            )
            Spacer(modifier = Modifier.padding(25.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.padding(10.dp))
                Button(

                    colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor),
                    onClick = { openDialogLogin.value = true },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                ) {
                    Text(text = "Login", fontSize = 20.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.padding(15.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Sinhronizuj bazu",
                    modifier = Modifier.clickable(onClick = {
                        openDialogSync.value = true
                    })
                )
                Spacer(modifier = Modifier.padding(20.dp))
            }


        }

    }
    //DIALOZI
    //                openDialogSync.value = true
    //                openDialogLogin.value = true
    if (openDialogSync.value) {

        var listItems =
            arrayOf("Ovde", "ce", "se", "vuci", "/getAllLocations", "sa", "backend-a")
        runBlocking {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        isLenient = true
                    })
                }
            }
            val customer: Array<String> =
                client.get("http://161.97.170.99:8081/api/v1/getAllLocations").body()
            listItems = customer

        }
        val contextForToast = LocalContext.current.applicationContext

        var selectedItem by remember {
            mutableStateOf(listItems[0])
        }
        var expanded by remember {
            mutableStateOf(false)
        }

        AlertDialog(
            onDismissRequest = {
                openDialogSync.value = false
            },
            title = null,
            modifier = Modifier.background(whiteBackground),
            shape = RoundedCornerShape(20.dp),
            backgroundColor = whiteBackground,
            text = null,
            buttons = {
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(shape = RoundedCornerShape(20.dp)),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Izaberite lokaciju posla",

                        )
                    Spacer(modifier = Modifier.padding(10.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        // text field
                        TextField(

                            value = selectedItem,
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                color = Color.White
                            ),
                            onValueChange = {
                                //komande
                            },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = ButtonColor)
                        )

                        // menu
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {

                            listItems.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(onClick = {
                                    selectedItem = selectedOption
                                    Toast.makeText(
                                        contextForToast,
                                        "Popunjavam tabelu za " + selectedItem,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    runBlocking {
                                        val client = HttpClient(CIO) {
                                            install(ContentNegotiation) {
                                                json(Json {
                                                    prettyPrint = true
                                                    isLenient = true
                                                })
                                            }
                                        }
                                        val customer: Array<User> =
                                            client.get("http://161.97.170.99:8081/api/v1/getLocation/" + selectedItem)
                                                .body()
                                        repository.deleteAllTodos()
                                        for (i in customer.indices) {
                                            repository.addTodo(customer[i])
                                        }
                                    }
                                    expanded = false
                                    openDialogSync.value = false
                                }) {
                                    Text(text = selectedOption)
                                }
                            }
                        }
                    }
                }
            }
        )
    }
    if (openDialogLogin.value) {


        val contextForToast = LocalContext.current.applicationContext
        val listItems = customerDb.todoDao().getAll()
        var selectedItem by remember {
            mutableStateOf(listItems[0])
        }


        var expanded by remember {
            mutableStateOf(false)
        }
        AlertDialog(
            onDismissRequest = {
                openDialogLogin.value = false
                mText = ""
            },

            title = null,
            modifier = Modifier
                .background(whiteBackground)
                .padding(15.dp)
                .clip(shape = RoundedCornerShape(20.dp)),


            backgroundColor = whiteBackground,
            text = null,
            buttons = {
                Column(

                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {

                    Text(
                        text = "Izaberite radnika",

                        )
                    Spacer(modifier = Modifier.padding(5.dp))
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        // text field

                        TextField(
                            value = selectedItem,
                            onValueChange = {
                                //komande
                            },
                            readOnly = true,
                            textStyle = LocalTextStyle.current.copy(
                                textAlign = TextAlign.Center,
                                color = Color.White
                            ),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )

                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = ButtonColor)
                        )

                        // menu
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            listItems.forEach { selectedOption ->
                                // menu item
                                DropdownMenuItem(onClick = {
                                    selectedItem = selectedOption

                                    expanded = false
                                }) {
                                    Text(text = selectedOption)
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(
                        text = "Unesite pin",

                        )
                    Spacer(modifier = Modifier.padding(5.dp))
                    var pin by remember { mutableStateOf("") }
                    TextField(
                        visualTransformation = PasswordVisualTransformation(),
                        value = pin,
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = ButtonColor
                        ),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(
                            textAlign = TextAlign.Center,
                            color = Color.White
                        ),
                        placeholder = { Text(text = "PIN", color = Color.White) },
                        onValueChange = {

                            if (it.length < 4) pin = it
                            else if (it.length == 4) {
                                pin = it
                                if (customerDb.todoDao().checkPin(selectedItem).equals(
                                        Integer.parseInt(pin)
                                    )
                                ) {
                                    openDialogLogin.value = false
                                    navController.navigate(route = Screen.HomeScreen.route)
                                } else {
                                    Toast.makeText(
                                        contextForToast,
                                        "Neispravan PIN, pokusajte ponovo!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    openDialogLogin.value = false
                                }
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                    )

                }

            })
    }


}








