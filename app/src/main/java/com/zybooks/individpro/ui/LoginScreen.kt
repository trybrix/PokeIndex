package com.zybooks.individpro.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zybooks.individpro.R
import com.zybooks.individpro.ui.theme.IndividProTheme

/*
Author: Jan Brix Batalla
 */

//loginScreen for user to input and validates
//recomposition when run doesn't follow any convention and runs accordingly to prevent frame drops
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: () -> Unit,
    onSignUpContinue: () -> Unit, // callback to navigate to signup screen
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()//imported allows landscape scroll

    //credentials - db not connected yet
    val correctEmail = "qwer@qwer.com"
    val correctPassword = "qwer"

    Image(
        painter = painterResource(id = R.drawable.login_picture),
        contentDescription = "Login Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { // blur effect
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop,// covers entire screen
    )

    Column(
        modifier = modifier//.verticalScroll imported
            .fillMaxSize()
            .padding(14.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(                            //from the lecture R. resource
            painter = painterResource(id = R.drawable.pokemongo_logo),
            contentDescription = "Pokemon GO Logo",
            modifier = Modifier
                .fillMaxSize(),

        )

        Spacer(modifier = Modifier.height(200.dp))

        OutlinedTextField(//email
            value = email,
            onValueChange = {validEmailInput ->
                email = validEmailInput.trimStart{it.isWhitespace()}
                //allows for user to enter when theres an accidental whiteSpace
            },
            label = {Text("Email")},
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(// change background color
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )

        )

        OutlinedTextField(//password
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")},
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),//to hide password when typing
        )

        Row (
            horizontalArrangement = Arrangement.spacedBy(24.dp)//syntax for adding gap
        ) {
            Button(
                modifier = Modifier.padding(vertical = 14.dp),
                onClick = {
                    if (email == correctEmail && password == correctPassword) {//hard coded email/pass- db not set
                        errorMessage = ""
                        onContinueClicked()//continues to homescreen
                    } else {
                        errorMessage = "Invalid email or password. Please try again."
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pokemon_navyBlue),//get resource from colors.xml
                    contentColor = Color.White//simple colors
                )
            ) {
                Text("Log In")
            }

            Button( //make new comp only put onClick = onSignUp
                modifier = Modifier.padding(vertical = 14.dp),
                onClick = onSignUpContinue,//goes to sign up screen
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pokemon_navyBlue),//get resource from colors.xml
                    contentColor = Color.White//simple colors
                )
            ) {
                Text("Sign Up")
            }
        }

        if (errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = modifier.padding(8.dp)
            )
        }
    }
}

//@Preview(showBackground = true, widthDp = 320, heightDp = 320, uiMode = UI_MODE_NIGHT_YES)//ask prof
@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    IndividProTheme {
        LoginScreen(
            onContinueClicked = {},  // Pass an empty lambda for the preview
            onSignUpContinue = {}    // Pass an empty lambda for the preview
        )
    }
}
