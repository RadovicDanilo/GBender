package com.radovicdanilo.gbender.presentation.practice

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.radovicdanilo.gbender.domain.PracticeViewModel


@Composable
fun PracticeScreen(practiceViewModel: PracticeViewModel) {

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

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Button(
                onClick = {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        ) -> {
                            Log.d("ExampleScreen", "Code requires permission")
                            practiceViewModel.start()

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
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        )
            return
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = practiceViewModel.currentPitch.collectAsState().value.toString() + " Hz")
        }

        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Bend the ${practiceViewModel.currentNote.collectAsState().value} by ${practiceViewModel.currentLevel.collectAsState().value}")
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Text(text = "Frequency = " + practiceViewModel.getDesiredNoteFrequency().toString())
        }

        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Circle(practiceViewModel.circleColorOn.collectAsState().value[0], 0)
            Spacer(modifier = Modifier.width(2.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[1], 1)
            Spacer(modifier = Modifier.width(2.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[2], 2)
            Spacer(modifier = Modifier.width(2.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[3], 1)
            Spacer(modifier = Modifier.width(2.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[4], 0)
        }
        Row(horizontalArrangement = Arrangement.Start) {
            Box(
                Modifier
                    .fillMaxWidth(practiceViewModel.progress.collectAsState().value.toFloat() / 2000.toFloat())
                    .height(30.dp)
                    .background(Color.Red),

                ) {

            }
        }
        Row(horizontalArrangement = Arrangement.End) {
            Skip(practiceViewModel)
        }
    }
}

@Composable
fun Circle(active: Boolean, type: Int) {
    var color = Color.Green
    var size = 70.dp
    if (type == 0) {
        color = Color.Red
        size = 50.dp
    } else if (type == 1) {
        color = Color.Yellow
        size = 60.dp
    }
    if (!active) {
        color = Color.Gray
    }
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )

}

@Composable
fun Skip(practiceViewModel: PracticeViewModel) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.End
    ) {
        Button(onClick = {
            practiceViewModel.next()
        })
        {
        }

    }
}
