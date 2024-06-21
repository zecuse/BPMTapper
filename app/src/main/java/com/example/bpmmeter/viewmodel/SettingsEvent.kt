package com.example.bpmmeter.viewmodel

import com.example.bpmmeter.ui.theme.MyColors
import com.example.bpmmeter.ui.theme.ThemeType

sealed interface SettingsEvent
{
	data class SetTheme(val theme: ThemeType): SettingsEvent
	data class SetColor(val color: MyColors): SettingsEvent
	data class SetSpacing(val spacing: String): SettingsEvent
}