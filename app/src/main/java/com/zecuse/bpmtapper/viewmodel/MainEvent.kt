package com.zecuse.bpmtapper.viewmodel

import com.zecuse.bpmtapper.model.BPMState

/**
 * All of the events that can be performed by a user on the [MainViewModel].
 */
sealed interface MainEvent
{
	/**
	 * Sets the [BPMState.startText] and [BPMState.keepText] to their stringResource values.
	 *
	 * This should only run once per recomposition.
	 */
	data class Init(val startText: String, val keepText: String): MainEvent

	/**
	 * Sets the [BPMState.display] text based on the number of taps that have occurred.
	 */
	data object Display: MainEvent

	/**
	 * Updates [BPMState.startTime] and subsequent taps also update [BPMState.elapsed] and [BPMState.times].
	 */
	data object Tap: MainEvent

	/**
	 * Resets [BPMState.startTime] to 0, [BPMState.display] to [BPMState.startText], and clears [BPMState.times].
	 */
	data object Reset: MainEvent

	/**
	 * Toggles the visibility of the color picker.
	 */
	data object TogglePicker: MainEvent
}