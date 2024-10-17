package com.zybooks.individpro.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zybooks.individpro.R
import com.zybooks.individpro.data.User
import com.zybooks.individpro.data.UserManager
import com.zybooks.individpro.ui.theme.IndividProTheme

//Author: Jan Brix Batalla


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var firstName by rememberSaveable {mutableStateOf("")}
    var lastName by rememberSaveable {mutableStateOf("")}
    var email by rememberSaveable {mutableStateOf("")}
    var dateOfBirth by rememberSaveable {mutableStateOf("")}
    var password by rememberSaveable {mutableStateOf("")}
    var reTypePassword by rememberSaveable {mutableStateOf("")}
    var errorMessage by rememberSaveable {mutableStateOf("")}

    val scrollState = rememberScrollState()
    val configuration = LocalConfiguration.current // need this for orientation
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Image(
        painter = painterResource(id = R.drawable.signup_picture),
        contentDescription = "Login Background Image",
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { // blur effect
                renderEffect = BlurEffect(radiusX = 20f, radiusY = 20f)
            },
        contentScale = ContentScale.Crop,// covers entire screen
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.pokemon_logo),
            contentDescription = "Pokemon Logo",
            modifier = Modifier
                .fillMaxWidth(if (isLandscape) 0.5f else 1f)
                .padding(top = if (isLandscape) 50.dp else 8.dp)
        )

        OutlinedTextField(
            value = firstName,
            onValueChange = { input ->
                firstName = input.trim()},
            label = {Text("Firstname")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { input ->
                lastName = input.trim()},
            label = {Text("Lastname")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        OutlinedTextField(
            value = email,
            onValueChange = { input ->
                email = input.trim()},
            label = {Text("Email")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(// change background color
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        //can use import datepickerdialog
        OutlinedTextField(
            value = dateOfBirth,
            onValueChange = { input ->
                dateOfBirth = input.trim()},
            label = {Text("Date of Birth (MM/DD/YYYY)")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(// change background color
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        OutlinedTextField(
            value = password,
            onValueChange = { input ->
                password = input.trim()},
            label = {Text("Password")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(// change background color
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            //no need for signup?
            //only 1 pw?
        )

        OutlinedTextField(
            value = reTypePassword,
            onValueChange = { input ->
                reTypePassword = input.trim()},
            label = {Text("Re-type Password")},
            modifier = modifier
                .fillMaxWidth(if (isLandscape) 0.7f else 1f)
                .padding(horizontal = 16.dp, vertical = if (isLandscape) 16.dp else 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(// change background color
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
//            visualTransformation = PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Row (
            horizontalArrangement = Arrangement.spacedBy(24.dp)//syntax for adding gap
        ) {
            Button(
                onClick = {//i love kotlin
                    errorMessage = ""

                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || dateOfBirth.isBlank() || password.isBlank() || reTypePassword.isBlank()){
                        errorMessage = "All fields must be filled out to sign up."
                    } else if (firstName.length !in 3..30){
                        errorMessage = "Firstname should be at least 3 characters long."
                    } else if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                        errorMessage = "Enter a valid email address."
                    } else if (password.length < 6){
                        errorMessage = "Password should be at least 6 characters long." // for a proper password it should be >12 char
                    } else if (password != reTypePassword){
                        errorMessage = "Passwords do not match. Please retype your password."
                    } else{
                        val newUser = User(firstName, lastName, email, dateOfBirth, password, reTypePassword) // goes to user.kt and creates

                        if (UserManager.registerUser(newUser)){
                            navController.navigate("login")
                        } else{
                            errorMessage = "Email already registered."
                        }
                    }
                },
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pokemon_navyBlue),
                    contentColor = colorResource(R.color.white)
                )
            ) {
                Text("Sign Up")
            }

            Button(
                onClick = { navController.navigate("login")},
                modifier = Modifier
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.pokemon_navyBlue),
                    contentColor = Color.White
                )
            ) {
                Text(    // Icon is an alternative for this
                    "Back to Login"
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color(0xFF000000)) // ARGB
                    .padding(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    IndividProTheme {
        SignUpScreen(navController = navController)
    }
}