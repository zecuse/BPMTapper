package com.example.bpmmeter.viewmodel

import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bpmmeter.database.SettingsDao
import com.example.bpmmeter.database.SettingsDatabase
import com.example.bpmmeter.model.SettingsData
import com.example.bpmmeter.model.SettingsState
import com.example.bpmmeter.ui.theme.AppFonts
import com.example.bpmmeter.ui.theme.changeFont
import com.example.bpmmeter.ui.theme.defaultType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val dao: SettingsDao): ViewModel()
{
	private val _state = MutableStateFlow(SettingsState())
	val state = _state.stateIn(viewModelScope,
	                           SharingStarted.WhileSubscribed(),
	                           SettingsState())

	init
	{
		viewModelScope.launch {
			val settings = dao.getSettings()
				               .first() ?: SettingsData()
			_state.update {
				val spacing = settings.spacing
				val typography = when (spacing)
				{
					"mono" -> changeFont(base = state.value.typography,
					                     fontFam = AppFonts.sourceCodePro)
					else   -> defaultType
				}
				it.copy(theme = settings.theme,
				        color = settings.color,
				        spacing = spacing,
				        typography = typography)
			}
		}
	}

	fun onEvent(event: SettingsEvent)
	{
		when (event)
		{
			is SettingsEvent.SetColor   ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(color = event.color)
					dao.updateSetting(settings)
					_state.update {it.copy(color = event.color)}
				}
			}
			is SettingsEvent.SetSpacing ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(spacing = event.spacing)
					dao.updateSetting(settings)
					_state.update {
						val spacing = event.spacing
						val font =
							if (spacing == "mono") AppFonts.sourceCodePro else FontFamily.Default
						val typography = changeFont(base = state.value.typography,
						                            fontFam = font)
						it.copy(spacing = spacing,
						        typography = typography)
					}
				}
			}
			is SettingsEvent.SetTheme   ->
			{
				viewModelScope.launch {
					var settings = dao.getSettings()
						               .first() ?: SettingsData()
					settings = settings.copy(theme = event.theme)
					dao.updateSetting(settings)
					_state.update {it.copy(theme = event.theme)}
				}
			}
		}
	}
}

class SettingsFactory(
	private val db: SettingsDatabase,
): ViewModelProvider.Factory
{
	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T
	{
		return SettingsViewModel(db.dao) as T
	}
}