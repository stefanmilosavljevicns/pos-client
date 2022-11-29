package com.example.payten_template

import android.graphics.Color
import android.graphics.fonts.FontFamily
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.payten_template.navigation.Screen
import com.example.payten_template.ui.theme.Background
import com.example.payten_template.ui.theme.ButtonColor
import com.example.payten_template.ui.theme.TextColor

@Composable
fun HomeScreen(navController: NavController) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(text = "Pozdrav Mitre!",  fontSize = 29.sp,

                style = TextStyle(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 30.dp, start = 40.dp))
            Row {
                Text(text = "Glavni Meni", color = Gray, fontSize = 13.sp,

                    modifier = Modifier.padding(start = 40.dp, top = 2.dp))
                Image(painterResource(id = R.drawable.ic_baseline_keyboard_arrow_down_24),null)
            }
        }
        Image(painterResource(id = R.drawable.ic_baseline_power_settings_new_24),null,
            modifier = Modifier.padding(end = 40.dp).width(35.dp).height(35.dp).clickable(
                enabled = true,
                onClick = {
                    navController.navigate(route = Screen.LoginScreen.route)
                }))
    }

}
