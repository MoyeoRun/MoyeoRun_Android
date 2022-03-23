package com.moyerun.moyeorun_android.views
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.moyerun.moyeorun_android.R
import com.moyerun.moyeorun_android.databinding.ViewBadgeRoundImageBinding

class BadgeRoundImageView : ConstraintLayout {
    constructor(context: Context): super(context) { initView() }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        initView()
        setAttrs(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs) {
        initView()
        setAttrs(attrs, defStyle)
    }

    private lateinit var binding: ViewBadgeRoundImageBinding

    private fun initView() { binding = ViewBadgeRoundImageBinding.inflate(LayoutInflater.from(context), this, true) }

    private fun getAttrs(attributeSet: AttributeSet, customViewAttrs: IntArray,  defStyle: Int=0, defStyleRes: Int=0)
    = context.obtainStyledAttributes(attributeSet, customViewAttrs, defStyle, defStyleRes)

    private fun setAttrs(attrs: AttributeSet) {
        val attributesTypedArray = getAttrs(attrs, R.styleable.BadgeRoundImageView)
        setTypedArray(attributesTypedArray)
    }
    private fun setAttrs(attrs: AttributeSet, defStyle: Int) {
        val attributesTypedArray = getAttrs(attrs, R.styleable.BadgeRoundImageView, defStyle, 0)
        setTypedArray(attributesTypedArray)
    }
    private fun setTypedArray(typedArray: TypedArray) {
        binding.imgBigCircle.apply {
            val bigCircleImgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_bigCircleImgSrc, R.drawable.ic_launcher_foreground)
            val bigCircleImgBgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_bigCircleBackgroundColor, R.drawable.ic_launcher_foreground)
            val bigCircleSize = typedArray.getDimensionPixelSize(R.styleable.BadgeRoundImageView_bigCircleSize, 0)

            this.setImageResource(bigCircleImgResId)
            this.setBackgroundResource(bigCircleImgBgResId)
            this.layoutParams.apply {
                this.height = bigCircleSize
                this.width = bigCircleSize
            }
            binding.viewBigCircle.radius = bigCircleSize.toFloat()
        }

        binding.imgBadgeSymbol.apply {
            val badgeResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_badgeImgSrc, R.drawable.ic_launcher_foreground)
            val badgeBgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_badgeBackgroundColor, R.drawable.ic_launcher_foreground)
            val badgeSize = typedArray.getDimensionPixelSize(R.styleable.BadgeRoundImageView_badgeSize, 0)

            this.setImageResource(badgeResId)
            this.setBackgroundResource(badgeBgResId)
            this.layoutParams.apply {
                this.height = badgeSize
                this.width = badgeSize
            }
            binding.viewBadge.radius = badgeSize.toFloat()
        }
        typedArray.recycle()
    }

    fun setBigCircleImgSrc(imgResId: Int) {
        binding.imgBigCircle.setImageResource(imgResId)
    }
    fun setBigCircleImageBg(bgResId: Int) {
        binding.imgBigCircle.setBackgroundResource(bgResId)
    }
    fun setBigCircleImageSize(imgSize: Int) {
        binding.imgBigCircle.layoutParams = binding.imgBigCircle.layoutParams.apply {
            this.height = imgSize
            this.width = imgSize
        }
        binding.viewBigCircle.radius = imgSize.toFloat()
    }
    fun setBadgeImgSrc(imgResId: Int) {
        binding.imgBadgeSymbol.setImageResource(imgResId)
    }
    fun setBadgeImageBg(bgResId: Int) {
        binding.imgBadgeSymbol.setBackgroundResource(bgResId)
    }
    fun setBadgeImageSize(imgSize: Int) {
        binding.imgBadgeSymbol.layoutParams = binding.imgBadgeSymbol.layoutParams.apply {
            this.height = imgSize
            this.width = imgSize
        }
        binding.viewBadge.radius = imgSize.toFloat()
    }
}