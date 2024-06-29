package com.zecuse.bpmtapper.ui.view

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.zecuse.bpmtapper.R
import com.zecuse.bpmtapper.ui.theme.BPMTapperTheme
import com.zecuse.bpmtapper.ui.theme.AppColor
import com.zecuse.bpmtapper.viewmodel.FakeDao
import com.zecuse.bpmtapper.viewmodel.MainViewModel
import com.zecuse.bpmtapper.viewmodel.SettingsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EndToEndTest
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
	}

	// Pauses are included for manual verification.
	@Test
	fun endToEnd_Portrait()
	{
		// Setup Portrait layout.
		rule.setContent {
			BPMTapperTheme(settings = settings.state.value) {
				Box(modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colorScheme.background)) {
					PortraitLayout(main = main)
					SettingsBar(settings = settings,
					            main = main)
				}
			}
		}
		// Get all of the initially available elements.
		val theme = rule.onNode(hasStateDescription(rule.activity.getString(R.string.light_mode)))
		val color = rule.onNodeWithContentDescription(rule.activity.getString(R.string.pick_color))
		val spacing = rule.onNodeWithContentDescription(rule.activity.getString(R.string.change_font))
		val reading = rule.onNodeWithContentDescription(rule.activity.getString(R.string.reading))
		val tap = rule.onNodeWithContentDescription(rule.activity.getString(R.string.tap))
		val reset = rule.onNodeWithContentDescription(rule.activity.getString(R.string.reset))
		val start = rule.activity.getString(R.string.start)

		// Change settings: theme -> Dark, color -> Green, spacing -> mono.
		theme.performClick().performClick()
		color.performClick()
		rule.onNodeWithContentDescription(AppColor.Green.name).performClick()
		spacing.performClick()

		// Tap 10 times. Should result in ~120bpm accounting for method call lag.
		(1..10).forEach {_ ->
			tap.performClick()
			Thread.sleep(400)
		}
		// Verify reading is ~120bpm.
		Thread.sleep(1000)

		// Reset reading.
		reset.performClick()
		reading.assertTextEquals(start)
	}

	// Pauses are included for manual verification.
	@Test
	fun endToEnd_Landscape()
	{
		// Setup Landscape layout.
		rule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
		rule.setContent {
			BPMTapperTheme(settings = settings.state.value) {
				Box(modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colorScheme.background)) {
					LandscapeLayout(settings = settings,
					                main = main)
					SettingsBar(settings = settings,
					            main = main)
				}
			}
		}
		// Get all of the initially available elements.
		val theme = rule.onNode(hasStateDescription(rule.activity.getString(R.string.light_mode)))
		val color = rule.onNodeWithContentDescription(rule.activity.getString(R.string.pick_color))
		val spacing = rule.onNodeWithContentDescription(rule.activity.getString(R.string.change_font))
		val reading = rule.onNodeWithContentDescription(rule.activity.getString(R.string.reading))
		val tap = rule.onNodeWithContentDescription(rule.activity.getString(R.string.tap))
		val reset = rule.onNodeWithContentDescription(rule.activity.getString(R.string.reset))
		val hand = rule.onNode(hasStateDescription(rule.activity.getString(R.string.left_hand)))
		val start = rule.activity.getString(R.string.start)

		// Change settings: theme -> Dark, color -> Green, spacing -> mono, switch hands.
		theme.performClick().performClick()
		color.performClick()
		rule.onNodeWithContentDescription(AppColor.Green.name).performClick()
		spacing.performClick()
		hand.performClick()

		// Tap 10 times. Should result in ~120bpm accounting for method call lag.
		(1..10).forEach {_ ->
			tap.performClick()
			Thread.sleep(400)
		}
		// Verify reading is ~120bpm.
		Thread.sleep(1000)

		// Reset reading.
		reset.performClick()
		reading.assertTextEquals(start)
	}
}