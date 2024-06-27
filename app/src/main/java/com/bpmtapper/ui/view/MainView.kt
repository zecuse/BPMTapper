package com.bpmtapper.ui.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.bpmtapper.R
import com.bpmtapper.database.SettingsDatabase
import com.bpmtapper.ui.theme.BPMTapperTheme
import com.bpmtapper.viewmodel.MainEvent
import com.bpmtapper.viewmodel.MainViewModel
import com.bpmtapper.viewmodel.SettingsFactory
import com.bpmtapper.viewmodel.SettingsViewModel

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
			BPMTapperTheme(settingsModel.state.value) {
				AppLayout(settingsModel)
			}
		}
	}
}

@Composable
fun AppLayout(settingsModel: SettingsViewModel)
{
	val configuration = LocalConfiguration.current
	val mainModel = viewModel<MainViewModel>()
	InitVMStrings(mainModel)
	Box(modifier = Modifier
		.fillMaxSize()
		.background(color = MaterialTheme.colorScheme.background)) {
		when (configuration.orientation)
		{
			Configuration.ORIENTATION_LANDSCAPE -> LandscapeLayout(settings = settingsModel,
			                                                       main = mainModel)
			else                                -> PortraitLayout(main = mainModel,
			                                                      modifier = Modifier.align(alignment = Alignment.BottomCenter))
		}
		SettingsBar(settings = settingsModel,
		            main = mainModel)
	}
}

@Composable
private fun InitVMStrings(main: MainViewModel)
{
	main.onEvent(MainEvent.Init(stringResource(R.string.start),
	                            stringResource(R.string.keep)))
}