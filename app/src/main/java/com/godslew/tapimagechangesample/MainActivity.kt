package com.godslew.tapimagechangesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.godslew.tapimagechangesample.ui.theme.TapImageChangeSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TapImageChangeSampleTheme {
                // A surface container using the 'background' color from the theme
                val imageUrls = remember {
                    listOf(
                        "https://source.unsplash.com/Xq1ntWruZQI/600x800",
                        "https://source.unsplash.com/NYyCqdBOKwc/600x800",
                        "https://source.unsplash.com/buF62ewDLcQ/600x800",
                        "https://source.unsplash.com/THozNzxEP3g/600x800",
                        "https://source.unsplash.com/LrMWHKqilUw/600x800",
                        "https://source.unsplash.com/PeFk7fzxTdk/600x800",
                        "https://source.unsplash.com/USrZRcRS2Lw/600x800",
                    )
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HorizontalTapSwitchablePagerScreen(
                        images = imageUrls,
                    )
                }
            }
        }
    }
}
