package com.example.payten_template.ui.shared

import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable

@Composable
fun CustomRadioButton(
    isSelected: Boolean,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    if(isSelected){
        Button(onClick = onClick) {
            content()
        }
    }else{
        OutlinedButton(onClick = onClick) {
            content()
        }
    }
}