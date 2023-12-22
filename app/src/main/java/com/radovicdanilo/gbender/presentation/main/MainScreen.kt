package com.radovicdanilo.gbender.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.radovicdanilo.gbender.data.model.Level
import com.radovicdanilo.gbender.data.model.Tuning
import com.radovicdanilo.gbender.di.AppCore
import com.radovicdanilo.gbender.presentation.Screen

@Composable
fun MainScreen(navController: NavController) {
    Column {
        Row {
            TuningDropdownMenu()
        }
        Row {
            for (l in Level.values()) {
                levelCard(level = l)
            }
        }
        Row {
            Button(onClick = {
            navController.navigate(Screen.PracticeScreen.route)
            }) {
                Text(text = "Start")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TuningDropdownMenu() {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var tuning by remember {
        mutableStateOf(Tuning.E)
    }
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }
    ) {
        TextField(
            value = tuning.toString(),
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = "Tuning")
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = "E")
                },
                onClick = {
                    tuning = Tuning.E
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Eb")
                },
                onClick = {
                    tuning = Tuning.Eb
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "D")
                },
                onClick = {
                    tuning = Tuning.D
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "Db")
                },
                onClick = {
                    tuning = Tuning.Db
                    isExpanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Text(text = "C")
                },
                onClick = {
                    tuning = Tuning.C
                    isExpanded = false
                }
            )
        }
    }
}

@Composable
fun levelCard(level: Level) {
    var selected = false
    if (level == Level.WHOLE || level == Level.HALF)
        selected = true
    var currentColor = White
    if (selected)
        currentColor = Green
    Column {
        Button(
            onClick = {
                selected = !selected
                if (selected) {
                    currentColor = Green
                } else {
                    currentColor = White
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = currentColor,
                contentColor = Color.Black)
        ){
            Text(text = level.toString())
        }
    }
}