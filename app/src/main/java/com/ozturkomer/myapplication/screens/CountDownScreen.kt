package com.ozturkomer.myapplication.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozturkomer.myapplication.R
import com.ozturkomer.myapplication.components.PlaySound
import com.ozturkomer.myapplication.components.TimePickerDialog
import com.ozturkomer.myapplication.icons.DebugStart
import com.ozturkomer.myapplication.icons.Device_reset
import com.ozturkomer.myapplication.icons.Stop_circle
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountDownScreen() {
    val context = LocalContext.current
    val countdownMillis =
        remember { mutableStateOf(10_000L) } // Geri sayım için başlangıç süresi (10 saniye)
    val isRunning = remember { mutableStateOf(false) }
    val isCountdownFinished = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }

    LaunchedEffect(isRunning.value) {
        if (isRunning.value && countdownMillis.value > 0L) {
            while (countdownMillis.value > 0L) {
                delay(100L)
                countdownMillis.value -= 100L
            }
            isRunning.value = false
            isCountdownFinished.value = true
        }
        if (isCountdownFinished.value) {
            PlaySound(context, R.raw.finish)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        val minutes = (countdownMillis.value / (1000 * 60)) % 60
        val seconds = (countdownMillis.value / 1000) % 60
        val milliseconds = countdownMillis.value % 1000


        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(330.dp),
            colors = ButtonColors(Color.Transparent, Color.Black, Color.Transparent, Color.Red),
            onClick = { openDialog.value = true }, enabled = !isRunning.value,
        )
        {
            Text(
                text = String.format("%02d:%02d:%02d", minutes, seconds, milliseconds / 10),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 120.dp)
                    .padding(vertical = 16.dp)
                    .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                colors = ButtonColors(Color.Black, Color.White, Color.Gray, Color.Red),
                onClick = {
                    if (!isRunning.value) {
                        isCountdownFinished.value = false
                        countdownMillis.value = 10_000L // Geri sayımı sıfırla
                    }
                }
            ) {
                Icon(
                    Device_reset,
                    "",
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(45.dp)
                )
            }

            Button(
                colors = ButtonColors(Color.Black, Color.White, Color.Gray, Color.Red),
                onClick = {
                    if (isRunning.value) {
                        PlaySound(context, R.raw.finish)
                    } else {
                        PlaySound(context, R.raw.start)
                    }
                    isRunning.value = !isRunning.value
                },
                enabled = countdownMillis.value > 0L
            ) {
                Icon(
                    if (isRunning.value) Stop_circle else DebugStart,
                    "",
                    tint = if (isRunning.value) Color.Blue else Color.Green,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .size(45.dp)
                )
            }
        }
    }
    if (openDialog.value) {
        TimePickerDialog(
            initialTimeMillis = countdownMillis.value,
            onTimeSelected = { selectedMillis ->
                countdownMillis.value = selectedMillis
                openDialog.value = false
            },
            onDismiss = { openDialog.value = false }
        )
    }
}
