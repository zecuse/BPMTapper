package com.example.bpmtapper.viewmodel

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest
{
	private lateinit var mainModel: MainViewModel

	@BeforeEach
	fun setUp()
	{
		mainModel = MainViewModel()
	}

	@Test
	fun `Initial BPMState, startText = 'Start tapping' & keepText = ' Keep tapping'`()
	{
		val start = "Start tapping"
		val keep = " Keep tapping"

		mainModel.onEvent(MainEvent.Init(startText = start,
		                                 keepText = keep))

		assertThat(mainModel.state.value.startText).isEqualTo(start)
		assertThat(mainModel.state.value.keepText).isEqualTo(keep)
	}
}