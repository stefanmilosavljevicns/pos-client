package com.example.payten_template


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.payten_template.Data.User
import com.example.payten_template.Data.UserDB
import com.example.payten_template.Data.UserRepository
import com.example.payten_template.navigation.Screen
import com.example.payten_template.ui.theme.Background
import com.example.payten_template.ui.theme.ButtonColor
import com.example.payten_template.ui.theme.TextColor
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class UserRest(val id: String,val pin: Int, val nickname: String, val name: String,val lastName: String,val email: String,val creationDate: String,val location : String?,val role: String,val grade: Float)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(navController : NavController){
    var repository: UserRepository
    val customerDb = UserDB.getInstance(LocalContext.current.applicationContext)
    val customerDao = customerDb.todoDao()
    repository = UserRepository(customerDao)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
     {
         var mText by remember { mutableStateOf("") }
         val openDialogLogin = remember { mutableStateOf(false)  }
         val openDialogSync = remember { mutableStateOf(false)  }
         Spacer(modifier = Modifier.padding(bottom = 100.dp))
        Text(
            text = "Ulogujte se",
            color = TextColor
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Button(
            onClick = {
                        openDialogLogin.value = true
                      },
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)

        ) {
            Text(
                text = "LOGIN"
            )
        }
         Spacer(modifier = Modifier.weight(1f))
         Divider(color = ButtonColor, thickness = 0.8.dp, modifier = Modifier.padding(horizontal = 16.dp))
         Text(
             text = "Sinhronizuj listu korisnika",
             color = TextColor
         )
         Button(onClick = {
             openDialogSync.value = true
         },
             colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
         ) {
             Text(text = "SINHRONIZACIJA")
         }
         if(openDialogSync.value){

             var listItems = arrayOf("Ovde", "ce", "se", "vuci", "/getAllLocations", "sa", "backend-a")
             runBlocking{
                 val client = HttpClient(CIO) {
                     install(ContentNegotiation) {
                         json(Json {
                             prettyPrint = true
                             isLenient = true
                         })
                     }
                 }
                 val customer: Array<String> = client.get("http://161.97.170.99:8081/api/v1/getAllLocations").body()
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
                 modifier = Modifier.background(Background),
                 backgroundColor = Background,
                 text = null,
                         buttons = {
                     Column(
                         modifier = Modifier.background(Background).padding(15.dp),
                         verticalArrangement = Arrangement.Center,
                         horizontalAlignment = Alignment.CenterHorizontally
                     )
                     {
                         Spacer(modifier = Modifier.padding(5.dp))
                         Text(
                             text = "Izaberite lokaciju posla",
                             color = TextColor
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
                                 onValueChange = {
                                                 //komande
                                 },
                                 readOnly = true,
                                 label = { Text(text = "Poslovnica") },
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
                                         Toast.makeText(contextForToast, "Popunjavam tabelu za "+selectedItem, Toast.LENGTH_SHORT).show()
                                         runBlocking{
                                             val client = HttpClient(CIO) {
                                                 install(ContentNegotiation) {
                                                     json(Json {
                                                         prettyPrint = true
                                                         isLenient = true
                                                     })
                                                 }
                                             }
                                            val customer: Array<User> = client.get("http://161.97.170.99:8081/api/v1/getLocation/"+selectedItem).body()
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
                                 }}}
                     }
                 }
             )
         }
         if(openDialogLogin.value){



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
                 modifier = Modifier.background(Background),
                 backgroundColor = Background,
                 text = null,
                 buttons = {
                     Column(
                         modifier = Modifier.background(Background).padding(15.dp),
                         verticalArrangement = Arrangement.Center,
                         horizontalAlignment = Alignment.CenterHorizontally
                     )
                     {
                         Spacer(modifier = Modifier.padding(5.dp))
                         Text(
                             text = "Izaberite radnika",
                             color = TextColor
                         )
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
                                 label = { Text(text = "Radnik") },
                                 textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
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
                                 }}}
                         Spacer(modifier = Modifier.padding(5.dp))
                         Text(
                             text = "Unesite pin",
                             color = TextColor
                         )
                         Spacer(modifier = Modifier.padding(5.dp))
                         var pin by remember { mutableStateOf("") }
                         TextField(
                             visualTransformation =  PasswordVisualTransformation(),
                             value = pin,
                             colors = TextFieldDefaults.textFieldColors(
                                 backgroundColor = ButtonColor
                             ),
                             singleLine = true,
                             textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                             placeholder = {Text(text="PIN")},
                             onValueChange = {

                                 if (it.length <= 4) pin = it
                             },
                             keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword))
                         Spacer(modifier = Modifier.padding(5.dp))
                         Button(onClick = {
                             println("PIN IZ BAZE"+customerDb.todoDao().checkPin(selectedItem))
                             println("PIN"+pin)
                             if(customerDb.todoDao().checkPin(selectedItem).equals(Integer.parseInt(pin)
                                 )){
                                 navController.navigate(route = Screen.HomeScreen.route)
                             }
                             else{
                                 Toast.makeText(contextForToast, "Neispravan PIN, pokusajte ponovo!", Toast.LENGTH_SHORT).show()
                                 openDialogLogin.value = false
                             }

                         },
                             colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)
                         ) {
                             Text(text = "LOGIN")
                         }
                         }

         })
    }


}
    }








