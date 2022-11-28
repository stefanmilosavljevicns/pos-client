package com.example.payten_template

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.payten_template.navigation.Screen
import com.example.payten_template.ui.theme.Background
import com.example.payten_template.ui.theme.ButtonColor
import com.example.payten_template.ui.theme.TextColor

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        Spacer(modifier = Modifier.padding(bottom = 100.dp))
        Text(
            text = "Main Menu",
            color = TextColor
        )
        Button(
            onClick = {
                navController.navigate(route = Screen.LoginScreen.route)
            },
            modifier = Modifier
                .height(50.dp)
                .width(150.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = ButtonColor)

        ) {
            Text(
                text = "Log out"
            )
        }
    }
}