package com.moyerun.moyeorun_android.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.moyerun.moyeorun_android.R

class RectangleButton constructor(
    context: Context,
    attributeSet: AttributeSet
) : AppCompatButton(context, attributeSet) {

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.RectangleButton)

        val initialBackground = typedArray.getDrawable(R.styleable.RectangleButton_android_background)
        this.background = initialBackground ?: getDrawable(context, R.drawable.shape_rectangle_button)

        val initialTextColor = typedArray.getColor(R.styleable.RectangleButton_android_textColor, ContextCompat.getColor(context, R.color.main_white))
        this.setTextColor(initialTextColor)

        this.textSize = 21F

        typedArray.recycle()
    }
}