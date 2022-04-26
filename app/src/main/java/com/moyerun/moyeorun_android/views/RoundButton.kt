package com.moyerun.moyeorun_android.views

import android.content.Context
import android.util.AttributeSet
import android.view.ContextThemeWrapper
import androidx.appcompat.widget.AppCompatButton
import com.moyerun.moyeorun_android.R

class RoundButton constructor(
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatButton(ContextThemeWrapper(context, R.style.RoundButtonTheme), attrs)