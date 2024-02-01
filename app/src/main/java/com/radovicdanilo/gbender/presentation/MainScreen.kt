package com.radovicdanilo.gbender.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.radovicdanilo.gbender.data.model.Level
import com.radovicdanilo.gbender.data.model.Tuning
import com.radovicdanilo.gbender.di.AppCore
import com.radovicdanilo.gbender.domain.PracticeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(navController: NavController) {

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row {
            Button(
                onClick = {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) -> {
                            Log.d("ExampleScreen", "Code requires permission")
                        }

                        else -> {
                            launcher.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    }
                }
            ) {
                Text(text = "Check and Request Permission")
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        Row(horizontalArrangement = Arrangement.Center) {
            TuningDropdownMenu()
        }
        Spacer(modifier = Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (l in Level.values()) {
                levelCard(level = l)
                Spacer(modifier = Modifier.width(2.dp))
            }
        }
        Row(horizontalArrangement = Arrangement.Center) {

        }
        Row {
            Button(onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                    val practiceViewModel = PracticeViewModel(tuning = AppCore.instance.tuning)
                    practiceViewModel.start()
                    AppCore.instance.practiceViewModel = practiceViewModel
                    navController.navigate(Screen.PracticeScreen.route)
                }
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
    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { newValue ->
        isExpanded = newValue
    }) {
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
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {
            isExpanded = false
        }) {
            DropdownMenuItem(text = {
                Text(text = "E")
            }, onClick = {
                tuning = Tuning.E
                isExpanded = false
            })
            DropdownMenuItem(text = {
                Text(text = "Eb")
            }, onClick = {
                tuning = Tuning.Eb
                isExpanded = false
            })
            DropdownMenuItem(text = {
                Text(text = "D")
            }, onClick = {
                tuning = Tuning.D
                isExpanded = false
            })
            DropdownMenuItem(text = {
                Text(text = "Db")
            }, onClick = {
                tuning = Tuning.Db
                isExpanded = false
            })
            DropdownMenuItem(text = {
                Text(text = "C")
            }, onClick = {
                tuning = Tuning.C
                isExpanded = false
            })
        }
    }
}

@Composable
fun levelCard(level: Level) {
    var backgroundColor = White
    if (level.selected.collectAsState().value) {
        backgroundColor = Green
    }
    Column(horizontalAlignment = Alignment.Start) {
        Button(
            onClick = {
                level.selected.value = !level.selected.value
            }, colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor, contentColor = Color.Black
            )
        ) {
            Text(text = level.toString())
        }
    }
}
