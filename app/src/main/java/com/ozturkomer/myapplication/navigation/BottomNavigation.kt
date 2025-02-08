package com.ozturkomer.myapplication.navigation

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ozturkomer.myapplication.icons.Calculate
import com.ozturkomer.myapplication.icons.Table_eye
import com.ozturkomer.myapplication.icons.Timer
import com.ozturkomer.myapplication.icons.Timer_play


sealed class BottomNavItem(val label:String,val route: String, val icon: ImageVector) {
    object Home : BottomNavItem(label="","home", Timer_play)
    object CountDown : BottomNavItem(label="","countdown", Timer)
    object VkeCalculate : BottomNavItem(label="","bmitest", Calculate)
    object ColorTest : BottomNavItem(label="","colortest", Table_eye)
}


@Composable
fun BottomNavigation(
    currentRoute: String, onItemSelected: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.CountDown,
        BottomNavItem.VkeCalculate,
        BottomNavItem.ColorTest
    )
    NavigationBar(
        containerColor = Color(0xFF1E1E1E), // Genel arka plan
//        containerColor = Color(0xFFDAB3B3), // Genel arka plan
        contentColor = Color.White,
        tonalElevation = 15.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            // Animasyonlu ikon büyüklüğü
            val iconSize by animateDpAsState(
                targetValue = if (isSelected) 40.dp else 32.dp,
                animationSpec = tween(durationMillis = 300)
            )

            NavigationBarItem(
                icon = {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight(unbounded = false)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF212121), Color(0xFF424242))
                                ),
                                shape = RoundedCornerShape(120.dp),
                                alpha = 1.0f // 💡 Alpha ekleyerek hatayı çözüyoruz
                            )// Görselliği iyileştirme
                            .padding(12.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            "",
                            modifier = Modifier.size(iconSize),
                            tint = if (isSelected)  Color(0xFFFFFFFF) else Color.Gray // Seçili öğeye altın rengi
                        )
                    }
                },

                selected = isSelected,
                onClick = { onItemSelected(item.route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFFFFF), // Seçili ikon rengi
                    indicatorColor = Color.Transparent // Seçili öğeye koyu gri ton
                ),
                modifier = Modifier
                    .padding(4.dp)
                    .size(56.dp)
            )
        }
    }
}
