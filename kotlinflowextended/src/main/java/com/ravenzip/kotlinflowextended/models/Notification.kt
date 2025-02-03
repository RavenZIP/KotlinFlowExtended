package com.ravenzip.kotlinflowextended.models

sealed class Notification<out T> {
    data class Next<out T>(val value: T) : Notification<T>()

    data class Error(val error: Throwable) : Notification<Nothing>()

    data object Complete : Notification<Nothing>()
}
