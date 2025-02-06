package com.ravenzip.kotlinflowextended.functions

import com.ravenzip.kotlinflowextended.models.FlowNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<T>.filterNull(): Flow<T?> =
    this.transform { value -> if (value == null) return@transform emit(value) }

fun <T> Flow<FlowNotification<T>>.filterNextNotification(): Flow<FlowNotification.Next<T>> =
    this.transform { notification ->
        if (notification is FlowNotification.Next) return@transform emit(notification)
    }

fun <T> Flow<FlowNotification<T>>.filterErrorNotification(): Flow<FlowNotification.Error> =
    this.transform { notification ->
        if (notification is FlowNotification.Error) return@transform emit(notification)
    }

fun <T> Flow<FlowNotification<T>>.filterCompleteNotification(): Flow<FlowNotification.Complete> =
    this.transform { notification ->
        if (notification is FlowNotification.Complete) return@transform emit(notification)
    }
