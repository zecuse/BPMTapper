package com.example.bpmmeter.ui.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bpmmeter.R
import com.example.bpmmeter.ui.theme.BPMTapperTheme
import com.example.bpmmeter.ui.theme.ratio
import com.example.bpmmeter.viewmodel.MainViewModel
import com.example.bpmmeter.viewmodel.MyColors
import com.example.bpmmeter.viewmodel.SettingsFactory
import com.example.bpmmeter.viewmodel.SettingsViewModel
import com.example.bpmmeter.viewmodel.ThemeType

class MainActivity: ComponentActivity()
{
	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContent {
			val settingsModel = viewModel<SettingsViewModel>(factory = SettingsFactory())
			AppLayout(settingsModel)
		}
	}
}

@Composable
fun AppLayout(settingsModel: SettingsViewModel)
{
	val configuration = LocalConfiguration.current
	BPMTapperTheme(settingsModel) {
		val mainModel = viewModel<MainViewModel>()
		InitVMStrings(mainModel)
		Box(modifier = Modifier
			.fillMaxSize()
			.background(color = MaterialTheme.colorScheme.background)) {
			when (configuration.orientation)
			{
				Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout(main = mainModel)
				else                                -> PortraitLayout(main = mainModel,
				                                                      modifier = Modifier.align(alignment = Alignment.BottomCenter))
			}
			SettingsBar(settings = settingsModel,
			            main = mainModel)
		}
	}
}

@Composable
fun SettingsBar(settings: SettingsViewModel,
                main: MainViewModel,
                modifier: Modifier = Modifier)
{
	val toggleVisibility = main::toggleVisibility
	Column(horizontalAlignment = Alignment.CenterHorizontally,
	       modifier = modifier.fillMaxWidth()) {
		Row(horizontalArrangement = Arrangement.SpaceBetween,
		    modifier = Modifier
			    .width(120.dp)
			    .padding(vertical = 10.dp)) {
			LightDark(theme = settings.themeType) {
				settings.changeTheme()
				if (settings.themeType == ThemeType.Auto && main.pickerVisibility) toggleVisibility()
			}
			ColorPicker(settings.themeType) {toggleVisibility()}
			FontFace {settings.changeFont()}
		}
		AnimatedVisibility(visible = main.pickerVisibility) {
			LazyVerticalGrid(columns = GridCells.Fixed(6),
			                 contentPadding = PaddingValues(all = 5.dp),
			                 modifier = Modifier.widthIn(min = 0.dp,
			                                             max = 400.dp)) {
				items(count = MyColors.entries.size) {colorIdx ->
					ColorCard(theme = settings.themeType,
					          color = MyColors.entries[colorIdx]) {
						settings.changeColors(it)
						toggleVisibility()
					}
				}
			}
		}
	}
}

@Composable
fun PortraitLayout(main: MainViewModel, modifier: Modifier = Modifier)
{
	var text by remember {
		mutableStateOf(main.start)
	}
	val tap = {
		main.beat()
		text = main.display()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	val reset = {
		main.reset()
		text = main.display()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	LaunchedEffect(Unit) {
		text = main.display()
	}
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

@Composable
fun LandscapeLayout(main: MainViewModel, modifier: Modifier = Modifier)
{
	Column(horizontalAlignment = Alignment.CenterHorizontally,
	       modifier = modifier.fillMaxSize()) {
		Box(contentAlignment = Alignment.BottomCenter,
		    modifier = Modifier.fillMaxHeight(MaterialTheme.ratio.septenth)) {
			LandControls(main)
		}
		Box(contentAlignment = Alignment.TopCenter,
		    modifier = Modifier.fillMaxSize()) {
			ChangeHands(main)
		}
	}
}

@Composable
fun LandControls(main: MainViewModel, modifier: Modifier = Modifier)
{
	var text by remember {
		mutableStateOf(main.start)
	}
	val tap = {
		main.beat()
		text = main.display()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	val reset = {
		main.reset()
		text = main.display()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	LaunchedEffect(Unit) {
		text = main.display()
	}
	Row(verticalAlignment = Alignment.CenterVertically,
	    horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = modifier.fillMaxWidth()) {
		if (main.leftHand) TapButton {tap()}
		else ResetButton {reset()}
		BPMText(text = text)
		if (!main.leftHand) TapButton {tap()}
		else ResetButton {reset()}
	}
}

@Composable
fun ChangeHands(main: MainViewModel, modifier: Modifier = Modifier)
{
	val switchHands = {
		main.switchHands()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	Row(verticalAlignment = Alignment.CenterVertically,
	    horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = modifier.fillMaxWidth()) {
		if (!main.leftHand)
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

@Composable
fun InitVMStrings(main: MainViewModel)
{
	main.start = stringResource(R.string.start)
	main.keep = stringResource(R.string.keep)
}

@Preview(showSystemUi = true,
         device = "spec:width=540dp,height=1230dp,orientation=portrait")
@Composable
fun PortraitPreview()
{
	val settingsModel =
		viewModel<SettingsViewModel>(factory = SettingsFactory(preview = true))
	AppLayout(settingsModel)
}

@Preview(showSystemUi = true,
         device = "spec:width=1230dp,height=540dp,orientation=landscape")
@Composable
fun LandscapePreview()
{
	val settingsModel =
		viewModel<SettingsViewModel>(factory = SettingsFactory(preview = true,
		                                                       orient = Configuration.ORIENTATION_LANDSCAPE))
	AppLayout(settingsModel)
}