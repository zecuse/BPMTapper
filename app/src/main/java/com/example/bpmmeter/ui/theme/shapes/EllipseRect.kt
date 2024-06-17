@file:Suppress("UnusedReceiverParameter",
               "SpellCheckingInspection",
               "unused")

package com.example.bpmmeter.ui.theme.shapes

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class EllipseRect(
	private val w: Dp,
	private val h: Dp,
	private val r: FourCorners,
): Shape
{
	override fun createOutline(
		size: Size,
		layoutDirection: LayoutDirection,
		density: Density,
	): Outline
	{
		val width = with(density) {w.toPx()}
		val height = with(density) {h.toPx()}
		val topLeft = CornerRadius(x = with(density) {r.topLeft.first.toPx()},
		                           y = with(density) {r.topLeft.second.toPx()})
		val topRight = CornerRadius(x = with(density) {r.topRight.first.toPx()},
		                            y = with(density) {r.topRight.second.toPx()})
		val bottomRight = CornerRadius(x = with(density) {r.bottomRight.first.toPx()},
		                               y = with(density) {r.bottomRight.second.toPx()})
		val bottomLeft = CornerRadius(x = with(density) {r.bottomLeft.first.toPx()},
		                              y = with(density) {r.bottomLeft.second.toPx()})
		val path = Path().apply {
			addRoundRect(RoundRect(rect = Rect(offset = Offset.Zero,
			                                   size = Size(width = width,
			                                               height = height)),
			                       topLeft = topLeft,
			                       topRight = topRight,
			                       bottomRight = bottomRight,
			                       bottomLeft = bottomLeft))
		}
		return Outline.Generic(path)
	}
}

data class FourCorners(
	val topLeft: Pair<Dp, Dp> = Pair(first = 0.dp,
	                                 second = 0.dp),
	val topRight: Pair<Dp, Dp> = Pair(first = 0.dp,
	                                  second = 0.dp),
	val bottomRight: Pair<Dp, Dp> = Pair(first = 0.dp,
	                                     second = 0.dp),
	val bottomLeft: Pair<Dp, Dp> = Pair(first = 0.dp,
	                                    second = 0.dp),
)

fun MaterialTheme.roundrect(size: Pair<Dp, Dp>, corners: Pair<Dp, Dp>): EllipseRect
{
	return EllipseRect(w = size.first,
	                   h = size.second,
	                   r = FourCorners(topLeft = corners,
	                                   topRight = corners,
	                                   bottomRight = corners,
	                                   bottomLeft = corners))
}

fun MaterialTheme.roundrect(size: Dp, corners: Pair<Dp, Dp>): EllipseRect
{
	return EllipseRect(w = size,
	                   h = size,
	                   r = FourCorners(topLeft = corners,
	                                   topRight = corners,
	                                   bottomRight = corners,
	                                   bottomLeft = corners))
}

fun MaterialTheme.roundrect(size: Pair<Dp, Dp>, radius: Dp): EllipseRect
{
	return EllipseRect(w = size.first,
	                   h = size.second,
	                   r = FourCorners(topLeft = Pair(first = radius,
	                                                  second = radius),
	                                   topRight = Pair(first = radius,
	                                                   second = radius),
	                                   bottomRight = Pair(first = radius,
	                                                      second = radius),
	                                   bottomLeft = Pair(first = radius,
	                                                     second = radius)))
}

fun MaterialTheme.roundrect(size: Dp, radius: Dp): EllipseRect
{
	return EllipseRect(w = size,
	                   h = size,
	                   r = FourCorners(topLeft = Pair(first = radius,
	                                                  second = radius),
	                                   topRight = Pair(first = radius,
	                                                   second = radius),
	                                   bottomRight = Pair(first = radius,
	                                                      second = radius),
	                                   bottomLeft = Pair(first = radius,
	                                                     second = radius)))
}

fun MaterialTheme.roundrect(size: Pair<Dp, Dp>, radii: FourCorners): EllipseRect
{
	return EllipseRect(w = size.first,
	                   h = size.second,
	                   r = radii)
}

fun MaterialTheme.roundrect(size: Dp, radii: FourCorners): EllipseRect
{
	return EllipseRect(w = size,
	                   h = size,
	                   r = radii)
}
