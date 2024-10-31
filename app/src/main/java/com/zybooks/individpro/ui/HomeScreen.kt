package com.zybooks.individpro.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.zybooks.individpro.R

/*
Author: Jan Brix Batalla
 */

@Composable
fun HomeScreen(
    navController: NavController,
    names: List<String> = List(20) { "$it" },
    currentUserEmail: String
) {


    val configuration = LocalConfiguration.current // need this for orientation
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val pressStart2PFont = FontFamily(
        Font(R.font.pressstart2p_regular) // Refers to the font file in res/font
    )

    Image(
        painter = painterResource(id = R.drawable.pokemon_mew_verticalbg),
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
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to PokeIndex",
            modifier = Modifier
                .padding(top = 100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(R.color.pokemon_navyBlue))
                .border(1.dp, Color.Black).padding(20.dp),
            style = androidx.compose.ui.text.TextStyle(
                color = colorResource(R.color.pokemon_yellow),
                fontSize = 16.sp,
                fontFamily = pressStart2PFont

            )
        )

        Spacer(modifier = Modifier.padding(25.dp))

        Button(
            onClick = { navController.navigate("pokequiz/{currentUserEmail}") },
            modifier = Modifier
//                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(text = "Take the PokeQuiz",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 12.sp,
                    fontFamily = pressStart2PFont)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    val currentUserEmail = "email@exam.com"
    HomeScreen(navController = navController, currentUserEmail = currentUserEmail)
}