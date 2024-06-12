package com.ravenzip.kotlinflowextended.functions

import com.ravenzip.kotlinflowextended.models.FlowOIndexedObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

/**
 * [forkJoin]
 *
 * Предназначен для параллельного выполнения запросов и выдачи результатов списком в том порядке, в
 * котором они были переданы
 *
 * @param flows список потоков
 * @param flowLimit количество одновременно работающих потоков
 * @param delay задержка перед переходом к следующей итерации
 * @return [Flow]
 */
fun <T> forkJoin(flows: List<Flow<T>>, flowLimit: Int = 3, delay: Long = 100): Flow<List<T>> =
    flow {
        coroutineScope {
            val flowsCount = flows.count()
            val results = mutableListOf<FlowOIndexedObject<T>>()
            var availableFlowCount = flowLimit
            var itemIndex = 0
            var isNotComplete = true

            while (isNotComplete) {
                if (itemIndex <= flowsCount - 1 && availableFlowCount <= flowLimit) {
                    val rightLimit = availableFlowCount + itemIndex - 1
                    for (i in itemIndex..rightLimit) {
                        launch(Dispatchers.Main) {
                            flows[i].collect { value ->
                                results.add(FlowOIndexedObject.create(value, i))
                                availableFlowCount += 1
                                if (
                                    results.count() == flowsCount &&
                                    availableFlowCount == flowsCount
                                ) {
                                    isNotComplete = false
                                }
                            }
                        }

                        availableFlowCount -= 1
                        itemIndex = i + 1
                    }
                }
                delay(delay)
            }

            emit(results.sortedBy { it.index }.map { it.value })
        }
    }