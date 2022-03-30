package com.moyerun.moyeorun_android.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.moyerun.moyeorun_android.R

class RoundButton constructor(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatButton(context, attributeSet) {

    init {
        this.background = AppCompatResources.getDrawable(context, R.drawable.shape_round_botton)
        this.setTextColor(ContextCompat.getColor(context, R.color.main_white))
        this.textSize = 18F
    }
}