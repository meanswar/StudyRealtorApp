package com.studyrealtorapp.util.ext

import android.graphics.Rect
import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.dividerHorizontal(
    @DrawableRes dividerRes: Int,
    ignoreFirstItem: Boolean = false,
    ignoreLastItem: Boolean = false,
    divideByNearby: Boolean = false
) {
    divider(
        dividerRes,
        DividerItemDecoration.HORIZONTAL,
        ignoreFirstItem,
        ignoreLastItem,
        divideByNearby
    )
}

fun RecyclerView.dividerVertical(
    @DrawableRes dividerRes: Int,
    ignoreFirstItem: Boolean = false,
    ignoreLastItem: Boolean = false,
    divideByNearby: Boolean = false
) {
    divider(
        dividerRes,
        DividerItemDecoration.VERTICAL,
        ignoreFirstItem,
        ignoreLastItem,
        divideByNearby
    )
}

fun RecyclerView.divider(
    @DrawableRes dividerRes: Int,
    orientation: Int,
    ignoreFirstItem: Boolean = false,
    ignoreLastItem: Boolean = false,
    divideByNearby: Boolean = false
) {
    val divider = drawable(dividerRes)!!

    addItemDecoration(object : DividerItemDecoration(context, orientation) {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val halfHeight = drawable!!.intrinsicHeight / 2
            val halfWidth = drawable!!.intrinsicWidth / 2

            val position = parent.getChildAdapterPosition(view)

            if (ignoreFirstItem && position == 0) {
                if (divideByNearby) {
                    when (orientation) {
                        VERTICAL -> outRect.set(0, 0, 0, halfHeight)
                        HORIZONTAL -> outRect.set(0, 0, halfWidth, 0)
                    }
                } else {
                    outRect.setEmpty()
                }
            } else if (ignoreLastItem && position == state.itemCount - 1) {
                if (divideByNearby) {
                    when (orientation) {
                        VERTICAL -> outRect.set(0, halfHeight, 0, 0)
                        HORIZONTAL -> outRect.set(halfWidth, 0, 0, 0)
                    }
                } else {
                    outRect.setEmpty()
                }
            } else {
                if (divideByNearby) {
                    when (orientation) {
                        VERTICAL -> outRect.set(0, halfHeight, 0, halfHeight)
                        HORIZONTAL -> outRect.set(halfWidth, 0, halfWidth, 0)
                    }
                } else {
                    super.getItemOffsets(outRect, view, parent, state)
                }
            }
        }
    }.apply {
        setDrawable(divider)
    })
}

