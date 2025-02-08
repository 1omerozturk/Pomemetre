package com.ozturkomer.myapplication.components

import android.content.Context
import android.media.MediaPlayer


fun PlaySound(context: Context, soundResId: Int) {
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer.setOnCompletionListener { it.release() } // ses tamamlandığında kaynak serbest bırakılır.
    mediaPlayer.start()
}
