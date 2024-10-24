package com.zybooks.individpro

import FifthQuizScreen
import FinalQuizScreen
import FirstQuizScreen
import FourthQuizScreen
import PokeQuiz
import QuizResultScreen
import SecondQuizScreen
import SixthQuizScreen
import ThirdQuizScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zybooks.individpro.ui.HomeScreen
import com.zybooks.individpro.ui.LoginScreen
import com.zybooks.individpro.ui.SignUpScreen


/*
Author: Jan Brix Batalla
 */

@Composable
fun MyApp() {
    val navController = rememberNavController()

    var totalScore by remember { mutableStateOf(0) }
    var totalCorrectAnswers by remember { mutableStateOf(0) }

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
        //pass with param - same thing - copy&paste
        composable("first_quiz") {
            FirstQuizScreen(
                navController = navController,
                score = totalScore,                 //passes all args value to the X-QuizScreen
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->      //fun for score/correctAnswers updates
                totalScore = newScore                       //that are going to be used by the quiz_result
                totalCorrectAnswers = newCorrectAnswers
            }
        }
        composable("second_quiz") {
            SecondQuizScreen(
                navController = navController,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }
        composable("third_quiz") {
            ThirdQuizScreen(
                navController = navController,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }
        composable("fourth_quiz") {
            FourthQuizScreen(
                navController = navController,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }
        composable("fifth_quiz") {
            FifthQuizScreen(
                navController = navController,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }
        composable("sixth_quiz") {
            SixthQuizScreen(
                navController = navController,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }
        composable("final_quiz") {
            FinalQuizScreen(
                navController = navController,
                score = totalScore,
                correctAnswers = totalCorrectAnswers
            ) { newScore, newCorrectAnswers ->
                totalScore = newScore
                totalCorrectAnswers = newCorrectAnswers
            }
        }

        composable(
            route = "quiz_result/{totalCorrectAnswers}/{totalScore}",               //nav route for the quiz_result screen
            arguments = listOf(                                                     //lists inside of quizresult
                navArgument("totalCorrectAnswers") { type = NavType.IntType },//passes arg with int type
                navArgument("totalScore") { type = NavType.IntType }          //passes arg with int type
            )
        ) { backStackEntry -> // Lambda to define what happens when the composable is navigated to
            // Retrieve the totalCorrectAnswers argument from the navigation back stack entry
            val totalCorrectAnswers = backStackEntry.arguments?.getInt("totalCorrectAnswers") ?: 0
            // Retrieve the totalScore argument from the navigation back stack entry
            val totalScore = backStackEntry.arguments?.getInt("totalScore") ?: 0
            // Call the QuizResultScreen composable and pass the retrieved arguments to it
            QuizResultScreen(navController, totalCorrectAnswers, totalScore)
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