package com.moyerun.moyeorun_android.views
import android.Manifest
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Patterns
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresPermission
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.databinding.ViewBadgeRoundImageBinding
import java.io.IOException

class BadgeRoundImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet?=null, defStyle: Int = 0)
    : ConstraintLayout(context, attrs, defStyle) {
    private var binding: ViewBadgeRoundImageBinding = ViewBadgeRoundImageBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if(attrs != null) { setAttrs(attrs, defStyle) }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val verticalPadding = paddingTop + paddingBottom
        val horizontalPadding = paddingStart + paddingEnd
        if(widthMeasureSpec >= heightMeasureSpec) {
            super.onMeasure(heightMeasureSpec + horizontalPadding, heightMeasureSpec + verticalPadding)
        } else {
            super.onMeasure(widthMeasureSpec + horizontalPadding, widthMeasureSpec + verticalPadding)
        }
    }

    private fun getAttrs(attributeSet: AttributeSet, customViewAttrs: IntArray, defStyle: Int=0, defStyleRes: Int=0)
            = context.obtainStyledAttributes(attributeSet, customViewAttrs, defStyle, defStyleRes)

    private fun setAttrs(attrs: AttributeSet, defStyle: Int) {
        val attributesTypedArray = getAttrs(attrs, R.styleable.BadgeRoundImageView, defStyle, 0)
        setTypedArray(attributesTypedArray)
    }
    private fun setTypedArray(typedArray: TypedArray) {
        binding.imgBigCircle.apply {
            val bigCircleImgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_bigCircleImgSrc, R.drawable.ic_launcher_foreground)
            val bigCircleImgBgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_bigCircleBackgroundColor, R.drawable.ic_launcher_foreground)

            this.setImageResource(bigCircleImgResId)
            this.setBackgroundResource(bigCircleImgBgResId)
        }

        binding.imgBadgeSymbol.apply {
            val badgeResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_badgeImgSrc, R.drawable.ic_launcher_foreground)
            val badgeBgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_badgeBackgroundColor, R.color.main_white)
            val badgeSize = typedArray.getDimensionPixelSize(R.styleable.BadgeRoundImageView_badgeSize, 0)

            this.setImageResource(badgeResId)
            this.setBackgroundColor(resources.getColor(badgeBgResId, context?.theme))
            this.layoutParams.apply {
                this.height = badgeSize
                this.width = badgeSize
            }
        }
        typedArray.recycle()
    }

    fun setBigCircleImgSrc(@DrawableRes imgResId: Int) {
        binding.imgBigCircle.setImageResource(imgResId)
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Throws(IOException::class)
    fun setBigCircleImgSrc(imgUrl: String, isImageCropped:Boolean = false) {
        if(Patterns.WEB_URL.matcher(imgUrl).matches()) {
            if(isImageCropped) {
                Glide.with(context).load(imgUrl).centerCrop().into(binding.imgBigCircle)
            } else
                Glide.with(context).load(imgUrl).into(binding.imgBigCircle)
        }
    }

    fun setBigCircleImageBg(@ColorRes bgResId: Int) {
        binding.imgBigCircle.setBackgroundColor(resources.getColor(bgResId, context?.theme))
    }
    fun setBadgeImgSrc(@DrawableRes imgResId: Int) {
        binding.imgBadgeSymbol.setImageResource(imgResId)
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    @Throws(IOException::class)
    fun setBadgeImgSrc(imgUrl: String, isImageCropped:Boolean = false) {
        if(Patterns.WEB_URL.matcher(imgUrl).matches()) {
            if(isImageCropped) {
                Glide.with(context).load(imgUrl).centerCrop().into(binding.imgBadgeSymbol)
            } else
                Glide.with(context).load(imgUrl).into(binding.imgBadgeSymbol)
        }
    }

    fun setBadgeImageBg(@ColorRes bgResId: Int) {
        binding.imgBadgeSymbol.setBackgroundColor(resources.getColor(bgResId, context?.theme))
    }
    fun setBadgeImageSize(@Dimension imgSize: Int) {
        binding.imgBadgeSymbol.layoutParams = binding.imgBadgeSymbol.layoutParams.apply {
            this.height = imgSize
            this.width = imgSize
        }
    }
}