package com.godslew.tapimagechangesample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.overscroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun <T> HorizontalTapSwitchablePager(
    items: List<T>,
    modifier: Modifier = Modifier,
    controller: HorizontalTapSwitchablePagerController = rememberHorizontalTapSwitchablePagerController(),
    onChange: (index: Int) -> Unit = {},
    key: ((page: Int) -> Any)? = null,
    content: @Composable (T) -> Unit,
) {
    HorizontalPager(
        count = items.size,
        state = controller.pagerState,
        userScrollEnabled = false,
        key = key,
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { offset ->
                        controller.onTap(offset)
                    }
                )
            }
            .overscroll(controller.overScrollController),
    ) {
        content(items[it])
    }

    LaunchedEffect(controller.currentPage) {
        onChange(controller.currentPage)
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun rememberHorizontalTapSwitchablePagerController(
    velocity: Float = 1000F,
    selectIndex: Int = 0,
    scrollController: OverscrollEffect = ScrollableDefaults.overscrollEffect(),
): HorizontalTapSwitchablePagerController {
    val state = rememberPagerState(selectIndex)
    val scope = rememberCoroutineScope()
    val screenWidth =
        with(LocalDensity.current) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    return remember {
        HorizontalTapSwitchablePagerController(
            state = state,
            scope = scope,
            screenWidth = screenWidth,
            velocity = velocity,
            scrollController = scrollController,
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
class HorizontalTapSwitchablePagerController @OptIn(ExperimentalFoundationApi::class) constructor(
    private val state: PagerState,
    private val scope: CoroutineScope,
    private val screenWidth: Float,
    private val velocity: Float = 1000F,
    private val scrollController: OverscrollEffect,
) {
    val pagerState
        get() = state

    @OptIn(ExperimentalFoundationApi::class)
    val overScrollController
        get() = scrollController

    val currentPage
        get() = pagerState.currentPage

    fun onTap(offset: Offset) {
        if (offset.x < screenWidth / 2) {
            previousPage()
        } else {
            nextPage()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun previousPage() {
        scope.launch {
            state.previousPage(scrollController, velocity)
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    fun nextPage() {
        scope.launch {
            state.nextPage(scrollController, velocity)
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
private suspend fun PagerState.previousPage(
    scrollController: OverscrollEffect,
    velocity: Float,
) {
    val currentIndex = this.currentPage
    if (currentIndex == 0) {
        scrollController.consumePostFling(Velocity(x = velocity, y = 0F))
    } else {
        this.animateScrollToPage(currentIndex - 1)
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
private suspend fun PagerState.nextPage(
    scrollController: OverscrollEffect,
    velocity: Float,
) {
    val currentIndex = this.currentPage
    if (currentIndex == this.pageCount - 1) {
        scrollController.consumePostFling(Velocity(x = -velocity, y = 0F))
    } else {
        this.animateScrollToPage(currentIndex + 1)
    }
}
