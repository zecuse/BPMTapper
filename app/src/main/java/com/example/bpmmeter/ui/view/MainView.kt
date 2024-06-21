package com.example.bpmmeter.ui.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.collectAsState
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
import androidx.room.Room
import com.example.bpmmeter.R
import com.example.bpmmeter.database.SettingsDatabase
import com.example.bpmmeter.viewmodel.SettingsEvent
import com.example.bpmmeter.model.SettingsState
import com.example.bpmmeter.ui.theme.BPMTapperTheme
import com.example.bpmmeter.ui.theme.MyColors
import com.example.bpmmeter.ui.theme.ratio
import com.example.bpmmeter.ui.theme.ThemeType
import com.example.bpmmeter.viewmodel.MainViewModel
import com.example.bpmmeter.viewmodel.SettingsFactory
import com.example.bpmmeter.viewmodel.SettingsViewModel

class MainActivity: ComponentActivity()
{
	private val db by lazy {
		Room.databaseBuilder(context = applicationContext,
		                     klass = SettingsDatabase::class.java,
		                     name = "settings.db")
			.build()
	}

	private val settingsModel by viewModels<SettingsViewModel>(factoryProducer = {SettingsFactory(db)})

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
//		applicationContext.deleteDatabase("settings.db")
		setContent {
			val state by settingsModel.state.collectAsState()
			BPMTapperTheme(settings = state) {
				AppLayout(state,
				          settingsModel::onEvent)
			}
		}
	}
}

@Composable
fun AppLayout(state: SettingsState, onEvent: (SettingsEvent) -> Unit)
{
	val configuration = LocalConfiguration.current
	val mainModel = viewModel<MainViewModel>()
	InitVMStrings(mainModel)
	Box(modifier = Modifier
		.fillMaxSize()
		.background(color = MaterialTheme.colorScheme.background)) {
		when (configuration.orientation)
		{
			Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout(settings = state,
			                                                       onEvent = onEvent,
			                                                       main = mainModel)
			else                                -> PortraitLayout(main = mainModel,
			                                                      modifier = Modifier.align(alignment = Alignment.BottomCenter))
		}
		SettingsBar(settings = state,
		            onEvent = onEvent,
		            main = mainModel)
	}
}

@Composable
fun SettingsBar(settings: SettingsState,
                onEvent: (SettingsEvent) -> Unit,
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
			LightDark(theme = settings.theme) {
				val newTheme = when (settings.theme)
				{
					ThemeType.Auto  -> ThemeType.Dark
					ThemeType.Dark  -> ThemeType.Light
					ThemeType.Light -> ThemeType.Auto
				}
				onEvent(SettingsEvent.SetTheme(newTheme))
				if (newTheme == ThemeType.Auto && main.pickerVisibility) toggleVisibility()
			}
			ColorPicker(settings.theme) {toggleVisibility()}
			FontFace {
				val newFont = if (settings.spacing == "mono") "default" else "mono"
				onEvent(SettingsEvent.SetSpacing(newFont))
			}
		}
		AnimatedVisibility(visible = main.pickerVisibility) {
			LazyVerticalGrid(columns = GridCells.Fixed(6),
			                 contentPadding = PaddingValues(all = 5.dp),
			                 modifier = Modifier.widthIn(min = 0.dp,
			                                             max = 400.dp)) {
				items(count = MyColors.entries.size) {colorIdx ->
					ColorCard(theme = settings.theme,
					          color = MyColors.entries[colorIdx]) {
						onEvent(SettingsEvent.SetColor(it))
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
	val display = {
		text = main.display()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	val tap = {
		main.beat()
		display()
	}
	val reset = {
		main.reset()
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

@Composable
fun LandscapeLayout(settings: SettingsState,
                    onEvent: (SettingsEvent) -> Unit,
                    main: MainViewModel,
                    modifier: Modifier = Modifier)
{
	Column(horizontalAlignment = Alignment.CenterHorizontally,
	       modifier = modifier.fillMaxSize()) {
		Box(contentAlignment = Alignment.BottomCenter,
		    modifier = Modifier.fillMaxHeight(MaterialTheme.ratio.septenth)) {
			LandControls(settings = settings, main = main)
		}
		Box(contentAlignment = Alignment.TopCenter,
		    modifier = Modifier.fillMaxSize()) {
			ChangeHands(settings = settings,
			            onEvent = onEvent,
			            main = main)
		}
	}
}

@Composable
fun LandControls(settings: SettingsState,
                 main: MainViewModel,
                 modifier: Modifier = Modifier)
{
	var text by remember {
		mutableStateOf(main.start)
	}
	val display = {
		text = main.display()
		if (main.pickerVisibility) main.toggleVisibility()
	}
	val tap = {
		main.beat()
		display()
	}
	val reset = {
		main.reset()
		display()
	}
	LaunchedEffect(Unit) {display()}
	Row(verticalAlignment = Alignment.CenterVertically,
	    horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = modifier.fillMaxWidth()) {
		if (settings.leftHanded) TapButton {tap()}
		else ResetButton {reset()}
		BPMText(text = text)
		if (!settings.leftHanded) TapButton {tap()}
		else ResetButton {reset()}
	}
}

@Composable
fun ChangeHands(settings: SettingsState,
                onEvent: (SettingsEvent) -> Unit,
                main: MainViewModel,
                modifier: Modifier = Modifier)
{
	val switchHands = {
		onEvent(SettingsEvent.SetHandedness(!settings.leftHanded))
		if (main.pickerVisibility) main.toggleVisibility()
	}
	Row(verticalAlignment = Alignment.CenterVertically,
	    horizontalArrangement = Arrangement.SpaceEvenly,
	    modifier = modifier.fillMaxWidth()) {
		if (!settings.leftHanded)
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
	val onEvent = {_: SettingsEvent ->}
	BPMTapperTheme {
		AppLayout(state = SettingsState(),
		          onEvent = onEvent)
	}
}

@Preview(showSystemUi = true,
         device = "spec:width=1230dp,height=540dp,orientation=landscape")
@Composable
fun LandscapePreview()
{
	val state = SettingsState(theme = ThemeType.Light)
	val onEvent = {_: SettingsEvent ->}
	BPMTapperTheme(settings = state) {
		AppLayout(state = SettingsState(),
		          onEvent = onEvent)
	}
}