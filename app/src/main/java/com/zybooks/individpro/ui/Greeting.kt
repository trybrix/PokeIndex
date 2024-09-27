package com.zybooks.individpro.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*
Author: Jan Brix Batalla
 */

@Composable
//simple greeting with lazycolumn, will change when there are more to do
fun Greeting(name: String, modifier: Modifier = Modifier) {
//    val expanded = remember{ mutableStateOf(false)}
    var expanded by remember{ mutableStateOf(false)}  //.value is no longer needed and changed to var
    val extraPadding by animateDpAsState(//starts from 0 - expanded(value)
        targetValue = if(expanded) 48.dp else 0.dp,
        animationSpec = tween(//linear from 1 to next
            durationMillis = 500 //.5sec
        ), label = ""
    )
    Surface(
        color = Color.Cyan,//can use MaterialTheme.colorSchemed.nameHere(from Theme.kt - color.kt to change)
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ){
        Row (modifier = Modifier.padding(24.dp)){
            Column(//making flexible child
                modifier = modifier
                    .weight(1f)
                    .padding(bottom = extraPadding)){
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(onClick = { expanded = !expanded}) {
                Text(if(expanded)"Show less" else "Show more")
            }
        }
    }
}