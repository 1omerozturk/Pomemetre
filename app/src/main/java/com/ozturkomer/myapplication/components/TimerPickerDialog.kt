package com.ozturkomer.myapplication.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TimePickerDialog(
    initialTimeMillis: Long,
    onTimeSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val minutes = remember { mutableStateOf((initialTimeMillis / (1000 * 60)) % 60) }
    val seconds = remember { mutableStateOf((initialTimeMillis / 1000) % 60) }
    val milliseconds = remember { mutableStateOf((initialTimeMillis % 1000) / 10) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Süre Seç") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally, true),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NumberPicker(
                        label = "Dk",
                        value = minutes.value,
                        range = 0..59,
                        onValueChange = { minutes.value = it.toLong() }
                    )
                    NumberPicker(
                        label = "Sn",
                        value = seconds.value,
                        range = 0..59,
                        onValueChange = { seconds.value = it.toLong() }
                    )
                    NumberPicker(
                        label = "Ms",
                        value = milliseconds.value,
                        range = 0..99,
                        onValueChange = { milliseconds.value = it.toLong() }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val totalMillis = (minutes.value * 60 * 1000L) +
                        (seconds.value * 1000L) + (milliseconds.value * 10)
                onTimeSelected(totalMillis)
            }) {
                Text("Tamam")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("İptal")
            }
        }
    )
}

