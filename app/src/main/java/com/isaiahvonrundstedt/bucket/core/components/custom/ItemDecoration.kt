package com.isaiahvonrundstedt.bucket.core.components.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ItemDecoration (context: Context?): RecyclerView.ItemDecoration() {

    private var divider: Drawable? = null

    init {
        val attrib = context?.obtainStyledAttributes(attrs)
        divider = attrib?.getDrawable(0)
        attrib?.recycle()
    }

    companion object {
        private var attrs = intArrayOf(android.R.attr.listDivider)
    }

    override fun onDraw(canvas: Canvas, container: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = 0
        val dividerRight = container.width

        for (i in 0 until container.childCount){
            val childView: View = container.getChildAt(i)
            val layoutParameters: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

            val dividerTop: Int = childView.bottom + layoutParameters.bottomMargin
            val dividerBottom: Int = dividerTop + divider!!.intrinsicHeight

            divider!!.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider!!.draw(canvas)
        }
    }

    override fun onDrawOver(canvas: Canvas, container: RecyclerView, state: RecyclerView.State) {
        val dividerLeft = 0
        val dividerRight = container.width

        for (i in 0 until container.childCount){

            val childView: View = container.getChildAt(i)
            val layoutParameters: RecyclerView.LayoutParams = childView.layoutParams as RecyclerView.LayoutParams

            val dividerTop = childView.top + layoutParameters.topMargin
            val dividerBottom = dividerTop + divider!!.intrinsicHeight

            divider!!.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider!!.draw(canvas)

        }
    }
}