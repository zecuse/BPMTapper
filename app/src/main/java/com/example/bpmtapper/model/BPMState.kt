package com.example.bpmtapper.model

import java.util.ArrayDeque
import java.util.Queue

data class BPMState(val startText: String = "Start tapping",
                    val keepText: String = " Keep tapping",
                    val display: String = startText,
                    val pickerVisibility: Boolean = false,
                    val startTime: Long = 0L,
                    val elapsed: Long = 0L,
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