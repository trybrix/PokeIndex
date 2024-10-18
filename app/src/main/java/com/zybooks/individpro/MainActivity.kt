package com.zybooks.individpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zybooks.individpro.ui.HomeScreen
import com.zybooks.individpro.ui.theme.IndividProTheme

/*
Author: Jan Brix Batalla
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndividProTheme() {
                MyApp()
            }
        }
    }
}