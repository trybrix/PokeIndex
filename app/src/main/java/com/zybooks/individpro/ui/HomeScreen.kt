package com.zybooks.individpro.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
Author: Jan Brix Batalla
 */


@Composable
fun HomeScreen(names: List<String> = List(20) { "$it" }) {
    Surface(color = Color.White){
        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            LazyColumn (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                item {Text("LazyColumn\nHello, Android", modifier = Modifier.padding(8.dp))}
                items(names){ name ->
                    Greeting(name)
                }
            }
        }
    }
}