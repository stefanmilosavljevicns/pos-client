package com.example.payten_template

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.payten_template.ui.navigation.Screen
import com.example.payten_template.ui.theme.ButtonColor
import io.github.g00fy2.quickie.QRResult
import io.github.g00fy2.quickie.ScanQRCode

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ScanQRCode()){
        if (it is QRResult.QRSuccess) {
            //Toast.makeText(context, it.content.rawValue, Toast.LENGTH_SHORT).show()
            navController.navigate("${Screen.Rezervacija.route}/${it.content.rawValue}")
        }
        if (it is QRResult.QRError){
            Toast.makeText(context, "Nevalidan QR code", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.White), contentAlignment = Alignment.TopCenter
        ) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(text = "Pozdrav!",  fontSize = 29.sp,

                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 30.dp, start = 40.dp))
            Row {
                Text(text = "Glavni Meni", color = Gray, fontSize = 13.sp,

                    modifier = Modifier.padding(start = 40.dp, top = 2.dp))
                Image(painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),null)
            }
        }
        Image(painterResource(id = R.drawable.ic_baseline_power_settings_new_24),null,
            modifier = Modifier
                .padding(end = 40.dp)
                .width(35.dp)
                .height(35.dp)
                .clickable(
                    enabled = true,
                    onClick = {
                        navController.navigate(route = Screen.LoginScreen.route)
                    }))


    }}
        val list = (1..4).map { it.toString() }
        val title = listOf("Rezervacije","Rezervisi","Arhiva","QR Skener")
        val desc = listOf("Pregledajte listu rezervacija","Izvrsite rezervaciju novog termina","Pregledajte arhivu","Skeniraj QR kod")
        val ids = listOf(R.drawable.rezervacije,R.drawable.rezervisi,R.drawable.arhiva,R.drawable.ic_qr_code)
        val navigations = listOf(
            Screen.Orders.route,
            Screen.Rezervisi.route,
            Screen.ArchivedOrders.route,
            Screen.LoginScreen.route)
        //val picture_ids = listOf()

        LazyVerticalGrid(

            verticalArrangement = Arrangement.Top,
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            ),
            content = {
                items(list.size) { index ->
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        backgroundColor = ButtonColor,
                        modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize().aspectRatio(1f).clickable(
                                enabled = true,
                                onClick = {
                                    if(index != 3){
                                        navController.navigate(route = navigations[index])
                                    }else{
                                        launcher.launch(null)
                                    }
                                }),
                        elevation = 10.dp,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier
                            .padding(12.dp)
                            .fillMaxSize()) {
                            Text(
                                maxLines = 1,
                                text = title[index],
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp),
                                style = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 2.sp,
                                        color = androidx.compose.ui.graphics.Color.White
                                )
                            )
                            Icon(painterResource(id = ids[index]),null,
                                modifier = Modifier

                                    .width(50.dp)
                                    .height(50.dp),
                                tint = Color.White
                                   )
                            Text(
                                text = desc[index],
                                fontSize = 13.sp,
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    color = androidx.compose.ui.graphics.Color.White
                                )
                            )
                        }



                    }

                }
            }
        )

}}





