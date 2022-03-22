package com.moyerun.moyeorun_android.views

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton
import com.moyerun.moyeorun_android.R

class DefaultButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int = R.attr.DefaultButtonStyle
) : MaterialButton(context, attributeSet, defStyleAttr) {

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.DefaultButton)
        val roundButton = typedArray.getBoolean(R.styleable.DefaultButton_roundButton, false)
        if(roundButton) this.setCornerRadiusResource(R.dimen.roundButtonRadius)
        typedArray.recycle()
    }
}