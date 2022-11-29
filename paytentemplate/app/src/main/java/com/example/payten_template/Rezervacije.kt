package com.example.payten_template

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.ui.tooling.preview.Preview
import com.example.payten_template.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Rezervacije(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.TopCenter
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = "Lista rezervacija",  fontSize = 29.sp,

                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 30.dp, start = 40.dp))
                    Row {
                        Text(text = "Ovde se nalazi lista svih trenutnih rezervacija", color = Color.Gray, fontSize = 13.sp,

                            modifier = Modifier.padding(start = 40.dp, top = 2.dp))
                        Image(painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),null)
                    }
                }
                Image(
                    painterResource(id = R.drawable.ic_baseline_power_settings_new_24),null,
                    modifier = Modifier
                        .padding(end = 40.dp)
                        .width(35.dp)
                        .height(35.dp)
                        .clickable(
                            enabled = true,
                            onClick = {
                                navController.navigate(route = Screen.HomeScreen.route)
                            }))


            }
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = {
                navController.navigate(Screen.Rezervisi.route)
            }) {
            Icon(Icons.Filled.Add,"add reservation")
        }
    }

}

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun RezervacijePreview(){
    Rezervacije(navController = NavController(LocalContext.current))
}