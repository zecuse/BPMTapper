package com.zecuse.bpmtapper.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.zecuse.bpmtapper.ui.theme.BPMTapperTheme
import com.zecuse.bpmtapper.ui.theme.ratio
import com.zecuse.bpmtapper.viewmodel.FakeDao
import com.zecuse.bpmtapper.viewmodel.MainEvent
import com.zecuse.bpmtapper.viewmodel.MainViewModel
import com.zecuse.bpmtapper.viewmodel.SettingsViewModel

@Composable
fun PortraitLayout(main: MainViewModel, modifier: Modifier = Modifier)
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
	Column(horizontalAlignment = Alignment.CenterHorizontally,
	       verticalArrangement = Arrangement.SpaceEvenly,
	       modifier = modifier
		       .fillMaxWidth()
		       .fillMaxHeight(MaterialTheme.ratio.nonenth)) {
		BPMText(text = text)
		TapButton {tap()}
		ResetButton {reset()}
	}
}

@Preview(showSystemUi = true,
         device = "spec:width=540dp,height=1230dp,orientation=portrait")
@Composable
private fun PortraitPreview()
{
	val fake = SettingsViewModel(FakeDao())
	BPMTapperTheme {
		AppLayout(fake)
	}
}