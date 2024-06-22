package com.example.bpmtapper.viewmodel

import com.example.bpmtapper.ui.theme.MyColors
import com.example.bpmtapper.ui.theme.ThemeType

sealed interface SettingsEvent
{
	data class SetTheme(val theme: ThemeType): SettingsEvent
	data class SetColor(val color: MyColors): SettingsEvent
	data class SetHandedness(val leftHanded: Boolean): SettingsEvent
	data class SetSpacing(val spacing: String): SettingsEvent
}