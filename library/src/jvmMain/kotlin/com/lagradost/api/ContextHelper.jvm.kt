package com.lagradost.api

import java.lang.ref.WeakReference

actual class WeakRef<T : Any> actual constructor(referent: T) {
    private val ref = WeakReference(referent)
    actual fun get(): T? = ref.get()
}

actual fun getContext(): Any? {
    return null
}

actual fun setContext(context: WeakRef<Any>) {
}
