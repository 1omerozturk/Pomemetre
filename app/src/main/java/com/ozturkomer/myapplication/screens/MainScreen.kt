package com.ozturkomer.myapplication.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ozturkomer.myapplication.navigation.BottomNavItem
import com.ozturkomer.myapplication.navigation.BottomNavigation
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController:NavHostController) {
    val pagerState = rememberPagerState(0) { 4 }
    val coroutineScope = rememberCoroutineScope()

    val pages = listOf(
        BottomNavItem.Home.route,
        BottomNavItem.CountDown.route,
        BottomNavItem.VkeCalculate.route,
        BottomNavItem.ColorTest.route,

        )

    var currentRoute = remember { mutableStateOf(BottomNavItem.Home.route) }

    // 📌 Sayfa değiştikçe BottomNavigation güncelleniyor
    LaunchedEffect(pagerState.currentPage) {
        currentRoute.value = pages[pagerState.currentPage]
    }

    Scaffold(
        bottomBar = {
            BottomNavigation(
                currentRoute = currentRoute.value,
                onItemSelected = { route ->
                    currentRoute.value = route

                    val pageIndex = pages.indexOf(route)
                    if (pageIndex != -1) {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pageIndex)
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            HorizontalPager(
                state = pagerState,
                pageSize = PageSize.Fill, // 📌 İç içe geçme sorunu olmaması için
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> TimerScreen()
                    1 -> CountDownScreen()
                    2 -> BmiScreen()
                    3 -> QuizScreen(navController)
                }
            }
        }
    }
}