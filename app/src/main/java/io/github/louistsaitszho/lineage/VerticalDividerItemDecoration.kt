package io.github.louistsaitszho.lineage

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * A very simple divider for recyclerview
 * Courtesy of http://stackoverflow.com/a/38984354/2384934
 * Created by louistsai on 29.11.17.
 */
class VerticalDividerItemDecoration(context: Context, dpMarginDimen: Int): RecyclerView.ItemDecoration() {
    private val marginPx = context.resources.getDimension(dpMarginDimen)

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val position = parent?.getChildLayoutPosition(view)
        if (position == 0) {
            outRect?.top = marginPx.toInt()
        }
        outRect?.left = marginPx.toInt()
        outRect?.right = marginPx.toInt()
        outRect?.bottom = marginPx.toInt()
    }
}