package com.example.payten_template.payment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import com.example.payten_template.MainActivity

class PaynetBroadcastReceiver: BroadcastReceiver() {

    companion object{
        var reservationId: String? = null
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val response = intent?.getStringExtra("ResponseResult")
        Log.i("Response", response?:"Blank")

        context?.startActivity(
            Intent(context, MainActivity::class.java).apply {
                response?.let {
                    putExtra("ResponseResult", it)
                }
                flags = FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
        )
    }
}