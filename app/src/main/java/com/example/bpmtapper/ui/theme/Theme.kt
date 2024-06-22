package com.example.bpmtapper.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.example.bpmtapper.model.SettingsState

enum class MyColors
{
	Red, Orange, Yellow, Lime, Green, Spring, Cyan, Sky, Blue, Violet, Purple, Magenta
}

enum class ThemeType
{
	Auto, Dark, Light
}

object ColorSchemes
{
	private fun baseDarkColors(): ColorScheme
	{
		return darkColorScheme(background = Grey15)
	}

	private fun baseLightColors(): ColorScheme
	{
		return lightColorScheme(background = Grey90)
	}

	val DarkRedColorScheme = baseDarkColors().copy(primary = Red70,
	                                               onPrimary = Folly15)
	val DarkOrangeColorScheme = baseDarkColors().copy(primary = Tangelo70,
	                                                  onPrimary = Orange15)
	val DarkYellowColorScheme = baseDarkColors().copy(primary = Amber70,
	                                                  onPrimary = Yellow15)
	val DarkLimeColorScheme = baseDarkColors().copy(primary = Chartreuse70,
	                                                onPrimary = Lime15)
	val DarkGreenColorScheme = baseDarkColors().copy(primary = Green70,
	                                                 onPrimary = Harlequin15)
	val DarkSpringColorScheme = baseDarkColors().copy(primary = Spring70,
	                                                  onPrimary = Erin15)
	val DarkCyanColorScheme = baseDarkColors().copy(primary = Aquamarine70,
	                                                onPrimary = Cyan15)
	val DarkSkyColorScheme = baseDarkColors().copy(primary = Azure70,
	                                               onPrimary = Sky15)
	val DarkBlueColorScheme = baseDarkColors().copy(primary = Blue70,
	                                                onPrimary = Persian15)
	val DarkVioletColorScheme = baseDarkColors().copy(primary = Indigo70,
	                                                  onPrimary = Violet15)
	val DarkPurpleColorScheme = baseDarkColors().copy(primary = Purple70,
	                                                  onPrimary = Fuchsia15)
	val DarkMagentaColorScheme = baseDarkColors().copy(primary = Magenta70,
	                                                   onPrimary = Rose15)

	val LightRedColorScheme = baseLightColors().copy(primary = Red15,
	                                                 onPrimary = Folly70)
	val LightOrangeColorScheme = baseLightColors().copy(primary = Tangelo15,
	                                                    onPrimary = Orange70)
	val LightYellowColorScheme = baseLightColors().copy(primary = Amber15,
	                                                    onPrimary = Yellow70)
	val LightLimeColorScheme = baseLightColors().copy(primary = Chartreuse15,
	                                                  onPrimary = Lime70)
	val LightGreenColorScheme = baseLightColors().copy(primary = Green15,
	                                                   onPrimary = Harlequin70)
	val LightSpringColorScheme = baseLightColors().copy(primary = Spring15,
	                                                    onPrimary = Erin70)
	val LightCyanColorScheme = baseLightColors().copy(primary = Aquamarine15,
	                                                  onPrimary = Cyan70)
	val LightSkyColorScheme = baseLightColors().copy(primary = Azure15,
	                                                 onPrimary = Sky70)
	val LightBlueColorScheme = baseLightColors().copy(primary = Blue15,
	                                                  onPrimary = Persian70)
	val LightVioletColorScheme = baseLightColors().copy(primary = Indigo15,
	                                                    onPrimary = Violet70)
	val LightPurpleColorScheme = baseLightColors().copy(primary = Purple15,
	                                                    onPrimary = Fuchsia70)
	val LightMagentaColorScheme = baseLightColors().copy(primary = Magenta15,
	                                                     onPrimary = Rose70)
}

@Composable
fun BPMTapperTheme(
	settings: SettingsState = SettingsState(),
	content: @Composable () -> Unit,
)
{
	val dynamicAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
	val dynamicTheme = dynamicAvailable && settings.theme == ThemeType.Auto
	val colorScheme = when
	{
		dynamicTheme && isSystemInDarkTheme()                                  -> dynamicDarkColorScheme(LocalContext.current)
		dynamicTheme && !isSystemInDarkTheme()                                 -> dynamicLightColorScheme(LocalContext.current)
		settings.theme == ThemeType.Dark && settings.color == MyColors.Red     -> ColorSchemes.DarkRedColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Orange  -> ColorSchemes.DarkOrangeColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Yellow  -> ColorSchemes.DarkYellowColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Lime    -> ColorSchemes.DarkLimeColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Green   -> ColorSchemes.DarkGreenColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Spring  -> ColorSchemes.DarkSpringColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Cyan    -> ColorSchemes.DarkCyanColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Sky     -> ColorSchemes.DarkSkyColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Blue    -> ColorSchemes.DarkBlueColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Violet  -> ColorSchemes.DarkVioletColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Purple  -> ColorSchemes.DarkPurpleColorScheme
		settings.theme == ThemeType.Dark && settings.color == MyColors.Magenta -> ColorSchemes.DarkMagentaColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Red    -> ColorSchemes.LightRedColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Orange -> ColorSchemes.LightOrangeColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Yellow -> ColorSchemes.LightYellowColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Lime   -> ColorSchemes.LightLimeColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Green  -> ColorSchemes.LightGreenColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Spring -> ColorSchemes.LightSpringColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Cyan   -> ColorSchemes.LightCyanColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Sky    -> ColorSchemes.LightSkyColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Blue   -> ColorSchemes.LightBlueColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Violet -> ColorSchemes.LightVioletColorScheme
		settings.theme == ThemeType.Light && settings.color == MyColors.Purple -> ColorSchemes.LightPurpleColorScheme
		else                                                                   -> ColorSchemes.LightMagentaColorScheme
	}
	val contrastColors = when
	{
		settings.theme == ThemeType.Auto && isSystemInDarkTheme() -> TextContrast(high = White,
		                                                                          med = Grey90,
		                                                                          low = Grey70)
		settings.theme == ThemeType.Dark                          -> TextContrast(high = White,
		                                                                          med = Grey90,
		                                                                          low = Grey70)
		else                                                      -> TextContrast(high = Black,
		                                                                          med = Grey15,
		                                                                          low = Grey35)
	}

	CompositionLocalProvider(LocalTextColors provides contrastColors) {
		MaterialTheme(colorScheme = colorScheme,
		              typography = settings.typography,
		              content = content)
	}
}