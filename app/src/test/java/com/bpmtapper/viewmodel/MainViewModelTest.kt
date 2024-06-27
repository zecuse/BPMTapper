package com.bpmtapper.viewmodel

import com.bpmtapper.model.BPMState
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest
{
	private lateinit var mainModel: MainViewModel
	private lateinit var state: BPMState

	@BeforeEach
	fun setUp()
	{
		mainModel = MainViewModel()
	}

	@Test
	fun `Init event, startText = 'Start tapping' & keepText = ' Keep tapping'`()
	{
		val start = "Start tapping"
		val keep = " Keep tapping"

		mainModel.onEvent(MainEvent.Init(startText = start,
		                                 keepText = keep))
		state = mainModel.state.value

		assertThat(state.startText).isEqualTo(start)
		assertThat(state.keepText).isEqualTo(keep)
		assertThat(state.display).isEqualTo(start)
		assertThat(state.pickerVisibility).isFalse()
		assertThat(state.startTime).isEqualTo(0)
		assertThat(state.elapsed).isEqualTo(0)
		assertThat(state.times).isEmpty()
	}

	@Test
	fun `TogglePicker event, pickerVisibility = true`()
	{
		mainModel.onEvent(MainEvent.TogglePicker)
		state = mainModel.state.value

		assertThat(state.pickerVisibility).isTrue()
	}

	@Test
	fun `TogglePicker event twice, pickerVisibility = false`()
	{
		(1..2).forEach {_ ->
			mainModel.onEvent(MainEvent.TogglePicker)
			Thread.sleep(10)
		}
		state = mainModel.state.value

		assertThat(state.pickerVisibility).isFalse()
	}

	@Test
	fun `Tap event once, startTime != 0`()
	{
		mainModel.onEvent(MainEvent.Tap)
		state = mainModel.state.value

		assertThat(state.startTime).isGreaterThan(0L)
		assertThat(state.elapsed).isEqualTo(0)
		assertThat(state.times).isEmpty()
	}

	@Test
	fun `Tap event twice, elapsed != 0`()
	{
		(1..2).forEach {_ ->
			mainModel.onEvent(MainEvent.Tap)
			Thread.sleep(10)
		}
		state = mainModel.state.value

		assertThat(state.startTime).isGreaterThan(0L)
		assertThat(state.elapsed).isGreaterThan(0L)
		assertThat(state.times.count()).isEqualTo(1)
	}

	@Test
	fun `Tap event more than 12 times, times_count() = 10`()
	{
		(1..15).forEach {_ ->
			mainModel.onEvent(MainEvent.Tap)
			Thread.sleep(10)
		}
		state = mainModel.state.value

		assertThat(state.times.count()).isEqualTo(10)
	}

	@Test
	fun `Reset event, startTime = 0 & times_count() = 0`()
	{
		(1..5).forEach {_ ->
			mainModel.onEvent(MainEvent.Tap)
			Thread.sleep(10)
		}
		mainModel.onEvent(MainEvent.Reset)
		state = mainModel.state.value

		assertThat(state.startTime).isEqualTo(0)
		assertThat(state.times.count()).isEqualTo(0)
	}

	@Test
	fun `Display event no taps, display = startText`()
	{
		mainModel.onEvent(MainEvent.Display)
		state = mainModel.state.value

		assertThat(state.display).isEqualTo(state.startText)
	}

	@Test
	fun `Display event 1 tap, display = keepText`()
	{
		mainModel.onEvent(MainEvent.Tap)
		mainModel.onEvent(MainEvent.Display)
		state = mainModel.state.value

		assertThat(state.display).isEqualTo(state.keepText)
	}

	@Test
	fun `Display event 2+ taps, display = d+,d`()
	{
		(1..5).forEach {_ ->
			mainModel.onEvent(MainEvent.Tap)
			Thread.sleep(10)
		}
		mainModel.onEvent(MainEvent.Display)
		state = mainModel.state.value

		assertThat(state.display).containsMatch("\\d+\\.\\d")
	}
}