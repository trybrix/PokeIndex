package com.zybooks.individpro

import PokeQuiz
import QuizResultScreen
import QuizScreen
import StatsDataStore
import StatsScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zybooks.individpro.ui.HomeScreen
import com.zybooks.individpro.ui.LoginScreen
import com.zybooks.individpro.ui.SignUpScreen
import kotlinx.coroutines.launch

/*
Author: Jan Brix Batalla
 */

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var totalScore by remember { mutableStateOf(0) }
    var totalCorrectAnswers by remember { mutableStateOf(0) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navController = navController,
                onLoginSuccess = { userEmail ->
                    navController.navigate("home/$userEmail")
                }
            )
        }

        composable("signup") {
            SignUpScreen(navController = navController)
        }

        composable("home/{userEmail}") { backStackEntry ->
            val currentUserEmail = backStackEntry.arguments?.getString("userEmail") ?: ""
            HomeScreen(navController = navController, currentUserEmail = currentUserEmail)
        }

        composable("pokequiz/{currentUserEmail}") { backStackEntry ->
            val currentUserEmail = backStackEntry.arguments?.getString("currentUserEmail") ?: ""
            totalScore = 0 // Reset for new game
            totalCorrectAnswers = 0 // Reset for new game
            PokeQuiz(navController = navController, currentUserEmail = currentUserEmail)
        }

        // QuizScreen route for each question
        composable(
            route = "quiz/{questionIndex}",
            arguments = listOf(navArgument("questionIndex") { type = NavType.IntType })
        ) { backStackEntry ->
            val questionIndex = backStackEntry.arguments?.getInt("questionIndex") ?: 0

            QuizScreen(
                navController = navController,
                questionIndex = questionIndex,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }

        composable(
            route = "quiz_result/{totalCorrectAnswers}/{totalScore}",
            arguments = listOf(
                navArgument("totalCorrectAnswers") { type = NavType.IntType },
                navArgument("totalScore") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val finalCorrectAnswers = backStackEntry.arguments?.getInt("totalCorrectAnswers") ?: 0
            val finalScore = backStackEntry.arguments?.getInt("totalScore") ?: 0
            val currentUserEmail = backStackEntry.arguments?.getString("currentUserEmail") ?: ""

            QuizResultScreen(
                navController = navController,
                totalCorrectAnswers = finalCorrectAnswers,
                totalScore = finalScore,
                currentUserEmail = currentUserEmail
            )

            // Save final score and correct answers to DataStore
            LaunchedEffect(Unit) {
                coroutineScope.launch {
                    StatsDataStore.saveQuizStats(context, currentUserEmail, finalScore, finalCorrectAnswers)
                }
            }
        }

        composable(
            route = "stats_screen/{totalCorrectAnswers}/{totalScore}",
            arguments = listOf(
                navArgument("totalCorrectAnswers") { type = NavType.IntType },
                navArgument("totalScore") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val totalCorrectAnswers = backStackEntry.arguments?.getInt("totalCorrectAnswers") ?: 0
            val totalScore = backStackEntry.arguments?.getInt("totalScore") ?: 0

            StatsScreen(navController, totalCorrectAnswers, totalScore)
        }
    }
}

/*
*todo}{backStackEntry-- refers to an object that represents an entry in the navigation back stack of a navigation controller in Android Jetpack Compose
*
*todo}{navigation back stack-- is the collection of all the screens (or destinations) that the user has visited in an app.
* When a user navigates from one screen to another, each new screen is pushed onto the stack,
* and when they press the "back" button, the most recent screen is popped from the stack, returning to the previous screen.
*
* todo}{backStackEntry-- is an object that represents one of those screens currently in the navigation back stack.
*  In Jetpack Compose, you often use backStackEntry to access arguments that were passed to the.
*  composable destination or to get information about the screen in the navigation stack.
*
* todo}{ backStackEntry.arguments?.getInt("totalCorrectAnswers") = gets totalCorrectAnswers
*
* todo}{ backStackEntry.arguments?.getInt("totalScore") = gets totalScore
 */