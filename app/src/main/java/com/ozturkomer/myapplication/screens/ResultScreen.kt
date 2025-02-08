package com.ozturkomer.myapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ResultScreen(
    navController: NavController,
    correctCount: Int,
    wrongCount: Int,
    totalQuestions: Int
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Button(modifier = Modifier.align(Alignment.BottomCenter),
            colors = ButtonColors(Color.Black, Color.White, Color.Gray, Color.Gray),
            onClick = { navController.navigate("main") }) {
            Text(text = "Ana Sayfa")
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sonuçlarınız:",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Doğru Sayısı: $correctCount",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Yanlış Sayısı: $wrongCount",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Toplam Soru Sayısı: $totalQuestions",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text =
            if (wrongCount > 3) {
                "Renk Körü olabilirsiniz \nEn kısa zamanda doktora görünmelisiniz."
            } else if (wrongCount > 1 && wrongCount < 4) {
                "Birden fazla yanlış cevap verdiniz. Lütfen tekrar testi deneyiniz."
            } else {
                "Göz sağlığınız iyi ve Renk Körü Değilsiniz."
            },
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

    }
}