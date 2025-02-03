package com.ravenzip.kotlinflowextended.functions

import com.ravenzip.kotlinflowextended.models.Notification
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.transform

fun <T> Flow<T>.materialize(): Flow<Notification<T>> =
    this.map<T, Notification<T>> { value -> Notification.Next(value) }
        .catch { error -> emit(Notification.Error(error)) }
        .onCompletion { cause ->
            if (cause == null) {
                emit(Notification.Complete)
            }
        }

fun <T> Flow<Notification<T>>.dematerialize(): Flow<T> =
    this.transform { notification ->
        when (notification) {
            is Notification.Next -> notification.value
            is Notification.Error -> throw notification.error
            is Notification.Complete -> return@transform
        }
    }
