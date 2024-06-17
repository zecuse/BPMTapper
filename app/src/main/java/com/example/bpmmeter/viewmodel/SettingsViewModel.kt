package com.example.bpmmeter.viewmodel

import android.content.res.Configuration
import androidx.compose.material3.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bpmmeter.ui.theme.AppFonts
import com.example.bpmmeter.ui.theme.defaultType

enum class MyColors
{
	Red, Orange, Yellow, Lime, Green, Spring, Cyan, Sky, Blue, Violet, Purple, Magenta
}

enum class ThemeType
{
	Auto, Dark, Light
}

class SettingsViewModel: ViewModel()
{

	var colorScheme by mutableStateOf(MyColors.Green)
		private set

	fun changeColors(color: MyColors)
	{
		colorScheme = color
	}

	var themeType by mutableStateOf(ThemeType.Dark)
		private set

	fun changeTheme()
	{
		themeType = when (themeType)
		{
			ThemeType.Auto -> ThemeType.Dark
			ThemeType.Dark -> ThemeType.Light
			else           -> ThemeType.Auto
		}
	}

	var typography: Typography = defaultType
	var fontFamily by mutableStateOf(AppFonts.sourceCodePro)
		private set

	fun changeFont()
	{
		fontFamily = when (fontFamily)
		{
			FontFamily.Default -> AppFonts.sourceCodePro
			else               -> FontFamily.Default
		}
	}
}

class SettingsFactory(
	private val preview: Boolean = false,
	private val orient: Int = Configuration.ORIENTATION_PORTRAIT,
): ViewModelProvider.Factory
{
	@Suppress("UNCHECKED_CAST")
	override fun <T: ViewModel> create(modelClass: Class<T>): T
	{
		val settings = SettingsViewModel()
		if (preview)
		{
			when (orient)
			{
				Configuration.ORIENTATION_LANDSCAPE -> settings.changeTheme()
				else                                ->
				{
				}
			}
		}
		return settings as T
	}
}
