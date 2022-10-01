package com.halfapum.commonextensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.halfapum.general.firstGeneralExtensions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        "".firstGeneralExtensions()
    }
}