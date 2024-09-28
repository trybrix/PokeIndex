package com.zybooks.individpro.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zybooks.individpro.R
import com.zybooks.individpro.ui.theme.IndividProTheme

/*
Author: Jan Brix Batalla
 */


@Composable
//registration for user
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onSignUpComplete: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var reTypePassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()//scrolling in

    //credentials - db not connected yet
    val correctFN = "qwer"
    val correctLN = "qwer"
    val correctEmail = "qwer@qwer.com"
    val correctDOB = "1/1/1111"
    val correctPW = "qwer"

    /* TODO - Would be better if I make another file with userData and handle all users auth there,
        but just for now everything is hardcoded and when we get to db (firebase or any) thats when ill fix it
    */
    fun validateInput(//all are string for now
        firstName: String,
        lastName: String,
        email: String,
        dateOfBirth: String,
        password: String,
        reTypePassword: String
    ): String? {// From lecture safe call operator ? ?: !!
        //Check if any field is empty - check out TODO
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dateOfBirth.isEmpty() || password.isEmpty() || reTypePassword.isEmpty()) {
            return "All fields must be filled out."
        }

        // Checout TODO
        if (firstName.length < 3 || firstName.length > 30) {
            return "First name must be between 3 and 30 characters."
        }

        val emailPattern = android.util.Patterns.EMAIL_ADDRESS//email pattern
        if (!emailPattern.matcher(email).matches()) {//looks at email and compare - matches
            return "Invalid email format."
        }

        //there has to be another way to do this
        if (firstName != correctFN) {
            return "First name does not match."
        }
        if (lastName != correctLN) {
            return "Last name does not match."
        }
        if (email != correctEmail) {
            return "Email does not match."
        }
        if (dateOfBirth != correctDOB) {
            return "Date of birth does not match."
        }
        if (password != correctPW) {
            return "Password does not match."
        }

        //Check if paswords match
        if (password != reTypePassword) {
            return "Passwords do not match."
        }
        return null
    }


    /*
            TODO: WILL RESULT IN A DIFFERENT LAYOUT ASK PROF - WHY?
            //todo - answer: its a modifier and this will stack on and stack on, which does the first "dot . " and does the second one
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(14.dp),

            .padding(14.dp)
            .verticalScroll(scrollState)
            .fillMaxSize(),
     */


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(                            //from the lecture R. resource
            painter = painterResource(id = R.drawable.pokemon_logo),
            contentDescription = "FSC Ram Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 14.dp)
        )

        Text(
            text = "OASIS Login\nOnline Administrative Student Information System",
            textAlign = TextAlign.Center, // Center the text content
            modifier = Modifier.fillMaxWidth() // Make the text fill the width, allowing it to center
        )

        OutlinedTextField(//firstName
            value = firstName,
            onValueChange = {firstName = it},
            label = {Text("First Name")},
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(//lastName
            value = lastName,
            onValueChange = {lastName = it},
            label = {Text("Last Name")},
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(//email
            value = email,
            onValueChange = {email = it},
            label = {Text("Email")},
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        //can use import datepickerdialog
        OutlinedTextField(//dateOfBirth
            value = dateOfBirth,
            onValueChange = {dateOfBirth = it},
            label = {Text("Date of Birth (MM/DD/YYYY)")},
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(//password
            value = password,
            onValueChange = {password = it},
            label = {Text("Password")},
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(//reTypePassword
            value = reTypePassword,
            onValueChange = {reTypePassword = it},
            label = {Text("reTypePassword")},
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Row (
            horizontalArrangement = Arrangement.spacedBy(24.dp)//syntax for adding gap
        ) {
            Button(
                modifier = Modifier.padding(vertical = 14.dp),
                onClick = {
                    try {
                        val validationResult = validateInput(
                            firstName,
                            lastName,
                            email,
                            dateOfBirth,
                            password,
                            reTypePassword
                        )

                        if (validationResult != null) {
                            errorMessage = validationResult //show specific error message
                        } else {
                            errorMessage = ""
                            onSignUpComplete() // Call sign-up completion logic
                        }
                    }catch(e: Exception){
                        errorMessage = "error: ${e.message}"
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.purple_200),//get resource from colors.xml
                    contentColor = Color.White//simple colors
                )
            ) {
                Text("Sign Up")
            }
        }

        if (errorMessage.isNotEmpty()){
            Text(
                text = errorMessage,
                color = androidx.compose.ui.graphics.Color.Red,//get resource from colors.xml
                modifier = modifier.padding(8.dp)//simple colors
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    IndividProTheme {
        SignUpScreen(onSignUpComplete = {})
        //not passing anything - just want to show the OnboardingScreen so pass an empty call
    }
}
