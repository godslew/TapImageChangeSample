package com.godslew.tapimagechangesample

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
internal fun HorizontalTapSwitchablePagerScreen(
    images: List<String>,
    modifier: Modifier = Modifier,
) {
    HorizontalTapSwitchablePager(
        items = images,
        modifier = modifier.fillMaxSize(),
        onChange = {
            Log.d("HorizontalTapSwitchablePager", "Index: $it")
        },
    ) {
        Image(
            url = it,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Composable
private fun Image(
    url: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = url,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}