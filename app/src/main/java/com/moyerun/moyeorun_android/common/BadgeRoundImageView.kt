package com.moyerun.moyeorun_android.common
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
        getAttrs(attrs)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs) {
        initView()
        getAttrs(attrs, defStyle)
    }

//  뷰 참조할 때 사용
    private lateinit var binding: ViewBadgeRoundImageBinding

//  View Binding으로 레이아웃 파일 연결
    private fun initView() { binding = ViewBadgeRoundImageBinding.inflate(LayoutInflater.from(context), this, true) }

//  attrs.xml에 선언해둔 attribute를 이용하여 이를 각각의 View에 설정해준다.
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeRoundImageView)
        setTypedArray(typedArray)
    }
    private fun getAttrs(attrs: AttributeSet, defStyle: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeRoundImageView, defStyle, 0)
        setTypedArray(typedArray)
    }
    private fun setTypedArray(typedArray: TypedArray) {
        binding.imgBigCircle.apply {
            val bigCircleImgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_bigCircleImgSrc, R.drawable.ic_launcher_foreground)
            val bigCircleImgBgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_bigCircleImgBackground, R.drawable.ic_launcher_foreground)
            val bigCircleImgSize = typedArray.getDimensionPixelSize(R.styleable.BadgeRoundImageView_bigCircleImgSize, 0)

            this.setImageResource(bigCircleImgResId)
            this.setBackgroundResource(bigCircleImgBgResId)
            this.layoutParams.apply {
                this.height = bigCircleImgSize
                this.width = bigCircleImgSize
            }
            binding.viewBigCircle.radius = bigCircleImgSize.toFloat()
        }

        binding.imgBadgeSymbol.apply {
            val badgeResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_badgeSrc, R.drawable.ic_launcher_foreground)
            val badgeBgResId = typedArray.getResourceId(R.styleable.BadgeRoundImageView_badgeBackground, R.drawable.ic_launcher_foreground)
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

//  Set Programmatically
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