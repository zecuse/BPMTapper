package com.zecuse.bpmtapper.model

import com.zecuse.bpmtapper.viewmodel.MainViewModel
import java.util.ArrayDeque
import java.util.Queue

/**
 * The backing state of the [MainViewModel].
 *
 * @param startText The "start" stringResource that is displayed before any tapping has occurred.
 * @param keepText The "keep" stringResource that is displayed between the first and third taps.
 * @param display The text shown in the Text composable with the "reading" contentDescription semantic modifier.
 * @param pickerVisibility Whether or not the color picker is showing color options.
 * @param startTime The time since the last tap.
 * @param elapsed The time between the last 2 taps.
 * @param times A [Queue] of [elapsed] times used for the [display].
 * @param weights How much recently remembered [elapsed] times contribute to the current [display].
 * The size of this determines how many [times] are remembered.
 */
data class BPMState(val startText: String = "Start tapping",
                    val keepText: String = " Keep tapping",
                    val display: String = startText,
                    val pickerVisibility: Boolean = false,
                    val startTime: Long = 0L,
                    val elapsed: Long = 0L,
                    val times: Queue<Long> = ArrayDeque(),
                    val weights: List<Float> = listOf(0.001f,
                                                      0.001f,
                                                      0.005f,
                                                      0.01f,
                                                      0.025f,
                                                      0.05f,
                                                      0.1f,
                                                      0.5f,
                                                      1f,
                                                      1f))