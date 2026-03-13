package com.lagradost.cloudstream3.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

actual fun runOnMainThreadNative(work: (() -> Unit)) {
    MainScope().launch(Dispatchers.Main) {
        work()
    }
}
