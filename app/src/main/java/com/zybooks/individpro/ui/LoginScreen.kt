package com.zybooks.individpro.ui

import android.content.res.Configuration
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zybooks.individpro.R
import com.zybooks.individpro.data.UserManager
import com.zybooks.individpro.ui.theme.IndividProTheme

//Author: Jan Brix Batalla


//loginScreen for user to input and validates
//recomposition when run doesn't follow any convention and runs accordingly to prevent frame drops
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val scrollState = rememberScrollState()//imported allows landscape scroll
    val configuration = LocalConfiguration.current // need this for orientation
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

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
                .fillMaxWidth(if (isLandscape) 0.5f else 1f)
                .padding(top = if (isLandscape) 50.dp else 60.dp)
        )

        Spacer(modifier = Modifier.height(if (isLandscape) 50.dp else 140.dp))

        OutlinedTextField(//email
            value = email,
            onValueChange = {validEmailInput ->
                email = validEmailInput.trim()
                //allows for user to enter when theres an accidental whiteSpace
            },
            label = {Text("Email")},
            modifier = modifier// new
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(// change background color
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )

        )

        OutlinedTextField(//password
            value = password,
            onValueChange = {validPasswordInput ->
                password = validPasswordInput.trim()},
            label = {Text("Password")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
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
                onClick = {// should check email first then pw
                    if (UserManager.isUserRegistered(email)) {// goes to registerUsers and check
                        val user = UserManager.getAllUsers().find { it.email == email }// goes to list of users

                        if (user != null && user.password == password) {
                            errorMessage = ""
                            navController.navigate("home")
                        } else {
                            errorMessage = "Invalid password. Please try again."
                        }
                    } else {
                        errorMessage = "Email not registered. Please sign up."
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pokemon_navyBlue),
                    contentColor = Color.White
                )
            ) {
                Text("Log In")
            }

            Button(
                modifier = Modifier.padding(vertical = 14.dp),
                onClick = { navController.navigate("signup") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pokemon_navyBlue),
                    contentColor = Color.White
                )
            ) {
                Text("Sign Up")
            }
        }

        if (errorMessage.isNotEmpty()) {
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
    val navController = rememberNavController()
    IndividProTheme {
        LoginScreen(navController = navController)
    }
}
