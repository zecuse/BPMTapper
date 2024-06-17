package com.example.bpmmeter.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bpmmeter.model.BPM
import java.util.Locale

class MainViewModel: ViewModel()
{
	private val meter = BPM()
	var start: String = ""
	var keep: String = ""

	fun beat()
	{
		if (meter.start == 0L)
		{
			meter.start = System.currentTimeMillis()
		}
		else
		{
			val now = System.currentTimeMillis()
			meter.elapsed = now - meter.start
			meter.start = now
			if (meter.times.count() == meter.maxTimes) meter.times.poll()
			meter.times.offer(meter.elapsed)
		}
	}

	fun reset()
	{
		meter.start = 0L
		meter.times.clear()
	}

	fun display(): String
	{
		if (meter.start == 0L) return start
		else if (meter.times.size == 0) return keep
		else
		{
			var sum = 0.0
			// Older values have smaller weights, 9 down to 0
			val pos = meter.maxTimes - meter.times.size
			// Calculate weighted average
			meter.times.forEachIndexed { idx, it ->
				sum += meter.weights[pos + idx] * it
			}
			sum /= meter.weights.takeLast(meter.times.size).sum()
			// Convert to BPM
			sum = 60 / (sum / 1000)
			return String.Companion
				.format(locale = Locale.getDefault(),
				        format = "%.2f",
				        sum)
				.padStart(length = 6,
				          padChar = ' ')
		}
	}

	var pickerVisibility by mutableStateOf(false)
	fun toggleVisibility()
	{
		pickerVisibility = !pickerVisibility
	}

	var leftHand by mutableStateOf(true)
	fun switchHands()
	{
		leftHand = !leftHand
	}
}