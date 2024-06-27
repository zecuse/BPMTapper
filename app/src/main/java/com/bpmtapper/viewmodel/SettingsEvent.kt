package com.example.bpmtapper.viewmodel

import com.example.bpmtapper.model.SettingsState
import com.example.bpmtapper.ui.theme.AppColor
import com.example.bpmtapper.ui.theme.AppTheme

/**
 * All of the events that can be performed by a user on the [SettingsViewModel].
 */
sealed interface SettingsEvent
{
	/**
	 * Sets the [SettingsState.theme] to the [theme] value.
	 */
	data class SetTheme(val theme: AppTheme): SettingsEvent

	/**
	 * Sets the [SettingsState.color] to the [color] value.
	 */
	data class SetColor(val color: AppColor): SettingsEvent

	/**
	 * Toggles the handedness in landscape mode.
	 */
	data class SetHandedness(val leftHanded: Boolean): SettingsEvent

	/**
	 * Toggles between monospace and the device's system default spacing.
	 */
	data class SetSpacing(val spacing: String): SettingsEvent
}