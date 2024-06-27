package com.example.bpmtapper.ui.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bpmtapper.ui.theme.BPMTapperTheme
import com.example.bpmtapper.ui.theme.AppColor
import com.example.bpmtapper.ui.theme.AppTheme
import com.example.bpmtapper.viewmodel.FakeDao
import com.example.bpmtapper.viewmodel.MainEvent
import com.example.bpmtapper.viewmodel.MainViewModel
import com.example.bpmtapper.viewmodel.SettingsEvent
import com.example.bpmtapper.viewmodel.SettingsViewModel

@Composable
fun SettingsBar(settings: SettingsViewModel,
                main: MainViewModel,
                modifier: Modifier = Modifier)
{
	val toggleVisibility = {main.onEvent(MainEvent.TogglePicker)}
	Column(horizontalAlignment = Alignment.CenterHorizontally,
	       modifier = modifier.fillMaxWidth()) {
		Row(horizontalArrangement = Arrangement.SpaceBetween,
		    modifier = Modifier
			    .width(120.dp)
			    .padding(vertical = 10.dp)) {
			ThemeButton(theme = settings.state.value.theme) {
				val newTheme = when (settings.state.value.theme)
				{
					AppTheme.Auto  -> AppTheme.Dark
					AppTheme.Dark  -> AppTheme.Light
					AppTheme.Light -> AppTheme.Auto
				}
				settings.onEvent(SettingsEvent.SetTheme(newTheme))
				if (newTheme == AppTheme.Auto && main.state.value.pickerVisibility) toggleVisibility()
			}
			ColorPicker(settings.state.value.theme) {toggleVisibility()}
			FontFace {
				val newFont =
					if (settings.state.value.spacing == "mono") "default" else "mono"
				settings.onEvent(SettingsEvent.SetSpacing(newFont))
			}
		}
		AnimatedVisibility(visible = main.state.value.pickerVisibility) {
			LazyVerticalGrid(columns = GridCells.Fixed(6),
			                 contentPadding = PaddingValues(all = 5.dp),
			                 modifier = Modifier.widthIn(min = 0.dp,
			                                             max = 400.dp)) {
				items(count = AppColor.entries.size) {colorIdx ->
					ColorCard(theme = settings.state.value.theme,
					          color = AppColor.entries[colorIdx]) {
						settings.onEvent(SettingsEvent.SetColor(it))
						toggleVisibility()
					}
				}
			}
		}
	}
}

@Preview()
@Composable
private fun SettingsBarPreview()
{
	val settings = SettingsViewModel(FakeDao())
	val main = MainViewModel()
	BPMTapperTheme {
		Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
			SettingsBar(settings = settings,
			            main = main)
		}
	}
}