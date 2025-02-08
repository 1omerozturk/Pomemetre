package com.ozturkomer.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
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
import com.ozturkomer.myapplication.icons.Device_reset
import com.ozturkomer.myapplication.icons.History
import com.ozturkomer.myapplication.icons.Timer_pause
import com.ozturkomer.myapplication.icons.Timer_play
import kotlinx.coroutines.delay


@Composable
fun TimerScreen() {
    val context = LocalContext.current
    val timer = remember { mutableStateOf(0L) }
    val timerMillis = remember { mutableStateOf(0L) }
    val isRunning = remember { mutableStateOf(false) }
    val history = remember { mutableStateListOf<String>() }

    LaunchedEffect(isRunning.value) {
        if (isRunning.value) {
            val startTime = System.currentTimeMillis() - timerMillis.value // Başlangıç zamanı
            while (true) {
                val currentTime = System.currentTimeMillis() - startTime // Geçen zaman
                timerMillis.value = currentTime
                timer.value = currentTime / 1000 // saniyeye dönüştür
                delay(50L) // her 50 milisaniyede bir güncelleme yap
            }
        }
    }



    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 170.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        val minutes = (timerMillis.value / (1000 * 60)) % 60
        val seconds = (timerMillis.value / 1000) % 60
        val milliseconds = timerMillis.value % 1000
        Text(
            text = String.format("%02d.%02d.%02d", minutes, seconds, milliseconds / 10),
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 30.dp)
                .padding(bottom = 50.dp)
                .border(
                    2.dp, Color.Black, RoundedCornerShape(50.dp)
                )
                .width(200.dp)


        ) // saniye.salise formatı

        Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(
                colors = ButtonColors(Color.Black, Color.White, Color.Gray, Color.DarkGray),
                onClick = {
                    if (isRunning.value) {
                        PlaySound(context, R.raw.finish)
                    } else {
                        PlaySound(context, R.raw.start)
                    }
                    isRunning.value = !isRunning.value
                },

                ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        if (!isRunning.value) Timer_play else Timer_pause,
                        "",
                        tint = if (isRunning.value) Color.Blue else Color.Green,
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .size(45.dp)
                    )
                }

            }

            Button(
                colors = ButtonColors(Color.Black, Color.White, Color.Gray, Color.DarkGray),
                onClick = {
                    val formattedTime =
                        String.format("%02d:%02d:%02d", minutes, seconds, milliseconds / 10)
                    if (timerMillis.value != 0L && !history.contains(formattedTime)) {
                        history.add(formattedTime) // Toplam milisaniye değerini ekle
                        Toast.makeText(
                            context,
                            "$formattedTime Eklendi",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // Kullanıcıya bilgi mesajı göstermek isterseniz
                        Toast.makeText(
                            context,
                            "Süre 0 veya Zaten mevcut süre var!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    timerMillis.value = 0
                    timer.value = 0
                }) {
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Icon(
                        Device_reset,
                        contentDescription = "",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .size(45.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 5.dp),
            // Genel padding
            verticalArrangement = Arrangement.SpaceBetween, // Üst ve alt boşlukları dengele
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Spacer örneği
            Spacer(modifier = Modifier.height(30.dp))

            // İconButton Görünürlüğü ve Spacer
            if (history.isNotEmpty()) {

                IconButton(onClick = { /* TODO: İşlem */ }) {
                    Icon(History, contentDescription = "History", modifier = Modifier.size(50.dp))
                }
                Button(
                    modifier = Modifier,
                    colors = ButtonColors(Color.Black, Color.White, Color.White, Color.Gray),
                    onClick = {
                        history.clear();
                        Toast.makeText(
                            context,
                            "Süreler silindi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            "Tümünü Temizle",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )
                        Icon(
                            Icons.Default.Clear, contentDescription = "History", modifier = Modifier
                                .padding(start = 10.dp)
                                .size(30.dp)
                        )
                    }
                }


            }

            // LazyColumn sadece history boş değilse görünür
            if (history.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .background(Color.Black, shape = RoundedCornerShape(30.dp))
                        .fillMaxHeight() // Yüksekliği tabandan en üste kadar ayarla
                        .padding(top = 10.dp)
                ) {
                    itemsIndexed(history) { index, time ->
                        Row(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "${index + 1}. Süre:  $time",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .border(2.dp, Color.LightGray, RoundedCornerShape(15.dp))
                                    .padding(horizontal = 10.dp, vertical = 8.dp)
                                    .fillMaxWidth(0.75f)
                            )
                            IconButton(onClick =
                            {
                                history.removeAt(index)
                                Toast.makeText(
                                    context,
                                    "$time Silindi.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {
                                Icon(
                                    Icons.Default.Delete,
                                    tint = Color.Red,
                                    contentDescription = "Delete",
                                    modifier = Modifier
                                        .border(
                                            1.dp, Color.White,
                                            RoundedCornerShape(100)
                                        )
                                        .padding(5.dp)

                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
