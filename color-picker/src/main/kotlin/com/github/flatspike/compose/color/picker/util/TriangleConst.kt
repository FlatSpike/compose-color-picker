package com.github.flatspike.compose.color.picker.util

import kotlin.math.cos
import kotlin.math.sin

@Suppress("MemberVisibilityCanBePrivate")
internal object TriangleConst {
    val RAD_30 = Math.toRadians(30.0).toFloat()
    val RAD_60 = Math.toRadians(60.0).toFloat()
    val RAD_90 = Math.toRadians(90.0).toFloat()
    val RAD_120 = Math.toRadians(120.0).toFloat()
    val RAD_180 = Math.toRadians(180.0).toFloat()
    val RAD_240 = Math.toRadians(240.0).toFloat()
    val RAD_300 = Math.toRadians(300.0).toFloat()

    val SIN_60 = sin(RAD_60)
    val SIN_120 = sin(RAD_120)
    val SIN_180 = sin(RAD_180)
    val SIN_240 = sin(RAD_240)
    val SIN_300 = sin(RAD_300)

    val COS_60 = cos(RAD_60)
    val COS_120 = cos(RAD_120)
    val COS_180 = cos(RAD_180)
    val COS_240 = cos(RAD_240)
    val COS_300 = cos(RAD_300)
}
