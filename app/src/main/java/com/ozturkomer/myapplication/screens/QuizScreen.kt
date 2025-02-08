package com.ozturkomer.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(navController: NavHostController) {

    val imageQuestions = remember {

        mutableStateListOf(
            ImageData("img1", "Renk körü olanlar ve normal görenler 12 görürler.", "12"),
            ImageData(
                "img2", "Normal görenler 5 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç birşey göremez.", "5"
            ),
            ImageData(
                "img3", "Normal görenler 7 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç birşey göremez.", "7"
            ),
            ImageData(
                "img4", "Normal görenler 16 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç birşey göremez.", "16"
            ),
            ImageData(
                "img5", "Normal görenler 73 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç birşey göremez.", "73"
            ),
            ImageData(
                "img6", "Kırmızı ve Yeşil renk körü olanlar 5 olarak görür.\n" +
                        "Normal görenler hiçbirşey okuyamazlar.", "Boş"
            ),
            ImageData(
                "img7", "Kırmızı ve Yeşil renk körü olanlar 45 olarak görür.\n" +
                        "Normal görenler hiçbirşey okuyamazlar.", "Boş"
            ),
            ImageData(
                "img8", "Renk körü olanlar 2 veya 6 olarak görürler.\n" +
                        "Normal görenler 26 olarak okurlar.", "26"
            ),
            ImageData(
                "img9", "Renk körü olanlar 2 veya 4 olarak görürler.\n" +
                        "Normal görenler 42 olarak okurlar.", "42"
            ),
            ImageData(
                "img10", "Kırmızı - Yeşil renk körü olanlar 3 olarak okur.\n" +
                        "Normal görenler 8 olarak görür.", "8"
            ),
            ImageData(
                "img11", "Kırmızı - Yeşil renk körü olanlar 79 olarak okur.\n" +
                        "Normal görenler 29 olarak görür.", "29"
            ),
            ImageData(
                "img12", "Kırmızı - Yeşil renk körü olanlar 2 olarak okur.\n" +
                        "Normal görenler 5 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç bir şey göremez.", "5"
            ),
            ImageData(
                "img13", "Kırmızı - Yeşil renk körü olanlar 5 olarak okur.\n" +
                        "Normal görenler 3 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç bir şey göremez.", "3"
            ),
            ImageData(
                "img14", "Kırmızı - Yeşil renk körü olanlar 17 olarak okur.\n" +
                        "Normal görenler 15 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç bir şey göremez.", "15"
            ),
            ImageData(
                "img15", "Kırmızı - Yeşil renk körü olanlar 21 olarak okur.\n" +
                        "Normal görenler 74 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç bir şey göremez.", "74"
            ),
            ImageData(
                "img16", "Normal görenler 6 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç birşey göremez.", "6"
            ),
            ImageData(
                "img17", "Normal görenler 45 olarak görür.\n" +
                        "Tüm renklere karşı renk körü olanlar hiç birşey göremez.", "45"
            ),
        )
            .shuffled() //Tüm soruları karıştırır.
    }
    val userAnswers = remember {
        mutableStateListOf<String?>().apply {
            repeat(imageQuestions.size) { add(null) }
        }
    }

    val pagerState = rememberPagerState(0) { imageQuestions.size }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Quiz(
                imageData = imageQuestions[page],
                index = page,
                allQuestions = imageQuestions,
                userAnswer = userAnswers[page],
                onAnswerSelected = { answer ->
                    userAnswers[page] = answer
                    coroutineScope.launch {
                        delay(1500) // 1.5 saniye bekle
                        if (pagerState.currentPage < imageQuestions.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }
            )
        }

        if (pagerState.currentPage == imageQuestions.size - 1) {
            Button(
                onClick = {
                    val correctCount = userAnswers.filterIndexed { index, answer ->
                        answer == imageQuestions[index].correctAnswer
                    }.size
                    val wrongCount = userAnswers.filterIndexed { index, answer ->
                        answer != imageQuestions[index].correctAnswer
                    }.size

                    navController.navigate("result/$correctCount/$wrongCount/${imageQuestions.size}")
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text("Bitir")
            }
        }

    }
}

@Composable
fun DrawableId(name: String): Int {
    return LocalContext.current.resources.getIdentifier(
        name,
        "drawable",
        LocalContext.current.packageName
    )
}


data class ImageData(
    val name: String,
    val content: String,
    val correctAnswer: String
)


@Composable
fun Quiz(
    imageData: ImageData,
    index: Int,
    allQuestions: List<ImageData>,
    userAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    var active = remember {
        mutableStateOf(true)
    }
    var visibile = remember {
        mutableStateOf(false);
    }

    // Seçenekleri oluştur (1 doğru cevap + 3 yanlış cevap)
    val options = remember {
        val incorrectOptions = allQuestions
            .map { it.correctAnswer }
            .filterNot { it == imageData.correctAnswer }
            .shuffled()
            .take(3) // Rastgele 3 yanlış cevap al
        (incorrectOptions + imageData.correctAnswer).shuffled() // Doğru cevabı ekleyip karıştır
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Text("Resim -${index + 1}", fontSize = 24.sp, fontFamily = FontFamily.Cursive)

        // Resmi göster
        Image(
            painter = painterResource(id = DrawableId(imageData.name)),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(300.dp)
                .padding(16.dp)
        )

        // Seçenekleri listele
        Row(modifier = Modifier.padding(16.dp)) {
            options.forEach { option ->
                val backgroundColor = when {
                    userAnswer == null -> Color.Black
                    userAnswer == option && userAnswer == imageData.correctAnswer -> Color.Green
                    userAnswer == option -> Color.Red
                    option == imageData.correctAnswer -> Color.Green
                    else -> Color.Gray
                }
                val activeButton = when {
                    userAnswer == null -> true
                    userAnswer == option && userAnswer == imageData.correctAnswer -> true
                    userAnswer == option -> true
                    option == imageData.correctAnswer -> true
                    else -> false
                }

                Button(
                    enabled = activeButton,
                    onClick = {
                        onAnswerSelected(option); visibile.value = true; active.value = false
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text(option, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Açıklama metni
        if (visibile.value) {
            Text(
                imageData.content,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}
