package com.example.bpmmeter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bpmmeter.model.BPMState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.util.Locale

class MainViewModel: ViewModel()
{
	private val _state = MutableStateFlow(BPMState())
	val state = _state.stateIn(viewModelScope,
	                           SharingStarted.WhileSubscribed(),
	                           BPMState())

	fun onEvent(event: MainEvent)
	{
		when (event)
		{
			is MainEvent.Init         ->
			{
				_state.update {
					it.copy(startText = event.startText,
					        keepText = event.keepText)
				}
			}
			is MainEvent.Display      ->
			{
				val self = state.value
				if (self.startTime == 0L) _state.update {it.copy(display = self.startText)}
				else if (self.times.size == 0) _state.update {it.copy(display = self.keepText)}
				else
				{
					var sum = 0.0
					// Older values have smaller weights, 9 down to 0
					val pos = self.maxTimes - self.times.size
					// Calculate weighted average
					self.times.forEachIndexed {idx, it ->
						sum += self.weights[pos + idx] * it
					}
					sum /= self.weights.takeLast(self.times.size)
						.sum()
					// Convert to BPM
					sum = 60 / (sum / 1000)
					val display = String.Companion.format(locale = Locale.getDefault(),
					                                      format = "%.1f",
					                                      sum)
						.padStart(length = 5,
						          padChar = ' ')
					_state.update {it.copy(display = display)}
				}
			}
			is MainEvent.Reset        ->
			{
				_state.update {it.copy(startTime = 0L)}
				_state.apply {this.value.times.clear()}
			}
			is MainEvent.Tap          ->
			{
				if (state.value.startTime == 0L)
				{
					val now = System.currentTimeMillis()
					_state.update {it.copy(startTime = now)}
				}
				else
				{
					val now = System.currentTimeMillis()
					val elapsed = now - state.value.startTime
					_state.update {it.copy(startTime = now)}
					_state.apply {
						val self = this.value
						if (self.times.count() == self.maxTimes) self.times.poll()
						self.times.offer(elapsed)
					}
				}
			}
			is MainEvent.TogglePicker ->
			{
				_state.update {it.copy(pickerVisibility = !state.value.pickerVisibility)}
			}
		}
	}
}