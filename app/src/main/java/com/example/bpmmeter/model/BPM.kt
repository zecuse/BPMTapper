package com.example.bpmmeter.model

import java.util.ArrayDeque
import java.util.Queue

data class BPM(var start: Long = 0L,
               var elapsed: Long = 0L,
               val weights: List<Float> = listOf(0.001f,
                                                 0.001f,
                                                 0.005f,
                                                 0.01f,
                                                 0.025f,
                                                 0.05f,
                                                 0.1f,
                                                 0.5f,
                                                 1f,
                                                 1f),
               val times: Queue<Long> = ArrayDeque(),
               val maxTimes: Int = 10)