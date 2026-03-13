package com.lagradost.api

import android.content.Context
import java.lang.ref.WeakReference

actual class WeakRef<T : Any> actual constructor(referent: T) {
    private val ref = WeakReference(referent)
    actual fun get(): T? = ref.get()
}

var ctx: WeakReference<Context>? = null

/**
 * Helper function for Android specific context. Not usable in JVM.
 * Do not use this unless absolutely necessary.
 */
actual fun getContext(): Any? {
    return ctx?.get()
}

actual fun setContext(context: WeakRef<Any>) {
    val actualContext = context.get() as? Context
    if (actualContext != null) {
        ctx = WeakReference(actualContext)
    }
}
