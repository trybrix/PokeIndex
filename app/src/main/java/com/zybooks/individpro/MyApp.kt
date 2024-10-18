package com.zybooks.individpro

import FirstQuizScreen
import PokeQuiz
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zybooks.individpro.ui.HomeScreen
import com.zybooks.individpro.ui.LoginScreen
import com.zybooks.individpro.ui.SignUpScreen


/*
Author: Jan Brix Batalla
 */

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignUpScreen(navController = navController)
        }
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("pokequiz") {
            PokeQuiz(navController = navController)
        }
        composable("first_quiz") {
            FirstQuizScreen(navController = navController)
        }
    }
}