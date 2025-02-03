package com.ravenzip.kotlinflowextended.functions

import com.ravenzip.kotlinflowextended.models.FlowNotification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform

fun <T> Flow<T>.materialize(): Flow<FlowNotification<T>> =
    this.map<T, FlowNotification<T>> { value -> FlowNotification.Next(value) }
        .catch { error -> emit(FlowNotification.Error(error)) }
        .onCompletion { cause ->
            if (cause == null) {
                emit(FlowNotification.Complete)
            }
        }

fun <T> Flow<FlowNotification<T>>.dematerialize(): Flow<T> =
    this.transform { notification ->
        when (notification) {
            is FlowNotification.Next -> notification.value
            is FlowNotification.Error -> throw notification.error
            is FlowNotification.Complete -> return@transform
        }
    }
