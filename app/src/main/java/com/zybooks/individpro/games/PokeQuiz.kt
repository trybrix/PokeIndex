import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zybooks.individpro.R

@Composable
fun PokeQuiz(navController: NavController, currentUserEmail: String) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Retrieve saved stats from DataStore and observe them as state(.collectAsState - updates the savedStats
    val savedStats = StatsDataStore.getQuizStats(context, currentUserEmail).collectAsState(initial = 0 to 0)
    // Destructuring declaration - allows to unpack values from a data structure into separate variables
    val (fetchedTotalScore, fetchedTotalCorrectAnswers) = savedStats.value

    Image(
        painter = painterResource(id = R.drawable.quiz_homeimage),
        contentDescription = "Pokemon Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(radiusX = 25f, radiusY = 25f)
            },
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to PokeQuiz",
            modifier = Modifier
                .padding(top = 50.dp)
                .border(1.dp, Color.Black)
                .background(Color.White.copy(alpha = 0.8f))
                .padding(20.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Test your knowledge of the original 151 Pokémon in this 7-question quiz! " +
                    "Each question will have 4 possible choices. Can you collect them all?",
            modifier = Modifier
                .background(Color.White.copy(alpha = 0.8f))
                .border(1.dp, Color.Black)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = Color.Black,
            lineHeight = 28.sp
        )

        Spacer(modifier = Modifier.height(120.dp))

        Row(
            modifier = Modifier.padding(top = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Nav to the first question
            Button(onClick = { navController.navigate("quiz/0") }) {
                Text(text = "Start Quiz")
            }

            Button(
                onClick = {
                    navController.navigate("stats_screen/$fetchedTotalCorrectAnswers/$fetchedTotalScore")
                }
            ) {
                Text(text = "View Stats")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home/$currentUserEmail") }) {
            Text(text = "Home")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PokeQuizPreview() {
//    val navController = rememberNavController()
//    PokeQuiz(navController = navController, currentUserEmail = "email@example.com")
//}

//TODO - finish feedback - center questions/choices - set scores - calculate - check navCTRL - DONE
@Composable
fun FeedbackDialog(isAnswerCorrect: Boolean, onDismiss: () -> Unit) {
    //after the user confirms their answer this will run to let them know if they got the right answer or not
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = if (isAnswerCorrect) "Correct!" else "Incorrect") }, // title of the popup alert
        text = { Text(text = if (isAnswerCorrect) "You earned 100 points! \uD83D\uDC4F " else "You got it next time! \uD83D\uDC4D ") },//text inside the box of alert
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

@Composable
fun ConfirmAnswerDialog(
    selectedAnswer: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("Confirm Answer") },
        text = { Text("Are you sure you want to select \"$selectedAnswer\" as your answer?") },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}

// Shown after the last question
@Composable
fun QuizResultScreen(
    navController: NavController,
    totalCorrectAnswers: Int,
    totalScore: Int,
    currentUserEmail: String
) {
    val totalQuestions = 7
    val maxScore = totalQuestions * 100
    val scrollState = rememberScrollState()

    Image(
        painter = painterResource(id = R.drawable.calm_verticalbg),
        contentDescription = "Pokemon Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "🎊 Game over! 🎊", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Here are your stats:", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Amount Correct",
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "$totalCorrectAnswers out of $totalQuestions",
                fontSize = 24.sp,
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Total Earning",
                fontSize = 20.sp,
                color = Color.Black
            )
            Text(
                text = "\$$totalScore out of \$$maxScore",
                fontSize = 24.sp,
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            navController.navigate("quiz/0") {
                popUpTo("quiz/0") { inclusive = true } // Clear back stack to avoid score carry-over - double check MyApp
            }
        }) {
            Text("Play Again")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            navController.navigate("pokequiz/$currentUserEmail") {
                popUpTo("pokequiz/$currentUserEmail") { inclusive = true }
            }
        }) {
            Text("Home")
        }
    }
}

@Composable
fun StatsScreen(navController: NavController, totalCorrectAnswers: Int, totalScore: Int) {
    val totalQuestions = 7
    val maxScore = totalQuestions * 100

    Image(
        painter = painterResource(id = R.drawable.calm_verticalbg),
        contentDescription = "Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "📊 Your Stats 📊", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Amount Correct: $totalCorrectAnswers out of $totalQuestions",
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total Score: \$$totalScore out of \$$maxScore",
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.padding(top = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Button(onClick = { navController.navigate("first_quiz") }) {
                Text("Play")
            }

            Button(onClick = { navController.navigate("pokequiz/{currentUserEmail}") }) {
                Text("Home")
            }
        }
    }
}

// Instead of having the quizzes on a diff #QuizScreen,  it gets put into an array for readability and more compact
data class QuizQuestion(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val backgroundResource: Int
)

val questions = listOf(
    QuizQuestion(
        questionText = "How many original Pokémon are there?",
        options = listOf("99", "151", "50", "999"),
        correctAnswer = "151",
        backgroundResource = R.drawable.orig_pokemon
    ),
    QuizQuestion(
        questionText = "Which Pokémon evolves into Charizard?",
        options = listOf("Bulbasaur", "Squirtle", "Pidgey", "Charmander"),
        correctAnswer = "Charmander",
        backgroundResource = R.drawable.charizard
    ),
    QuizQuestion(
        questionText = "What type is Pikachu?",
        options = listOf("Water", "Fire", "Electric", "Grass"),
        correctAnswer = "Electric",
        backgroundResource = R.drawable.elements
    ),
    QuizQuestion(
        questionText = "What is the primary type of Snorlax?",
        options = listOf("Fighting", "Normal", "Rock", "Ground"),
        correctAnswer = "Normal",
        backgroundResource = R.drawable.snorlax
    ),
    QuizQuestion(
        questionText = "Which Pokémon is known as the \"Tiny Turtle Pokémon\"?",
        options = listOf("Squirtle", "Bulbasaur", "Charmander", "Pidgey"),
        correctAnswer = "Squirtle",
        backgroundResource = R.drawable.squirtle
    ),
    QuizQuestion(
        questionText = "What type is Gengar?",
        options = listOf("Ghost/Fighting", "Ghost/Poison", "Psychic/Fairy", "Dark/Ghost"),
        correctAnswer = "Ghost/Poison",
        backgroundResource = R.drawable.gengar
    ),
    QuizQuestion(
        questionText = "Which Pokémon is known as the \"Rock Snake Pokémon\"?",
        options = listOf("Golem", "Graveler", "Geodude", "Onix"),
        correctAnswer = "Onix",
        backgroundResource = R.drawable.onix
    )
)

// Same here, have a single style for each problem
@Composable
fun QuizScreen(
    navController: NavController,
    questionIndex: Int,
    score: Int,
    correctAnswers: Int,
    onScoreUpdated: (Int, Int) -> Unit
) {
    val question = questions[questionIndex]
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isAnswerCorrect by remember { mutableStateOf(false) }
    var isConfirmEnabled by remember { mutableStateOf(false) }
    var showFeedbackDialog by remember { mutableStateOf(false) }
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }
    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var showConfirmDialog by remember { mutableStateOf(false) }

    Box() {
        Image(
            painter = painterResource(id = question.backgroundResource),
            contentDescription = "Background Image",
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
                },
            contentScale = ContentScale.Crop
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = question.questionText,
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEach { option ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow)
                    .padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null
                )
                Text(text = option, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(if (isLandscape) 50.dp else 350.dp))

        Button(
            onClick = { showConfirmDialog = true },
            enabled = isConfirmEnabled,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showConfirmDialog && selectedOption != null) {
            ConfirmAnswerDialog(
                selectedAnswer = selectedOption!!,
                onConfirm = {
                    showConfirmDialog = false
                    if (selectedOption == question.correctAnswer) {
                        isAnswerCorrect = true
                        currentScore += 100
                        currentCorrectAnswers += 1
                        onScoreUpdated(currentScore, currentCorrectAnswers)
                    } else {
                        isAnswerCorrect = false
                    }
                    showFeedbackDialog = true
                },
                onCancel = { showConfirmDialog = false }
            )
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    if (questionIndex < questions.size - 1) {
                        navController.navigate("quiz/${questionIndex + 1}")
                    } else {
                        navController.navigate("quiz_result/$currentCorrectAnswers/$currentScore")
                    }
                }
            )
        }
    }
}