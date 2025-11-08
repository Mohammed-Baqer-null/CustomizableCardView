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

import android.graphics.Color
import android.view.Gravity

/*
 * @author Mohammed-baqer-null @ https://github.com/Mohammed-baqer-null
*/

object CustomizableCardViewConstants {
    const val TOP_END = 0x00000001
    const val TOP_START = 0x00000002
    const val BOTTOM_END = 0x00000003
    const val BOTTOM_START = 0x00000004

    const val ALIGN_LEFT = Gravity.START
    const val ALIGN_CENTER = Gravity.CENTER_HORIZONTAL
    const val ALIGN_RIGHT = Gravity.END

    const val BADGE_PADDING_H = 8
    const val BADGE_PADDING_V = 4
    const val BADGE_CORNER_RADIUS = 4
    const val BADGE_TEXT_SIZE = 10
    const val BADGE_TEXT = "New"
    val BADGE_COLOR = Color.parseColor("#FFD700")
    const val STROKE_COLOR = 0xFFFFFFFF.toInt()
}
