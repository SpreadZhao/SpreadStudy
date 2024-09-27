package com.example.customviewtest.utils

import android.content.Context
import android.util.TypedValue
import com.example.customviewtest.MainApplication
import java.lang.reflect.Type

val Float.dp
    get() = (MainApplication.context.resources.displayMetrics.density * this) + 0.5f

val Float.sp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, MainApplication.context.resources.displayMetrics)

val Int.dp
    get() = this.toFloat().dp

val Int.sp
    get() = this.toFloat().sp