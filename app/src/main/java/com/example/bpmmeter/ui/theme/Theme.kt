package com.example.bpmmeter.ui.theme

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
import com.example.bpmmeter.viewmodel.MyColors
import com.example.bpmmeter.viewmodel.SettingsViewModel
import com.example.bpmmeter.viewmodel.ThemeType

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
	settings: SettingsViewModel = SettingsViewModel(),
	content: @Composable () -> Unit,
)
{
	val dynamicAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
	val dynamicTheme = dynamicAvailable && settings.themeType == ThemeType.Auto
	val colorScheme = when
	{
		dynamicTheme && isSystemInDarkTheme()                                            -> dynamicDarkColorScheme(LocalContext.current)
		dynamicTheme && !isSystemInDarkTheme()                                           -> dynamicLightColorScheme(LocalContext.current)
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Red     -> ColorSchemes.DarkRedColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Orange  -> ColorSchemes.DarkOrangeColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Yellow  -> ColorSchemes.DarkYellowColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Lime    -> ColorSchemes.DarkLimeColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Green   -> ColorSchemes.DarkGreenColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Spring  -> ColorSchemes.DarkSpringColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Cyan    -> ColorSchemes.DarkCyanColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Sky     -> ColorSchemes.DarkSkyColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Blue    -> ColorSchemes.DarkBlueColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Violet  -> ColorSchemes.DarkVioletColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Purple  -> ColorSchemes.DarkPurpleColorScheme
		settings.themeType == ThemeType.Dark && settings.colorScheme == MyColors.Magenta -> ColorSchemes.DarkMagentaColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Red    -> ColorSchemes.LightRedColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Orange -> ColorSchemes.LightOrangeColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Yellow -> ColorSchemes.LightYellowColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Lime   -> ColorSchemes.LightLimeColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Green  -> ColorSchemes.LightGreenColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Spring -> ColorSchemes.LightSpringColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Cyan   -> ColorSchemes.LightCyanColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Sky    -> ColorSchemes.LightSkyColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Blue   -> ColorSchemes.LightBlueColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Violet -> ColorSchemes.LightVioletColorScheme
		settings.themeType == ThemeType.Light && settings.colorScheme == MyColors.Purple -> ColorSchemes.LightPurpleColorScheme
		else                                                                             -> ColorSchemes.LightMagentaColorScheme
	}
	val contrastColors = when
	{
		settings.themeType == ThemeType.Auto && isSystemInDarkTheme() -> TextContrast(high = White,
		                                                                              med = Grey90,
		                                                                              low = Grey70)
		settings.themeType == ThemeType.Dark                          -> TextContrast(high = White,
		                                                                              med = Grey90,
		                                                                              low = Grey70)
		else                                                          -> TextContrast(high = Black,
		                                                                              med = Grey15,
		                                                                              low = Grey35)
	}
	settings.typography = changeFont(base = settings.typography,
	                                 fontFam = settings.fontFamily)

	CompositionLocalProvider(LocalTextColors provides contrastColors) {
		MaterialTheme(colorScheme = colorScheme,
		              typography = settings.typography,
		              content = content)
	}
}