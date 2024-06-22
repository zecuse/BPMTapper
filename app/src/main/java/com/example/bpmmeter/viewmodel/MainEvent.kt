package com.example.bpmmeter.viewmodel

sealed interface MainEvent
{
	data class Init(val startText: String, val keepText: String): MainEvent
	data object Display: MainEvent
	data object Tap: MainEvent
	data object Reset: MainEvent
	data object TogglePicker: MainEvent
}