package com.example.bpmtapper.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bpmtapper.ui.theme.BPMTapperTheme
import com.example.bpmtapper.ui.theme.ThemeType
import com.example.bpmtapper.ui.theme.ratio
import com.example.bpmtapper.viewmodel.FakeDao
import com.example.bpmtapper.viewmodel.MainEvent
import com.example.bpmtapper.viewmodel.MainViewModel
import com.example.bpmtapper.viewmodel.SettingsEvent
import com.example.bpmtapper.viewmodel.SettingsViewModel

@Composable
fun LandscapeLayout(settings: SettingsViewModel,
                    main: MainViewModel,
                    modifier: Modifier = Modifier)
{
	Column(horizontalAlignment = Alignment.CenterHorizontally,
	       modifier = modifier.fillMaxSize()) {
		Box(contentAlignment = Alignment.BottomCenter,
		    modifier = Modifier.fillMaxHeight(MaterialTheme.ratio.septenth)) {
			LandControls(settings = settings,
			             main = main)
		}
		Box(contentAlignment = Alignment.TopCenter,
		    modifier = Modifier.fillMaxSize()) {
			ChangeHands(settings = settings,
			            main = main)
		}
	}
}

@Composable
fun LandControls(settings: SettingsViewModel,
                 main: MainViewModel,
                 modifier: Modifier = Modifier)
{
	var text by remember {
		mutableStateOf(main.state.value.startText)
	}
	val display = {
		main.onEvent(MainEvent.Display)
		text = main.state.value.display
		if (main.state.value.pickerVisibility) main.onEvent(MainEvent.TogglePicker)
	}
	val tap = {
		main.onEvent(MainEvent.Tap)
		display()
	}
	val reset = {
		main.onEvent(MainEvent.Reset)
		display()
	}
	LaunchedEffect(Unit) {display()}
	Row(verticalAlignment = Alignment.CenterVertically,
	    horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = modifier.fillMaxWidth()) {
		if (settings.state.value.leftHanded) TapButton {tap()}
		else ResetButton {reset()}
		BPMText(text = text)
		if (!settings.state.value.leftHanded) TapButton {tap()}
		else ResetButton {reset()}
	}
}

@Composable
fun ChangeHands(settings: SettingsViewModel,
                main: MainViewModel,
                modifier: Modifier = Modifier)
{
	val switchHands = {
		settings.onEvent(SettingsEvent.SetHandedness(!settings.state.value.leftHanded))
		if (main.state.value.pickerVisibility) main.onEvent(MainEvent.TogglePicker)
	}
	Row(verticalAlignment = Alignment.CenterVertically,
	    horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = modifier.fillMaxWidth()) {
		if (!settings.state.value.leftHanded)
		{
			HandIcon {switchHands()}
			Spacer(modifier = Modifier.width(132.dp))
		}
		else
		{
			Spacer(modifier = Modifier.width(132.dp))
			HandIcon(false) {switchHands()}
		}
	}
}

@Preview(showSystemUi = true,
         device = "spec:width=1230dp,height=540dp,orientation=landscape")
@Composable
fun LandscapePreview()
{
	val fake = SettingsViewModel(FakeDao())
	fake.onEvent(SettingsEvent.SetTheme(ThemeType.Light))
	BPMTapperTheme(settings = fake.state.value) {
		AppLayout(fake)
	}
}