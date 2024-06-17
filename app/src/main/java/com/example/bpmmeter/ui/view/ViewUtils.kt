package com.example.bpmmeter.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bpmmeter.R
import com.example.bpmmeter.ui.theme.BPMTapperTheme
import com.example.bpmmeter.ui.theme.ColorSchemes
import com.example.bpmmeter.ui.theme.contrast
import com.example.bpmmeter.ui.theme.shapes.roundrect
import com.example.bpmmeter.viewmodel.MyColors
import com.example.bpmmeter.viewmodel.SettingsFactory
import com.example.bpmmeter.viewmodel.SettingsViewModel
import com.example.bpmmeter.viewmodel.ThemeType

@Composable
fun BPMText(text: String, modifier: Modifier = Modifier)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier
		    .width(400.dp)
		    .height(100.dp)) {
		Column(horizontalAlignment = Alignment.CenterHorizontally,
		       verticalArrangement = Arrangement.SpaceBetween,
		       modifier = Modifier.fillMaxHeight()) {
			Text(text = text,
			     color = MaterialTheme.contrast.high,
			     style = MaterialTheme.typography.displayMedium,
			     modifier = Modifier
				     .height(65.dp)
				     .wrapContentHeight(align = Alignment.Bottom))
			Text(text = stringResource(R.string.bpm),
			     color = MaterialTheme.contrast.high,
			     style = MaterialTheme.typography.titleLarge,
			     modifier = Modifier
				     .height(30.dp)
				     .wrapContentHeight(align = Alignment.Top))
		}
	}
}

@Composable
fun TapButton(modifier: Modifier = Modifier, tap: () -> Unit)
{
	val size = 200.dp
	Button(onClick = tap,
	       shape = MaterialTheme.roundrect(size = size,
	                                       radius = size / 2),
	       modifier = modifier.size(size)) {
		CenterText(text = stringResource(R.string.tap),
		           style = MaterialTheme.typography.headlineLarge)
	}
}

@Composable
fun ResetButton(modifier: Modifier = Modifier, reset: () -> Unit)
{
	val size = 200.dp
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier.size(size)) {
		Button(onClick = reset) {
			CenterText(text = stringResource(R.string.reset),
			           style = MaterialTheme.typography.titleLarge)
		}
	}
}

@Composable
fun CenterText(text: String, style: TextStyle)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier.height(36.dp)) {
		Text(text = text,
		     style = style)
	}
}

@Composable
fun HandIcon(left: Boolean = true, onClick: () -> Unit)
{
	Box(modifier = Modifier.clickable {onClick()}) {
		if (left)
		{
			Icon(painter = painterResource(R.drawable.baseline_back_hand_24),
			     contentDescription = "left",
			     tint = MaterialTheme.colorScheme.primary,
			     modifier = Modifier
				     .size(50.dp)
				     .scale(scaleX = 1f,
				            scaleY = 1f))
			Text(text = "L",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleLarge,
			     modifier = Modifier.offset(x = 21.dp,
			                                y = 23.dp))
		}
		else
		{
			Icon(painter = painterResource(R.drawable.baseline_back_hand_24),
			     contentDescription = "right",
			     tint = MaterialTheme.colorScheme.primary,
			     modifier = Modifier
				     .size(50.dp)
				     .scale(scaleX = -1f,
				            scaleY = 1f))
			Text(text = "R",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleLarge,
			     modifier = Modifier.offset(x = 16.dp,
			                                y = 23.dp))
		}
	}
}

@Composable
fun LightDark(theme: ThemeType, modifier: Modifier = Modifier, onClick: () -> Unit)
{
	Box(modifier = modifier.clickable {onClick()}) {
		if (theme == ThemeType.Light || (theme == ThemeType.Auto && !isSystemInDarkTheme()))
		{
			Icon(painter = painterResource(R.drawable.baseline_light_mode_24),
			     tint = MaterialTheme.colorScheme.primary,
			     contentDescription = "light",
			     modifier = Modifier.size(35.dp))
		}
		Icon(painter = painterResource(R.drawable.baseline_lightbulb_24),
		     contentDescription = "dark",
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = Modifier
			     .padding(top = 5.dp)
			     .size(35.dp))
		if (theme == ThemeType.Auto)
		{
			Text(text = "A",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleMedium,
			     modifier = Modifier.padding(start = 12.dp,
			                                 top = 8.dp))
		}
	}
}

@Composable
fun ColorPicker(theme: ThemeType, modifier: Modifier = Modifier, onClick: () -> Unit)
{
	if (theme != ThemeType.Auto)
	{
		Icon(painter = painterResource(R.drawable.baseline_color_lens_24),
		     contentDescription = "Pick color",
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = modifier
			     .clickable {onClick()}
			     .padding(top = 3.dp,
			              end = 7.dp)
			     .size(35.dp))
	}
}

@Composable
fun ColorCard(
	theme: ThemeType,
	color: MyColors,
	modifier: Modifier = Modifier,
	onClick: (MyColors) -> Unit,
)
{
	val picked: ColorScheme
	if (theme == ThemeType.Dark || (theme == ThemeType.Auto && isSystemInDarkTheme()))
	{
		picked = when (color)
		{
			MyColors.Red    -> ColorSchemes.DarkRedColorScheme
			MyColors.Orange -> ColorSchemes.DarkOrangeColorScheme
			MyColors.Yellow -> ColorSchemes.DarkYellowColorScheme
			MyColors.Lime   -> ColorSchemes.DarkLimeColorScheme
			MyColors.Green  -> ColorSchemes.DarkGreenColorScheme
			MyColors.Spring -> ColorSchemes.DarkSpringColorScheme
			MyColors.Cyan   -> ColorSchemes.DarkCyanColorScheme
			MyColors.Sky    -> ColorSchemes.DarkSkyColorScheme
			MyColors.Blue   -> ColorSchemes.DarkBlueColorScheme
			MyColors.Violet -> ColorSchemes.DarkVioletColorScheme
			MyColors.Purple -> ColorSchemes.DarkPurpleColorScheme
			else            -> ColorSchemes.DarkMagentaColorScheme
		}
	}
	else
	{
		picked = when (color)
		{
			MyColors.Red    -> ColorSchemes.LightRedColorScheme
			MyColors.Orange -> ColorSchemes.LightOrangeColorScheme
			MyColors.Yellow -> ColorSchemes.LightYellowColorScheme
			MyColors.Lime   -> ColorSchemes.LightLimeColorScheme
			MyColors.Green  -> ColorSchemes.LightGreenColorScheme
			MyColors.Spring -> ColorSchemes.LightSpringColorScheme
			MyColors.Cyan   -> ColorSchemes.LightCyanColorScheme
			MyColors.Sky    -> ColorSchemes.LightSkyColorScheme
			MyColors.Blue   -> ColorSchemes.LightBlueColorScheme
			MyColors.Violet -> ColorSchemes.LightVioletColorScheme
			MyColors.Purple -> ColorSchemes.LightPurpleColorScheme
			else            -> ColorSchemes.LightMagentaColorScheme
		}
	}
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier
		    .size(65.dp)
		    .background(color = picked.primary)
		    .clickable {onClick(color)}) {
		Text(text = color.name,
		     color = picked.onPrimary,
		     style = MaterialTheme.typography.labelLarge)
	}
}

@Composable
fun FontFace(onClick: () -> Unit)
{
	val size = 30.dp
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier
		    .padding(top = 7.dp)
		    .size(size)
		    .clip(shape = MaterialTheme.roundrect(size = Pair(first = size,
		                                                      second = size),
		                                          radius = 7.dp))
		    .background(color = MaterialTheme.colorScheme.primary)
		    .clickable {onClick()}) {
		Text(text = "A",
		     color = MaterialTheme.colorScheme.onPrimary,
		     style = MaterialTheme.typography.titleLarge)
	}
}

@Preview
@Composable
private fun TestDisplay()
{
	val settingsModel =
		viewModel<SettingsViewModel>(factory = SettingsFactory(preview = true))
	BPMTapperTheme(settingsModel) {
		Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
			BPMText(text = stringResource(R.string.start))
			TapButton {}
			ResetButton {}
			Row {
				LightDark(theme = settingsModel.themeType) {}
				ColorPicker(theme = settingsModel.themeType) {}
				FontFace {}
			}
			Row {
				HandIcon {}
				HandIcon(false) {}
			}
			ColorCard(theme = ThemeType.Dark,
			          color = MyColors.Magenta) {}
		}
	}
}