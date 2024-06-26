package com.example.bpmtapper.ui.view

import android.content.pm.ActivityInfo
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.bpmmeter.R
import com.example.bpmtapper.ui.theme.BPMTapperTheme
import com.example.bpmtapper.viewmodel.FakeDao
import com.example.bpmtapper.viewmodel.MainViewModel
import com.example.bpmtapper.viewmodel.SettingsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LandscapeLayoutTest
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
		rule.activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
		rule.setContent {
			BPMTapperTheme(settings = settings.state.value) {
				Box(modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colorScheme.background)) {
					LandscapeLayout(settings = settings,
					                main = main)
				}
			}
		}
	}

	@Test
	fun initial()
	{
		val tap = rule.onNodeWithContentDescription(rule.activity.getString(R.string.tap))
		val reset = rule.onNodeWithContentDescription(rule.activity.getString(R.string.reset))
		val reading = rule.onNodeWithContentDescription(rule.activity.getString(R.string.reading))
		val hand = rule.onNode(hasStateDescription(rule.activity.getString(R.string.left_hand)))

		tap.assertExists()
		reset.assertExists()
		reading.assertExists()
		hand.assertExists()
	}

	@Test
	fun singleTap_keepText()
	{
		val keep = rule.activity.getString(R.string.keep)
		val tap = rule.onNodeWithContentDescription(rule.activity.getString(R.string.tap))
		val reading =
			rule.onNodeWithContentDescription(rule.activity.getString(R.string.reading))

		tap.performClick()

		reading.assertTextEquals(keep)
	}

	@Test
	fun doubleTap_number()
	{
		val tap = rule.onNodeWithContentDescription(rule.activity.getString(R.string.tap))
		val reading =
			rule.onNodeWithContentDescription(rule.activity.getString(R.string.reading))

		tap.performClick()
			.performClick()

		reading.assertTextContains(value = ".",
		                           substring = true)
	}

	@Test
	fun reset_startText()
	{
		val start = rule.activity.getString(R.string.start)
		val tap = rule.onNodeWithContentDescription(rule.activity.getString(R.string.tap))
		val reset =
			rule.onNodeWithContentDescription(rule.activity.getString(R.string.reset))
		val reading =
			rule.onNodeWithContentDescription(rule.activity.getString(R.string.reading))

		tap.performClick()
		reset.performClick()

		reading.assertTextEquals(start)
	}

	@Test
	fun right_exists()
	{
		val rightState = rule.activity.getString(R.string.right_state)
		val state = rule.onNode(hasStateDescription(rightState))

		state.assertExists()
	}

	@Test
	fun left_exists()
	{
		val leftHand = rule.activity.getString(R.string.left_hand)
		val leftState = rule.activity.getString(R.string.left_state)
		val hand = rule.onNode(hasStateDescription(leftHand))

		hand.performClick()

		rule.onNode(hasStateDescription(leftState))
			.assertExists()
	}
}