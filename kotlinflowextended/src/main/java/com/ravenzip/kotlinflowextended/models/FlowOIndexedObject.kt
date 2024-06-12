package com.ravenzip.kotlinflowextended.models

class FlowOIndexedObject<T>(val value: T, val index: Int) {
    companion object {
        fun <T> create(value: T, index: Int): FlowOIndexedObject<T> {
            return FlowOIndexedObject(value, index)
        }
    }
}
