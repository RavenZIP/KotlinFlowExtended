package com.ravenzip.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ravenzip.app.ui.theme.KotlinFlowExtendedTheme
import com.ravenzip.kotlinflowextended.functions.forkJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinFlowExtendedTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.padding(innerPadding).fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val scope = rememberCoroutineScope()
                        val listflows = listOf(flow1, flow2, flow3, flow4, flow5)

                        Button(
                            onClick = {
                                scope.launch {
                                    forkJoin(listflows).collect {
                                        Log.d("NEW FORKJOIN", "FINISHED WITH $it")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.9f),
                        ) {
                            Text("LIST FORK JOIN")
                        }

                        Button(
                            onClick = {
                                scope.launch {
                                    forkJoin(flow1, flow2String, flow3Class, flow4Float).collect {
                                        Log.d("NEW FORKJOIN", "FINISHED WITH $it")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.9f),
                        ) {
                            Text("VARARG FORK JOIN")
                        }
                    }
                }
            }
        }
    }
}

class Test(val p1: String = "")

val flow1 = flow {
    Log.d("FLOW 1", "STARTED")
    delay(5000)
    Log.d("FLOW 1", "FINISHED")
    emit(1)
}

val flow2 = flow {
    Log.d("FLOW 2", "STARTED")
    delay(3000)
    Log.d("FLOW 2", "FINISHED")
    emit(2)
}

val flow2String = flow {
    Log.d("FLOW 2", "STARTED")
    delay(3000)
    Log.d("FLOW 2", "FINISHED")
    emit("2")
}

val flow3 = flow {
    Log.d("FLOW 3", "STARTED")
    delay(10000)
    Log.d("FLOW 3", "FINISHED")
    emit(3)
}

val flow3Class = flow {
    Log.d("FLOW 3", "STARTED")
    delay(10000)
    Log.d("FLOW 3", "FINISHED")
    emit(Test())
}

val flow4 = flow {
    Log.d("FLOW 4", "STARTED")
    delay(2000)
    Log.d("FLOW 4", "FINISHED")
    emit(4)
}

val flow4Float = flow {
    Log.d("FLOW 4", "STARTED")
    delay(2000)
    Log.d("FLOW 4", "FINISHED")
    emit(4F)
}

val flow5 = flow {
    Log.d("FLOW 5", "STARTED")
    delay(8000)
    Log.d("FLOW 5", "FINISHED")
    emit(5)
}
