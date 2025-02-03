package com.ravenzip.kotlinflowextended.models

class FlowIndexedObject<T>(val value: T, val index: Int) {
    companion object {
        fun <T> create(value: T, index: Int): FlowIndexedObject<T> {
            return FlowIndexedObject(value, index)
        }
    }
}
