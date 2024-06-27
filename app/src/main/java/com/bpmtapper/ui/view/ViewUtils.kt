package com.bpmtapper.ui.view

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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bpmtapper.R
import com.bpmtapper.model.SettingsState
import com.bpmtapper.ui.theme.BPMTapperTheme
import com.bpmtapper.ui.theme.ColorSchemes
import com.bpmtapper.ui.theme.contrast
import com.bpmtapper.ui.theme.AppColor
import com.bpmtapper.ui.theme.shapes.roundrect
import com.bpmtapper.ui.theme.AppTheme

@Composable
fun BPMText(text: String, modifier: Modifier = Modifier)
{
	val reading = stringResource(R.string.reading)
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
				     .wrapContentHeight(align = Alignment.Bottom)
				     .semantics {contentDescription = reading})
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
	val tapText = stringResource(R.string.tap)
	val size = 200.dp
	Button(onClick = tap,
	       shape = MaterialTheme.roundrect(size = size,
	                                       radius = size / 2),
	       modifier = modifier
		       .size(size)
		       .semantics {contentDescription = tapText}) {
		CenterText(text = stringResource(R.string.tap),
		           style = MaterialTheme.typography.displayLarge,
				   modifier = Modifier.height(72.dp))
	}
}

@Composable
fun ResetButton(modifier: Modifier = Modifier, reset: () -> Unit)
{
	val resetText = stringResource(R.string.reset)
	val size = 200.dp
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier.size(size)) {
		Button(onClick = reset,
		       modifier = Modifier.semantics {contentDescription = resetText}) {
			CenterText(text = stringResource(R.string.reset),
			           style = MaterialTheme.typography.headlineSmall,
			           modifier = Modifier.height(36.dp))
		}
	}
}

@Composable
fun CenterText(text: String, style: TextStyle, modifier: Modifier = Modifier)
{
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier) {
		Text(text = text,
		     style = style)
	}
}

@Composable
fun HandButton(left: Boolean = true, onClick: () -> Unit)
{
	val leftHand = stringResource(R.string.left_hand)
	val rightHand = stringResource(R.string.right_hand)
	Box(modifier = Modifier
		.clickable {onClick()}
		.semantics {stateDescription = if (left) leftHand else rightHand}) {
		if (left)
		{
			Icon(painter = painterResource(R.drawable.baseline_back_hand_24),
			     contentDescription = null,
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
			     contentDescription = null,
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
fun ThemeButton(theme: AppTheme, modifier: Modifier = Modifier, onClick: () -> Unit)
{
	val autoTheme = stringResource(R.string.auto_theme)
	val darkMode = stringResource(R.string.dark_mode)
	val lightMode = stringResource(R.string.light_mode)
	Box(modifier = modifier
		.clickable {onClick()}
		.semantics {
			stateDescription = if (theme == AppTheme.Dark) darkMode else lightMode
		}) {
		if (theme == AppTheme.Light || (theme == AppTheme.Auto && !isSystemInDarkTheme()))
		{
			Icon(painter = painterResource(R.drawable.baseline_light_mode_24),
			     tint = MaterialTheme.colorScheme.primary,
			     contentDescription = null,
			     modifier = Modifier.size(35.dp))
		}
		Icon(painter = painterResource(R.drawable.baseline_lightbulb_24),
		     contentDescription = null,
		     tint = MaterialTheme.colorScheme.primary,
		     modifier = Modifier
			     .padding(top = 5.dp)
			     .size(35.dp))
		if (theme == AppTheme.Auto)
		{
			Text(text = "A",
			     color = MaterialTheme.colorScheme.onPrimary,
			     style = MaterialTheme.typography.titleMedium,
			     modifier = Modifier
				     .padding(start = 12.dp,
				              top = 8.dp)
				     .semantics {
					     this.contentDescription = autoTheme
				     })
		}
	}
}

@Composable
fun ColorPicker(theme: AppTheme, modifier: Modifier = Modifier, onClick: () -> Unit)
{
	if (theme != AppTheme.Auto)
	{
		Icon(painter = painterResource(R.drawable.baseline_color_lens_24),
		     contentDescription = stringResource(R.string.pick_color),
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
	theme: AppTheme,
	color: AppColor,
	modifier: Modifier = Modifier,
	onClick: (AppColor) -> Unit,
)
{
	val picked: ColorScheme
	if (theme == AppTheme.Dark || (theme == AppTheme.Auto && isSystemInDarkTheme()))
	{
		picked = when (color)
		{
			AppColor.Red    -> ColorSchemes.DarkRedColorScheme
			AppColor.Orange -> ColorSchemes.DarkOrangeColorScheme
			AppColor.Yellow -> ColorSchemes.DarkYellowColorScheme
			AppColor.Lime   -> ColorSchemes.DarkLimeColorScheme
			AppColor.Green  -> ColorSchemes.DarkGreenColorScheme
			AppColor.Spring -> ColorSchemes.DarkSpringColorScheme
			AppColor.Cyan   -> ColorSchemes.DarkCyanColorScheme
			AppColor.Sky    -> ColorSchemes.DarkSkyColorScheme
			AppColor.Blue   -> ColorSchemes.DarkBlueColorScheme
			AppColor.Violet -> ColorSchemes.DarkVioletColorScheme
			AppColor.Purple -> ColorSchemes.DarkPurpleColorScheme
			else            -> ColorSchemes.DarkMagentaColorScheme
		}
	}
	else
	{
		picked = when (color)
		{
			AppColor.Red    -> ColorSchemes.LightRedColorScheme
			AppColor.Orange -> ColorSchemes.LightOrangeColorScheme
			AppColor.Yellow -> ColorSchemes.LightYellowColorScheme
			AppColor.Lime   -> ColorSchemes.LightLimeColorScheme
			AppColor.Green  -> ColorSchemes.LightGreenColorScheme
			AppColor.Spring -> ColorSchemes.LightSpringColorScheme
			AppColor.Cyan   -> ColorSchemes.LightCyanColorScheme
			AppColor.Sky    -> ColorSchemes.LightSkyColorScheme
			AppColor.Blue   -> ColorSchemes.LightBlueColorScheme
			AppColor.Violet -> ColorSchemes.LightVioletColorScheme
			AppColor.Purple -> ColorSchemes.LightPurpleColorScheme
			else            -> ColorSchemes.LightMagentaColorScheme
		}
	}
	Box(contentAlignment = Alignment.Center,
	    modifier = modifier
		    .size(65.dp)
		    .background(color = picked.primary)
		    .clickable {onClick(color)}
		    .semantics {contentDescription = color.name}) {
		Text(text = color.name,
		     color = picked.onPrimary,
		     style = MaterialTheme.typography.labelLarge)
	}
}

@Composable
fun FontFace(onClick: () -> Unit)
{
	val size = 30.dp
	val spacing = stringResource(R.string.change_font)
	Box(contentAlignment = Alignment.Center,
	    modifier = Modifier
		    .padding(top = 7.dp)
		    .size(size)
		    .clip(shape = MaterialTheme.roundrect(size = Pair(first = size,
		                                                      second = size),
		                                          radius = 7.dp))
		    .background(color = MaterialTheme.colorScheme.primary)
		    .clickable {onClick()}
		    .semantics {contentDescription = spacing}) {
		Text(text = "A",
		     color = MaterialTheme.colorScheme.onPrimary,
		     style = MaterialTheme.typography.titleLarge)
	}
}

@Preview
@Composable
private fun TestDisplay()
{
	val state = SettingsState(theme = AppTheme.Dark,
	                          color = AppColor.Green)
	BPMTapperTheme(settings = state) {
		Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
			BPMText(text = stringResource(R.string.start))
			TapButton {}
			ResetButton {}
			Row {
				ThemeButton(theme = state.theme) {}
				ColorPicker(theme = state.theme) {}
				FontFace {}
			}
			Row {
				HandButton {}
				HandButton(false) {}
			}
			ColorCard(theme = state.theme,
			          color = state.color) {}
		}
	}
}