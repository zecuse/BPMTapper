package com.example.bpmtapper.viewmodel

import com.example.bpmtapper.model.SettingsState
import com.example.bpmtapper.ui.theme.MyColors
import com.example.bpmtapper.ui.theme.ThemeType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
	private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
): BeforeEachCallback, AfterEachCallback
{
	override fun beforeEach(context: ExtensionContext?)
	{
		Dispatchers.setMain(testDispatcher)
	}

	override fun afterEach(context: ExtensionContext?)
	{
		Dispatchers.resetMain()
	}
}

@ExtendWith(MainDispatcherRule::class)
class SettingsViewModelTest
{
	private lateinit var settingsModel: SettingsViewModel
	private lateinit var state: SettingsState

	@BeforeEach
	fun setUp()
	{
		settingsModel = SettingsViewModel(FakeDao())
	}

	@Test
	fun `Init, defaults from FakeDao`()
	{
		state = settingsModel.state.value

		assertThat(state.theme).isEqualTo(ThemeType.Light)
		assertThat(state.color).isEqualTo(MyColors.Magenta)
		assertThat(state.leftHanded).isFalse()
		assertThat(state.spacing).isEqualTo("default")
	}

	@Test
	fun `SetColor event, color = Blue`()
	{
		settingsModel.onEvent(SettingsEvent.SetColor(MyColors.Blue))
		state = settingsModel.state.value

		assertThat(state.color).isEqualTo(MyColors.Blue)
	}

	@Test
	fun `SetHandedness event, leftHanded = false`()
	{
		settingsModel.onEvent(SettingsEvent.SetHandedness(false))
		state = settingsModel.state.value

		assertThat(state.leftHanded).isFalse()
	}

	@Test
	fun `SetSpacing event, spacing = mono`()
	{
		settingsModel.onEvent(SettingsEvent.SetSpacing("mono"))
		state = settingsModel.state.value

		assertThat(state.spacing).isEqualTo("mono")
	}

	@Test
	fun `SetTheme event, theme = Dark`()
	{
		settingsModel.onEvent(SettingsEvent.SetTheme(ThemeType.Dark))
		state = settingsModel.state.value

		assertThat(state.theme).isEqualTo(ThemeType.Dark)
	}
}