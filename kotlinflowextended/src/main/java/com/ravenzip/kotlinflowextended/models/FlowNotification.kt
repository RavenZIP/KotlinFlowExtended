package com.ravenzip.kotlinflowextended.models

sealed class FlowNotification<out T> {
    data class Next<out T>(val value: T) : FlowNotification<T>()

    data class Error(val error: Throwable) : FlowNotification<Nothing>()

    data object Complete : FlowNotification<Nothing>()
}
