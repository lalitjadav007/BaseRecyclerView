package `in`.thejadav.baserecyclerview

import androidx.recyclerview.widget.RecyclerView

interface DragListener {
    fun startDrag(holder: RecyclerView.ViewHolder)
}