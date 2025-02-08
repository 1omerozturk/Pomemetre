package com.ozturkomer.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ozturkomer.myapplication.R

@Composable
fun BmiScreen() {
    val context = LocalContext.current
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmi by remember { mutableStateOf("") }
    var bmiValue by remember { mutableStateOf(0f) }
    val imageModifier = Modifier
        .fillMaxWidth()
        .height(200.dp)
        .border(BorderStroke(1.dp, Color.Black))
        .background(Color.White)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Vücut Kitle Endeksi Hesaplama",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        Image(
            painterResource(R.drawable.bmi),
            contentDescription = "",
            contentScale = ContentScale.FillBounds, modifier = imageModifier
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Boy (cm)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Kilo (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            onClick = {
                val h = height.toFloatOrNull()?.div(100)
                val w = weight.toFloatOrNull()
                if (h != null && w != null) {
                    bmiValue = w / (h * h)
                    bmi = String.format("%.2f", bmiValue) // BMI'yi string olarak sakla
                } else {
                    Toast.makeText(
                        context,
                        "Lütfen Boy ve Kilo değerlerinizi giriniz.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Hesapla")
        }


        if (bmi != "") {
            Text(
                text =

                when {
                    bmiValue < 18.5 -> "$bmi - Aşırı Zayıf"
                    bmiValue < 24.9 -> "$bmi - Normal"
                    bmiValue < 29.9 -> "$bmi - Fazla Kilolu"
                    bmiValue < 34.9 -> "$bmi - Şişman (1. Derece)"
                    bmiValue < 39.9 -> "$bmi - Şişman (2. Derece)"
                    else -> "$bmi - Aşırı Şişman (3. Derece)"
                },

                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.DarkGray,
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .border(
                        2.dp, Color.Blue,
                        RoundedCornerShape(50.dp)
                    )
                    .padding(5.dp)
            )
        }

    }
}
