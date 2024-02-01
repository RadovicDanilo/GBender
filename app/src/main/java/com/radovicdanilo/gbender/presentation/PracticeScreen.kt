package com.radovicdanilo.gbender.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.radovicdanilo.gbender.di.AppCore
import com.radovicdanilo.gbender.domain.PracticeViewModel


@Composable
fun PracticeScreen() {
    val practiceViewModel = viewModel<PracticeViewModel>()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
        )
        {
            Text(text = practiceViewModel.currentPitch.collectAsState().value.toString() + " Hz")
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Bend the ${practiceViewModel.currentNote.collectAsState().value} by ${practiceViewModel.currentLevel.collectAsState().value}")
        }
        Spacer(modifier = Modifier.height(25.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(text = "Frequency = " + practiceViewModel.getDesiredNoteFrequency().toString())
        }
        Spacer(modifier = Modifier.height(25.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Circle(practiceViewModel.circleColorOn.collectAsState().value[0], 0)
            Spacer(modifier = Modifier.width(4.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[1], 1)
            Spacer(modifier = Modifier.width(4.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[2], 2)
            Spacer(modifier = Modifier.width(4.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[3], 1)
            Spacer(modifier = Modifier.width(4.dp))
            Circle(practiceViewModel.circleColorOn.collectAsState().value[4], 0)
        }
        Spacer(modifier = Modifier.height(5.dp))

        Row(horizontalArrangement = Arrangement.Start) {
            Box(
                Modifier
                    .fillMaxWidth(practiceViewModel.progress.collectAsState().value.toFloat() / AppCore.instance.timeMilis.toFloat())
                    .height(30.dp)
                    .background(Color.Red)
                ) {

            }
        }
        Spacer(modifier = Modifier.height(80.dp))

        Row(horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom) {
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
            Text(text = "Skip")
        }

    }
}
