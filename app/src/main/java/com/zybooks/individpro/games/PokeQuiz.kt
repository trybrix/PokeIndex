import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
    val pressStart2PFont = FontFamily(
        Font(R.font.pressstart2p_regular)
    )
    //simple background
    Image(
        painter = painterResource(id = R.drawable.whos_template),
        contentDescription = "Home Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop,
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
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(R.color.pokemon_navyBlue))
                .border(1.dp, Color.Black)
                .padding(20.dp),
            style = androidx.compose.ui.text.TextStyle(
                color = colorResource(R.color.pokemon_yellow),
                fontSize = 16.sp,
                fontFamily = pressStart2PFont
            )
        )

        Spacer(modifier = Modifier.height(50.dp))

        //button to nav to start the quiz - style is not needed just added for fun
        Button(onClick = { navController.navigate("first_quiz") }) {
            Text(text = "Start Quiz",
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = pressStart2PFont
                )
            )
        }

        //nav back to HOME
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Home",
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = pressStart2PFont
                )
            )
        }
    }
}

//TODO - finish feedback - center questions/choices - set scores - calculate - check navCTRL
@Composable
fun FeedbackDialog(isAnswerCorrect: Boolean, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = if (isAnswerCorrect) "Correct!" else "Incorrect") },
        text = { Text(text = if (isAnswerCorrect) "You earned 100 points!" else "Try again!") },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    )
}

//comp screen for the first question
//copy&paste for the other questions todo - create 7 questions w/ diff UI
@Composable
fun FirstQuizScreen(navController: NavController) {
    var selectedOption by rememberSaveable { mutableStateOf<String?>(null) }//init to null first because user !chosen an answer yet
    var isAnswerCorrect by rememberSaveable { mutableStateOf(false) }//^
    var isConfirmEnabled by rememberSaveable { mutableStateOf(false) }//^
    var showFeedbackDialog by rememberSaveable { mutableStateOf(false) }//init to false


    Image(
        painter = painterResource(id = R.drawable.calm_verticalbg),
        contentDescription = "Home Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop,
    )

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
                text = "Points Earned: $",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Text(
                text = "How many original Pokémon are there?",
                fontSize = 24.sp,
                modifier = Modifier
                    .padding(16.dp)
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
                    .fillMaxWidth()
                    .padding(8.dp)
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
                } else {
                    isAnswerCorrect = false
                }
                showFeedbackDialog = true
            },
            enabled = isConfirmEnabled,  //similar in classdemo
            modifier = Modifier
                .padding(top = 16.dp)
            ) {
            Text("Confirm")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PokeQuizPreview() {
    val navController = rememberNavController()
    FirstQuizScreen(navController = navController)
}