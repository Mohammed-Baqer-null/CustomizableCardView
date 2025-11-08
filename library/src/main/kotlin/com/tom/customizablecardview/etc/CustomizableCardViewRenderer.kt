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

import android.graphics.Canvas
import android.graphics.Paint
import com.tom.customizablecardview.CustomizableCardView

/*
 * @author Mohammed-baqer-null @ https://github.com/Mohammed-baqer-null
*/

class CustomizableCardViewRenderer(private val view: CustomizableCardView) {

    fun drawRecommendedBadge(canvas: Canvas) {
        if (!view.isRecommended()) return

        val badgePaint = view.badgePaint
        val badgeTextPaint = view.badgeTextPaint
        val badgeTextWidth = view.badgeTextWidth
        val badgeHeight = view.badgeHeight

        val badgeWidth = badgeTextWidth + view.dpToPx(CustomizableCardViewConstants.BADGE_PADDING_H) * 2

        canvas.save()
        canvas.translate(view.width - badgeWidth / 2, 0f)

        val left = -badgeWidth / 2
        val top = 0f
        val right = badgeWidth / 2
        val bottom = badgeHeight

        canvas.drawRoundRect(
            left,
            top,
            right,
            bottom,
            view.dpToPx(CustomizableCardViewConstants.BADGE_CORNER_RADIUS).toFloat(),
            view.dpToPx(CustomizableCardViewConstants.BADGE_CORNER_RADIUS).toFloat(),
            badgePaint
        )

        val textX = 0f
        val textY = badgeHeight / 2 - (badgeTextPaint.descent() + badgeTextPaint.ascent()) / 2
        canvas.drawText(CustomizableCardViewConstants.BADGE_TEXT, textX, textY, badgeTextPaint)

        canvas.restore()
    }
}
