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

/*
 * @author Mohammed-baqer-null @ https://github.com/Mohammed-baqer-null
*/

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.android.material.textview.MaterialTextView
import com.tom.customizablecardview.CustomizableCardView

class CustomizableCardViewLayoutManager(private val view: CustomizableCardView, private val context: Context) {

    fun createLayout(): LayoutComponents {
        val containerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(view.dpToPx(1), view.dpToPx(1), view.dpToPx(1), view.dpToPx(1))
        }

        val headerLayout = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val iconContainer = createIconContainer()
        val iconView = createIconView()
        iconContainer.addView(iconView)

        val innerLayout = createInnerLayout()
        val titleView = createTitleView()
        val summaryView = createSummaryView()

        innerLayout.addView(titleView)
        innerLayout.addView(summaryView)

        headerLayout.addView(iconContainer)
        headerLayout.addView(innerLayout)

        val childContentLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 0, 0, 0)
            }
        }

        containerLayout.addView(childContentLayout)

        return LayoutComponents(
            containerLayout,
            headerLayout,
            iconContainer,
            iconView,
            innerLayout,
            titleView,
            summaryView,
            childContentLayout
        )
    }

    private fun createIconContainer(): FrameLayout {
        return FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(view.dpToPx(40), view.dpToPx(40))
        }
    }

    private fun createIconView(): ImageView {
        return ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(view.dpToPx(20), view.dpToPx(20), Gravity.CENTER)
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    private fun createInnerLayout(): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(view.dpToPx(8), 0, 0, 0)
            }
        }
    }

    private fun createTitleView(): MaterialTextView {
        return MaterialTextView(context).apply {
            textSize = 17f
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = (6 * context.resources.displayMetrics.density).toInt()
            }
        }
    }

    private fun createSummaryView(): MaterialTextView {
        return MaterialTextView(context).apply {
            textSize = 12f
            alpha = 0.6f
            setSingleLine(false)
            maxLines = Integer.MAX_VALUE
            ellipsize = null
        }
    }

    data class LayoutComponents(
        val containerLayout: LinearLayout,
        val headerLayout: LinearLayout,
        val iconContainer: FrameLayout,
        val iconView: ImageView,
        val innerLayout: LinearLayout,
        val titleView: MaterialTextView,
        val summaryView: MaterialTextView,
        val childContentLayout: LinearLayout
    )
}
