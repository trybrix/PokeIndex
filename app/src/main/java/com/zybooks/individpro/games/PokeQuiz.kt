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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zybooks.individpro.R

// sets navController to access the quiz home screen
@Composable
fun PokeQuiz(navController: NavController) {

    Image(
        painter = painterResource(id = R.drawable.pokeball_logo),
        contentDescription = "Pokemon Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop
    )

    // shows welcome screen and 2 buttons to start quiz and to go back to HOME
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // simple welcome text for the user
        Text(
            text = "Welcome to PokeQuiz",
            modifier = Modifier
                .border(1.dp, Color.Black)
                .padding(20.dp),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        //button to nav to start the quiz - style is not needed just added for fun
        Button(onClick = { navController.navigate("first_quiz") }) {
            Text(text = "Start Quiz")
        }

        //nav back to HOME
        Button(onClick = { navController.navigate("pokequiz") }) {
            Text(text = "Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokeQuizPreview() {
    val navController = rememberNavController()
    PokeQuiz(navController = navController)
}

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

//Quiz_Result what is visible to the user after they finish the quiz
@Composable
fun QuizResultScreen(navController: NavController, totalCorrectAnswers: Int, totalScore: Int) {
    val totalQuestions = 7
    val maxScore = totalQuestions * 100

    //simple bg
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
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //text for user
        Text(text = "\uD83C\uDF8AGame over!\uD83C\uDF8A", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Here are your stats:", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))

        //2 Columns inside of the column just so that i can change style of the users scores
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Amount Correct",
                fontSize = 20.sp,
                color = Color.Black
            )

            Text(
                text = "$totalCorrectAnswers out of $totalQuestions",
                fontSize = 26.sp,
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Earning",
                fontSize = 26.sp,
                color = Color.Black
            )

            Text(
                text = "\$$totalScore out of \$$maxScore",
                fontSize = 20.sp,
                color = Color.Green,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(8.dp)
            )

        }

        Spacer(modifier = Modifier.height(32.dp))

        //goes back to the first_quiz is user want to play again with 0/0 scores
        Button(onClick = {
            navController.navigate("first_quiz")
        }) {
            Text("Play Again")
        }

    }
}


//comp screen for the first question
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun FirstQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(0) }
    var currentCorrectAnswers by remember { mutableStateOf(0) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.orig_pokemon),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "How many original Pokémon are there?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("99", "151", "50", "999")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "151") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //show feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    navController.navigate("second_quiz")
                }
            )
        }
    }
}

//SECOND
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun SecondQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.charizard),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Which Pokémon evolves into Charizard?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("Bulbasaur", "Squirtle", "Pidgey", "Charmander")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "Charmander") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //show feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    navController.navigate("third_quiz")
                }
            )
        }
    }
}

//THIRD
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun ThirdQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.elements),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "What type is Pikachu?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("Water", "Fire", "Electric", "Grass")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "Electric") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    navController.navigate("fourth_quiz")
                }
            )
        }
    }
}

//FOURTH
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun FourthQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.snorlax),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "What is the primary type of Snorlax?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("Fighting", "Normal", "Rock", "Ground")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "Normal") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    navController.navigate("fifth_quiz")
                }
            )
        }
    }
}

//FIFTH
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun FifthQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.squirtle),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Which Pokémon is known as the \"Tiny Turtle Pokémon\"?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("Squirtle", "Bulbasaur", "Charmander", "Pidgey")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "Squirtle") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    navController.navigate("sixth_quiz")
                }
            )
        }
    }
}

//SIXTH
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun SixthQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.gengar),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "What type is Gengar?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("Ghost/Fighting", "Ghost/Poison", "Psychic/Fairy", "Dark/Ghost")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "Ghost/Poison") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    navController.navigate("final_quiz")
                }
            )
        }
    }
}

//Final
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun FinalQuizScreen(navController: NavController, score: Int, correctAnswers: Int, onScoreUpdated: (Int, Int) -> Unit) {
    var selectedOption by remember { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by remember { mutableStateOf(false) }//^
    var isConfirmEnabled by remember { mutableStateOf(false) }//^
    var showFeedbackDialog by remember { mutableStateOf(false) }//init to false
    var currentScore by remember { mutableStateOf(score) }
    var currentCorrectAnswers by remember { mutableStateOf(correctAnswers) }

    //simple bg
    Box() {
        Image(
            painter = painterResource(id = R.drawable.onix),
            contentDescription = "Pokemon Background Image",
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
            .padding(16.dp),
        //centers overall
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column (
            modifier = Modifier
                .padding(10.dp),
            //centers text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Points Earned: $currentScore",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Which Pokémon is known as the \"Rock Snake Pokémon\"?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.White).padding(4.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        //creates list then iterate through it
        val options = listOf("Golem", "Graveler", "Geodude", "Onix")
        options.forEach { option ->
            //row for radiobtn and text
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Yellow).padding(4.dp)
                    .clickable {
                        selectedOption = option
                        isConfirmEnabled = true//change to true when user chooses
                    },
                //centers
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = null //{ selectedOption = option } no need to interact directly with radbtn
                )
                Text(text = option,
                    modifier = Modifier
                        .padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(350.dp))

        Button(
            onClick = {
                if (selectedOption == "Onix") {
                    isAnswerCorrect = true
                    currentScore += 100
                    currentCorrectAnswers += 1
                    onScoreUpdated(currentScore, currentCorrectAnswers)
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true  //feedback dialog
            },
            enabled = isConfirmEnabled,  //same thing in demo
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Confirm")
        }

        if (showFeedbackDialog) {
            FeedbackDialog(
                isAnswerCorrect = isAnswerCorrect,
                onDismiss = {
                    showFeedbackDialog = false
                    //goes to quiz_result to get args
                    navController.navigate("quiz_result/$currentCorrectAnswers/$currentScore")
                }
            )
        }
    }
}