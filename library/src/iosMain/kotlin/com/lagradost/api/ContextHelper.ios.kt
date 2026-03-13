package com.lagradost.api

actual class WeakRef<T : Any> actual constructor(referent: T) {
    private var ref: T? = referent
    actual fun get(): T? = ref
}

actual fun getContext(): Any? {
    return null
}

actual fun setContext(context: WeakRef<Any>) {
}
