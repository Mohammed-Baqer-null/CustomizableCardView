/*
 *  This file is part of CustomizableCardView.
 *
 *  CustomizableCardView is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  CustomizableCardView is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with CustomizableCardView.  If not, see <https://www.gnu.org/licenses/>.
 */

@file:Suppress("DEPRECATION")
package com.tom.customizablecardview

/*
 * @author Mohammed-baqer-null @ https://github.com/Mohammed-baqer-null
*/

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import kotlin.math.roundToInt
import com.tom.customizablecardview.etc.*

class CustomizableCardView
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    MaterialCardView(context, attrs, defStyleAttr) {

    // Views
    internal lateinit var iconView: ImageView
    internal lateinit var titleView: MaterialTextView
    internal lateinit var summaryView: MaterialTextView
    internal lateinit var containerLayout: LinearLayout
    internal lateinit var innerLayout: LinearLayout
    internal lateinit var childContentLayout: LinearLayout
    internal lateinit var headerLayout: LinearLayout
    internal lateinit var iconContainer: FrameLayout
    
    // Graphics
    internal lateinit var circleBackground: GradientDrawable
    internal lateinit var strokePaint: Paint
    internal lateinit var badgePaint: Paint
    internal lateinit var badgeTextPaint: Paint

    // State
    private var isRecommended = false
    private var noTitle = false
    private var noSummary = false
    private var noIconBackground = false
    private var noIcon = false
    internal var iconBackgroundColor = Color.parseColor("#E0E0E0")

    private var contentAlignment = Gravity.START
    private var iconTint: ColorStateList? = null
    private var autoTintIcon = true
    
    // Measurements
    internal var badgeTextWidth = 0f
    internal var badgeHeight = 0f

    // Helpers
    private lateinit var layoutManager: CustomizableCardViewLayoutManager
    private lateinit var renderer: CustomizableCardViewRenderer
    private lateinit var attributeParser: CustomizableCardViewAttributes

    init {
        init(context, attrs, defStyleAttr)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        iconBackgroundColor = resolveThemeColor(
            com.google.android.material.R.attr.colorPrimaryContainer,
            Color.parseColor("#E0E0E0")
        )
        
        initializePaints()
        initializeCardProperties()
        
        layoutManager = CustomizableCardViewLayoutManager(this, context)
        renderer = CustomizableCardViewRenderer(this)
        
        val components = layoutManager.createLayout()
        containerLayout = components.containerLayout
        headerLayout = components.headerLayout
        iconContainer = components.iconContainer
        iconView = components.iconView
        innerLayout = components.innerLayout
        titleView = components.titleView
        summaryView = components.summaryView
        childContentLayout = components.childContentLayout

        circleBackground = GradientDrawable().apply {
            shape = GradientDrawable.OVAL
            setColor(iconBackgroundColor)
        }
        iconContainer.background = circleBackground

        setupMarqueeTextView(titleView)
        addView(containerLayout)

        attributeParser = CustomizableCardViewAttributes(this, context, attrs, defStyle)
        attributeParser.parseAttributes()

        if (attrs == null) {
            containerLayout.addView(headerLayout, 0)
        }
    }

    private fun initializePaints() {
        strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = dpToPx(0).toFloat()
            color = CustomizableCardViewConstants.STROKE_COLOR
        }

        badgePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
            color = CustomizableCardViewConstants.BADGE_COLOR
        }

        badgeTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = dpToPx(CustomizableCardViewConstants.BADGE_TEXT_SIZE).toFloat()
            textAlign = Paint.Align.CENTER
        }

        badgeTextWidth = badgeTextPaint.measureText(CustomizableCardViewConstants.BADGE_TEXT)
        val fontMetrics = badgeTextPaint.fontMetrics
        badgeHeight = fontMetrics.bottom - fontMetrics.top + dpToPx(CustomizableCardViewConstants.BADGE_PADDING_V) * 2
    }

    private fun initializeCardProperties() {
        isCheckable = true
        isClickable = true
        isFocusable = true
        radius = dpToPx(30).toFloat()
        cardElevation = 0f
        useCompatPadding = true
        preventCornerOverlap = false
        setContentPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16))
        
        setCardBackgroundColor(resolveThemeColor(
            com.google.android.material.R.attr.colorSurfaceContainerLow,
            Color.parseColor("#282e2a") // Fallback
        ))
    }
    
    private fun resolveThemeColor(attr: Int, fallbackColor: Int): Int {
        return try {
            val typedValue = TypedValue()
            if (context.theme.resolveAttribute(attr, typedValue, true)) {
                if (typedValue.resourceId != 0) {
                    ContextCompat.getColor(context, typedValue.resourceId)
                } else {
                    typedValue.data
                }
            } else {
                fallbackColor
            }
        } catch (e: Exception) {
            fallbackColor
        }
    }
    
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        renderer.drawRecommendedBadge(canvas)
    }

    // Public API - Icon methods
    fun setCardIconDrawable(drawable: Drawable?) {
        iconView.setImageDrawable(drawable)
    }

    fun setCardIcon(resourceId: Int) {
        iconView.setImageResource(resourceId)
    }

    fun setCardIconSize(width: Int, height: Int) {
        val params = iconView.layoutParams as FrameLayout.LayoutParams
        params.width = width
        params.height = height
        iconView.layoutParams = params
    }

    fun setCardIconWidth(width: Int) {
        val params = iconView.layoutParams as FrameLayout.LayoutParams
        params.width = width
        iconView.layoutParams = params
    }

    fun setCardIconHeight(height: Int) {
        val params = iconView.layoutParams as FrameLayout.LayoutParams
        params.height = height
        iconView.layoutParams = params
    }

    fun setCardIconBackgroundColor(color: Int) {
        this.iconBackgroundColor = color
        circleBackground.setColor(color)
        if (!noIconBackground) {
            iconContainer.invalidate()
        }
    }

    fun setCardIconBackgroundSize(size: Int) {
        val params = iconContainer.layoutParams as LinearLayout.LayoutParams
        params.width = size
        params.height = size
        iconContainer.layoutParams = params
    }

    fun setIconTint(tint: ColorStateList?) {
        this.iconTint = tint
        iconView.imageTintList = tint
    }

    fun setIconTint(color: Int) {
        this.iconTint = ColorStateList.valueOf(color)
        iconView.imageTintList = iconTint
    }

    fun getIconTint(): ColorStateList? = iconTint

    fun setAutoTintIcon(autoTint: Boolean) {
        this.autoTintIcon = autoTint
        if (autoTint) {
            applyDefaultIconTint(context)
        } else {
            iconView.imageTintList = null
            iconTint = null
        }
    }

    val isAutoTintIcon: Boolean
        get() = autoTintIcon

    // Public API - Text methods
    fun setCardTitle(title: String) {
        titleView.text = title
    }

    fun setCardSummary(summary: CharSequence?) {
        summaryView.text = when {
            summary == null -> null
            summary.contains("\\n") -> {
                val spannable = SpannableStringBuilder(summary)
                var index = 0
                while (index < spannable.length - 1) {
                    if (spannable[index] == '\\' && spannable[index + 1] == 'n') {
                        spannable.delete(index, index + 2)
                        spannable.insert(index, "\n")
                    }
                    index++
                }
                spannable
            }
            else -> summary
        }
    }

    fun setTitleTextSize(size: Int) {
        val scaledSize = size / resources.displayMetrics.scaledDensity
        titleView.textSize = scaledSize
    }

    fun setSummaryTextSize(size: Int) {
        val scaledSize = size / resources.displayMetrics.scaledDensity
        summaryView.textSize = scaledSize
    }

    val titleText: String
        get() = titleView.text.toString()

    val summaryText: String
        get() = summaryView.text.toString()

    fun setTitleFont(fontName: String) {
        try {
            val typeface = Typeface.create(fontName, Typeface.NORMAL)
            titleView.typeface = typeface
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSummaryFont(fontName: String) {
        try {
            val typeface = Typeface.create(fontName, Typeface.NORMAL)
            summaryView.typeface = typeface
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTitleFont(fontName: String, style: Int) {
        try {
            val typeface = Typeface.create(fontName, style)
            titleView.typeface = typeface
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setSummaryFont(fontName: String, style: Int) {
        try {
            val typeface = Typeface.create(fontName, style)
            summaryView.typeface = typeface
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setTitleTypeface(typeface: Typeface?) {
        typeface?.let { titleView.typeface = it }
    }

    fun setSummaryTypeface(typeface: Typeface?) {
        typeface?.let { summaryView.typeface = it }
    }

    // Public API - Layout methods
    fun setNoIconBackground(noBackground: Boolean) {
        this.noIconBackground = noBackground
        iconContainer.background = if (noBackground) null else circleBackground
        iconContainer.invalidate()
    }

    val isNoIconBackground: Boolean
        get() = noIconBackground

    fun setNoIcon(noIcon: Boolean) {
        this.noIcon = noIcon
        iconContainer.visibility = if (noIcon) View.GONE else View.VISIBLE
    }

    val isNoIcon: Boolean
        get() = noIcon

    fun setNoTitle(noTitle: Boolean) {
        this.noTitle = noTitle
        updateLayout()
    }

    fun setNoSummary(noSummary: Boolean) {
        this.noSummary = noSummary
        updateLayout()
    }

    val isNoTitle: Boolean
        get() = noTitle

    val isNoSummary: Boolean
        get() = noSummary

    fun setContentAlign(gravity: Int) {
        this.contentAlignment = gravity
        childContentLayout.gravity = gravity
    }

    val contentAlign: Int
        get() = contentAlignment

    fun hideHeader(hide: Boolean) {
        headerLayout.visibility = if (hide) View.GONE else View.VISIBLE
    }

    // Public API - Card style methods
    fun setSexyCardElevation(elevation: Float) {
        cardElevation = elevation
    }

    fun setSexyCardBackgroundColor(color: Int) {
        setCardBackgroundColor(color)
    }

    fun setSexyCardStroke(strokeWidth: Float) {
        setStrokeWidth(strokeWidth.toInt())
    }

    fun setSexyCardStrokeColor(color: Int) {
        setStrokeColor(color)
    }

    fun setCheckableCard(checkable: Boolean) {
        isCheckable = checkable
        isFocusable = checkable
    }

    fun setClickableCard(clickable: Boolean) {
        isClickable = clickable
    }

    fun setSexyCardCheckedIcon(resourceId: Int) {
        checkedIcon = resources.getDrawable(resourceId, context.theme)
    }

    fun setSexyCardCheckedIconGravity(gravity: Int) {
        checkedIconGravity = convertToMaterialGravity(gravity)
    }

    fun setSexyCardCheckedIconMargin(margin: Int) {
        checkedIconMargin = margin
    }

    // Public API - Badge methods
    fun setRecommended(recommended: Boolean) {
        this.isRecommended = recommended
        invalidate()
    }

    fun setSexyCardRecommended(recommended: Boolean) {
        setRecommended(recommended)
    }

    fun isRecommended(): Boolean = isRecommended

    // Public API - Child content methods
    fun getChildContentLayout(): LinearLayout = childContentLayout

    fun removeAllChildContent() {
        childContentLayout.removeAllViews()
    }

    override fun addView(view: View) {
        if (::containerLayout.isInitialized && view != containerLayout) {
            childContentLayout.addView(view)
        } else {
            super.addView(view)
        }
    }

    override fun addView(view: View, index: Int) {
        if (::containerLayout.isInitialized && view != containerLayout) {
            if (index == -1) {
                childContentLayout.addView(view)
            } else {
                childContentLayout.addView(view, index)
            }
        } else {
            super.addView(view, index)
        }
    }

    override fun addView(view: View, params: ViewGroup.LayoutParams) {
        if (::containerLayout.isInitialized && view != containerLayout) {
            childContentLayout.addView(view, params)
        } else {
            super.addView(view, params)
        }
    }

    override fun addView(view: View, index: Int, params: ViewGroup.LayoutParams) {
        if (::containerLayout.isInitialized && view != containerLayout) {
            if (index == -1) {
                childContentLayout.addView(view, params)
            } else {
                childContentLayout.addView(view, index, params)
            }
        } else {
            super.addView(view, index, params)
        }
    }

    // Internal helper methods
    internal fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).roundToInt()
    }

    internal fun convertToMaterialGravity(gravity: Int): Int {
        return when (gravity) {
            CustomizableCardViewConstants.TOP_START -> MaterialCardView.CHECKED_ICON_GRAVITY_TOP_START
            CustomizableCardViewConstants.TOP_END -> MaterialCardView.CHECKED_ICON_GRAVITY_TOP_END
            CustomizableCardViewConstants.BOTTOM_START -> MaterialCardView.CHECKED_ICON_GRAVITY_BOTTOM_START
            CustomizableCardViewConstants.BOTTOM_END -> MaterialCardView.CHECKED_ICON_GRAVITY_BOTTOM_END
            else -> MaterialCardView.CHECKED_ICON_GRAVITY_TOP_END
        }
    }

    private fun updateLayout() {
        if (containerLayout.indexOfChild(headerLayout) != -1) {
            containerLayout.removeView(headerLayout)
        }

        if (!(noTitle && noSummary)) {
            titleView.visibility = if (noTitle) View.GONE else View.VISIBLE
            summaryView.visibility = if (noSummary) View.GONE else View.VISIBLE
            iconContainer.visibility = if (noIcon) View.GONE else View.VISIBLE
            containerLayout.addView(headerLayout, 0)
        }

        requestLayout()
        invalidate()
    }

    private fun setupMarqueeTextView(textView: MaterialTextView) {
        if (textView == titleView) {
            textView.apply {
                setSingleLine(true)
                ellipsize = TextUtils.TruncateAt.MARQUEE
                marqueeRepeatLimit = -1
                isFocusable = true
                isFocusableInTouchMode = true
                setHorizontallyScrolling(true)
                isSelected = true
            }
        }
    }

    private fun applyDefaultIconTint(context: Context) {
        try {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(
                com.google.android.material.R.attr.colorOnSurface,
                typedValue,
                true
            )

            iconTint = if (typedValue.resourceId != 0) {
                ContextCompat.getColorStateList(context, typedValue.resourceId)
            } else {
                ColorStateList.valueOf(typedValue.data)
            }

            iconTint?.let { iconView.imageTintList = it }
        } catch (e: Exception) {
            iconTint = ColorStateList.valueOf(Color.parseColor("#757575"))
            iconView.imageTintList = iconTint
        }
    }

    val cardIconBackgroundColor: Int
        get() = iconBackgroundColor
}
