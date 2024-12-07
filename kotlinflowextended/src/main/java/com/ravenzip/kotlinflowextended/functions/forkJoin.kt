package com.ravenzip.kotlinflowextended.functions

import com.ravenzip.kotlinflowextended.models.FlowOIndexedObject
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Semaphore

/**
 * [forkJoin]
 *
 * Предназначен для параллельного выполнения запросов и выдачи результатов списком в том порядке, в
 * котором они были переданы
 *
 * @param flows список потоков
 * @param flowLimit количество одновременно работающих потоков
 * @return [Flow]
 */
fun <T> forkJoin(flows: List<Flow<T>>, flowLimit: Int = 3): Flow<List<T>> = flow {
    coroutineScope {
        // Семафор для ограничения количества одновременно работающих потоков
        val semaphore = Semaphore(flowLimit)
        val deferredResults = mutableListOf<Deferred<FlowOIndexedObject<T>>>()

        flows.forEachIndexed { index, flow ->
            semaphore.acquire() // Ограничиваем количество потоков
            val deferred = async {
                try {
                    val result = flow.first() // Берем только первый элемент (как в RxJS forkJoin)
                    FlowOIndexedObject.create(result, index)
                } finally {
                    semaphore.release() // Освобождаем место в семафоре
                }
            }
            deferredResults.add(deferred)
        }

        // Ждем завершения всех задач и сортируем результаты
        val results = deferredResults.awaitAll().sortedBy { it.index }.map { it.value }
        emit(results)
    }
}

/**
 * [forkJoin]
 *
 * Предназначен для параллельного выполнения запросов и выдачи результатов списком в том порядке, в
 * котором они были переданы
 *
 * @param flows список потоков
 * @param flowLimit количество одновременно работающих потоков
 * @return [Flow]
 */
fun <T> forkJoin(vararg flows: Flow<T>, flowLimit: Int = 3): Flow<List<T>> = flow {
    coroutineScope {
        val semaphore = Semaphore(flowLimit)
        val deferredResults = mutableListOf<Deferred<FlowOIndexedObject<T>>>()

        flows.forEachIndexed { index, flow ->
            semaphore.acquire()
            val deferred = async {
                try {
                    val result = flow.first()
                    FlowOIndexedObject(result, index)
                } finally {
                    semaphore.release()
                }
            }
            deferredResults.add(deferred)
        }

        val results = deferredResults.awaitAll().sortedBy { it.index }.map { it.value }
        emit(results)
    }
}
