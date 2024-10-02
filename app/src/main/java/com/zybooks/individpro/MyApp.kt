package com.zybooks.individpro

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.zybooks.individpro.ui.HomeScreen
import com.zybooks.individpro.ui.LoginScreen
import com.zybooks.individpro.ui.SignUpScreen


/*
Author: Jan Brix Batalla
 */

@Composable
fun MyApp() {
    var currentScreen by rememberSaveable{ mutableStateOf("login") }// TODO: This state should be hoisted - done

    when (currentScreen){
        "home" -> {
            HomeScreen()
        }
        "login" -> {
            LoginScreen (
                onContinueClicked = { currentScreen = "home"},//navs to hs
                onSignUpContinue = { currentScreen = "signup"}//to signup
            )
        }
        "signup" -> {
            SignUpScreen(
                onSignUpComplete = { currentScreen = "login" },
                onLoginClick = { currentScreen = "login"}
            )
        }
    }
}
