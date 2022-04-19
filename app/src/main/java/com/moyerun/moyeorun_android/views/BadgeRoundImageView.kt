package com.moyerun.moyeorun_android.views

import android.Manifest
import android.content.Context
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
import com.moyerun.moyeorun_android.common.extension.dp
import com.moyerun.moyeorun_android.databinding.ViewBadgeRoundImageBinding
import java.io.IOException

class BadgeRoundImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private var binding: ViewBadgeRoundImageBinding =
        ViewBadgeRoundImageBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val attributesTypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BadgeRoundImageView, defStyle, 0)
        with(binding.imgBigCircle) {
            val bigCircleImgResId = attributesTypedArray.getResourceId(
                R.styleable.BadgeRoundImageView_bigCircleImgSrc,
                R.drawable.user_profile_image_default_112dp
            )
            val bigCircleImgBgResId = attributesTypedArray.getResourceId(
                R.styleable.BadgeRoundImageView_bigCircleBackgroundColor,
                R.color.main_white
            )

            this.setImageResource(bigCircleImgResId)
            this.setBackgroundResource(bigCircleImgBgResId)
        }

        with(binding.imgBadgeSymbol) {
            val badgeResId = attributesTypedArray.getResourceId(
                R.styleable.BadgeRoundImageView_badgeImgSrc,
                R.drawable.all_badge_camera_20dp
            )
            val badgeBgResId = attributesTypedArray.getResourceId(
                R.styleable.BadgeRoundImageView_badgeBackgroundColor,
                R.color.main_white
            )
            val badgeSize =
                attributesTypedArray.getDimensionPixelSize(
                    R.styleable.BadgeRoundImageView_badgeSize,
                    35.dp
                )

            this.setImageResource(badgeResId)
            this.setBackgroundColor(resources.getColor(badgeBgResId, context.theme))
            with(this.layoutParams) {
                this.height = badgeSize
                this.width = badgeSize
            }
        }

        attributesTypedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val verticalPadding = paddingTop + paddingBottom
        val horizontalPadding = paddingStart + paddingEnd
        if (widthMeasureSpec >= heightMeasureSpec) {
            super.onMeasure(
                heightMeasureSpec + horizontalPadding,
                heightMeasureSpec + verticalPadding
            )
        } else {
            super.onMeasure(
                widthMeasureSpec + horizontalPadding,
                widthMeasureSpec + verticalPadding
            )
        }
    }

    fun setBigCircleImgSrc(@DrawableRes imgResId: Int) {
        binding.imgBigCircle.setImageResource(imgResId)
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    fun setBigCircleImgSrc(imgUrl: String, isImageCropped: Boolean = false) {
        if (isImageCropped) {
            Glide.with(context).load(imgUrl).centerCrop()
                .placeholder(R.drawable.user_profile_image_default_112dp).into(binding.imgBigCircle)
        } else
            Glide.with(context).load(imgUrl)
                .placeholder(R.drawable.user_profile_image_default_112dp).into(binding.imgBigCircle)
    }

    fun setBigCircleImageBg(@ColorRes bgResId: Int) {
        binding.imgBigCircle.setBackgroundColor(resources.getColor(bgResId, context?.theme))
    }

    fun setBadgeImgSrc(@DrawableRes imgResId: Int) {
        binding.imgBadgeSymbol.setImageResource(imgResId)
    }

    @RequiresPermission(Manifest.permission.INTERNET)
    fun setBadgeImgSrc(imgUrl: String, isImageCropped: Boolean = false) {
        if (isImageCropped) {
            Glide.with(context).load(imgUrl).centerCrop().into(binding.imgBadgeSymbol)
        } else
            Glide.with(context).load(imgUrl).into(binding.imgBadgeSymbol)
    }

    fun setBadgeImageBg(@ColorRes bgResId: Int) {
        binding.imgBadgeSymbol.setBackgroundColor(resources.getColor(bgResId, context?.theme))
    }

    fun setBadgeImageSize(@Dimension imgSize: Int) {
        with(binding.imgBadgeSymbol.layoutParams) {
            this.height = imgSize
            this.width = imgSize
        }
    }
}