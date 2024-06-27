package com.bpmtapper.ui.view

import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.bpmtapper.R
import com.bpmtapper.ui.theme.BPMTapperTheme
import com.bpmtapper.ui.theme.AppColor
import com.bpmtapper.viewmodel.FakeDao
import com.bpmtapper.viewmodel.MainViewModel
import com.bpmtapper.viewmodel.SettingsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsBarTest
{
	@get:Rule
	val rule = createAndroidComposeRule<ComponentActivity>()

	private lateinit var settings: SettingsViewModel
	private lateinit var main: MainViewModel

	@Before
	fun setUp()
	{
		settings = SettingsViewModel(FakeDao())
		main = MainViewModel()
		rule.setContent {
			BPMTapperTheme(settings = settings.state.value) {
				Box(modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colorScheme.background)) {
					SettingsBar(settings = settings,
					            main = main)
				}
			}
		}
	}

	@Test
	fun initial()
	{
		val light = rule.onNode(hasStateDescription(rule.activity.getString (R.string.light_mode)))
		val color = rule.onNodeWithContentDescription(rule.activity.getString(R.string.pick_color))
		val spacing = rule.onNodeWithContentDescription(rule.activity.getString(R.string.change_font))

		light.assertExists()
		color.assertExists()
		spacing.assertExists()
	}

	@Test
	fun theme_LightMode()
	{
		val light = rule.activity.getString(R.string.light_mode)
		val state = rule.onNode(hasStateDescription(light))

		state.assertExists()
	}

	@Test
	fun theme_Auto()
	{
		val light = rule.activity.getString(R.string.light_mode)
		val auto = rule.activity.getString(R.string.auto_theme)
		val theme = rule.onNode(hasStateDescription(light))

		theme.performClick()

		rule.onNodeWithContentDescription(auto).assertExists()
	}

	@Test
	fun theme_DarkMode()
	{
		val light = rule.activity.getString(R.string.light_mode)
		val dark = rule.activity.getString(R.string.dark_mode)
		val theme = rule.onNode(hasStateDescription(light))

		theme.performClick().performClick()

		rule.onNode(hasStateDescription(dark)).assertExists()
	}

	@Test
	fun color_Exist()
	{
		val pickerText = rule.activity.getString(R.string.pick_color)
		val picker = rule.onNodeWithContentDescription(pickerText)

		picker.performClick()

		AppColor.entries.forEach {
			rule.onNodeWithContentDescription(it.name).assertExists()
		}
	}
}