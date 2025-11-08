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

package com.tom.customizablecardview.etc

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import com.google.android.material.card.MaterialCardView
import com.tom.customizablecardview.CustomizableCardView
import com.tom.customizablecardview.R

/*
 * @author Mohammed-baqer-null @ https://github.com/Mohammed-baqer-null
*/

class CustomizableCardViewAttributes(
    private val view: CustomizableCardView,
    private val context: Context,
    private val attrs: AttributeSet?,
    private val defStyle: Int
) {
    fun parseAttributes() {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.CustomizableCardView, defStyle, 0)

            try {
                parseIconAttributes(a)
                parseTextAttributes(a)
                parseStyleAttributes(a)
                parseCardAttributes(a)
                parseBehaviorAttributes(a)
                parseLayoutAttributes(a)
            } finally {
                a.recycle()
            }
        }
    }

    private fun parseIconAttributes(a: android.content.res.TypedArray) {
        val iconRes = a.getResourceId(R.styleable.CustomizableCardView_setCardIcon, -1)
        if (iconRes != -1) {
            view.setCardIcon(iconRes)
        } else {
            a.getDrawable(R.styleable.CustomizableCardView_setCardIcon)?.let { drawable ->
                view.setCardIconDrawable(drawable)
            }
        }

        val iconWidth = a.getDimension(R.styleable.CustomizableCardView_setCardIconWidth, view.dpToPx(24).toFloat())
        val iconHeight = a.getDimension(R.styleable.CustomizableCardView_setCardIconHeight, view.dpToPx(24).toFloat())
        view.setCardIconSize(iconWidth.toInt(), iconHeight.toInt())

        val iconBgColor = a.getColor(R.styleable.CustomizableCardView_setCardIconBackgroundColor, view.iconBackgroundColor)
        view.setCardIconBackgroundColor(iconBgColor)

        val disableIconBg = a.getBoolean(R.styleable.CustomizableCardView_noIconBackground, false)
        view.setNoIconBackground(disableIconBg)

        val hideIcon = a.getBoolean(R.styleable.CustomizableCardView_noIcon, false)
        view.setNoIcon(hideIcon)
    }

    private fun parseTextAttributes(a: android.content.res.TypedArray) {
        a.getString(R.styleable.CustomizableCardView_setCardTitle)?.let { title -> 
            view.setCardTitle(title)
        }

        a.getString(R.styleable.CustomizableCardView_setCardSummary)?.let { summary ->
            view.setCardSummary(summary)
        }

        a.getString(R.styleable.CustomizableCardView_setTitleFont)?.let { titleFontName ->
            view.setTitleFont(titleFontName)
        }

        a.getString(R.styleable.CustomizableCardView_setSummaryFont)?.let { summaryFontName ->
            view.setSummaryFont(summaryFontName)
        }

        val titleTextSizeValue = a.getDimensionPixelSize(
            R.styleable.CustomizableCardView_setTitleTextSize,
            view.dpToPx(17)
        )
        view.setTitleTextSize(titleTextSizeValue)

        val summaryTextSizeValue = a.getDimensionPixelSize(
            R.styleable.CustomizableCardView_setSummaryTextSize,
            view.dpToPx(12)
        )
        view.setSummaryTextSize(summaryTextSizeValue)
    }

    private fun parseStyleAttributes(a: android.content.res.TypedArray) {
        val strokeWidth = a.getDimension(R.styleable.CustomizableCardView_setCardStroke, view.dpToPx(2).toFloat())
        view.setStrokeWidth(strokeWidth.toInt())

        val strokeColor = a.getColor(
            R.styleable.CustomizableCardView_setCardStrokeColor,
            context.resources.getColor(android.R.color.white)
        )
        view.setStrokeColor(strokeColor)

        val backgroundColor = a.getColor(
            R.styleable.CustomizableCardView_setCardBackgroundColor,
            view.cardBackgroundColor.defaultColor
        )
        view.setCardBackgroundColor(backgroundColor)

        val cardElevation = a.getDimension(R.styleable.CustomizableCardView_setCardElevation, view.cardElevation)
        view.setCardElevation(cardElevation)
    }

    private fun parseCardAttributes(a: android.content.res.TypedArray) {
        val checkedIconRes = a.getResourceId(R.styleable.CustomizableCardView_cardCheckedIcon, -1)
        if (checkedIconRes != -1) {
            view.checkedIcon = context.resources.getDrawable(checkedIconRes, context.theme)
        }

        val checkedIconGravity = a.getInt(R.styleable.CustomizableCardView_cardCheckedIconGravity, CustomizableCardViewConstants.TOP_END)
        view.checkedIconGravity = view.convertToMaterialGravity(checkedIconGravity)

        var checkedIconMargin = a.getDimension(R.styleable.CustomizableCardView_cardCheckedIconMargin, -1f)
        if (checkedIconMargin != -1f) {
            view.checkedIconMargin = checkedIconMargin.toInt()
        }

        val isRecommended = a.getBoolean(R.styleable.CustomizableCardView_isCardRecommended, false)
        view.setRecommended(isRecommended)
    }

    private fun parseBehaviorAttributes(a: android.content.res.TypedArray) {
        val isCheckable = a.getBoolean(R.styleable.CustomizableCardView_isCheckableCard, true)
        view.isCheckable = isCheckable
        view.isFocusable = isCheckable

        val isClickable = a.getBoolean(R.styleable.CustomizableCardView_isClickableCard, true)
        view.isClickable = isClickable
    }

    private fun parseLayoutAttributes(a: android.content.res.TypedArray) {
        val hideTitle = a.getBoolean(R.styleable.CustomizableCardView_noTitle, false)
        val hideSummary = a.getBoolean(R.styleable.CustomizableCardView_noSummary, false)

        view.setNoTitle(hideTitle)
        view.setNoSummary(hideSummary)

        val contentAlign = a.getInt(R.styleable.CustomizableCardView_contentAlign, CustomizableCardViewConstants.ALIGN_LEFT)
        view.setContentAlign(contentAlign)
    }
}