package com.example.bpmtapper.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.bpmtapper.model.BPMState
import java.util.Locale

class MainViewModel: ViewModel()
{
	val state = mutableStateOf(BPMState())

	fun onEvent(event: MainEvent)
	{
		when (event)
		{
			is MainEvent.Init         ->
			{
				state.apply {
					this.value = this.value.copy(startText = event.startText,
					                             keepText = event.keepText)
				}
			}
			is MainEvent.Display      ->
			{
				val self = state.value
				if (self.startTime == 0L)
				{
					state.apply {
						this.value = this.value.copy(display = self.startText)
					}
				}
				else if (self.times.size == 0)
				{
					state.apply {
						this.value = this.value.copy(display = self.keepText)
					}
				}
				else
				{
					var sum = 0.0
					// Older values have smaller weights, 9 down to 0
					val pos = self.weights.size - self.times.size
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
					state.apply {
						this.value = this.value.copy(display = display)
					}
				}
			}
			is MainEvent.Reset        ->
			{
				state.apply {
					val self = this.value
					this.value = self.copy(startTime = 0L,
					                       display = self.startText)
					self.times.clear()
				}
			}
			is MainEvent.Tap          ->
			{
				if (state.value.startTime == 0L)
				{
					val now = System.currentTimeMillis()
					state.apply {
						this.value = this.value.copy(startTime = now)
					}
				}
				else
				{
					val now = System.currentTimeMillis()
					val elapsed = now - state.value.startTime
					state.apply {
						val self = this.value
						this.value = self.copy(startTime = now,
						                       elapsed = elapsed)
						if (self.times.count() == self.weights.size) self.times.poll()
						self.times.offer(elapsed)
					}
				}
			}
			is MainEvent.TogglePicker ->
			{
				state.apply {
					val self = this.value
					this.value = self.copy(pickerVisibility = !self.pickerVisibility)
				}
			}
		}
	}
}