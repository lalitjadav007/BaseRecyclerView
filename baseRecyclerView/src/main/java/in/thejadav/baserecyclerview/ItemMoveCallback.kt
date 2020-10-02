package `in`.thejadav.baserecyclerview

import BaseViewHolder
import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView

class ItemMoveCallback(private val listener: ItemMoveListener, var useViewForDrag: Boolean = false) : ItemTouchHelper.Callback() {

    var swipeBack = true
    var parentId = 1
    var childId = 2

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = UP or DOWN

        val parentView = (viewHolder.itemView.findViewById<View>(parentId))
        val childView = (viewHolder.itemView.findViewById<View>(childId))

        if(parentView == null || childView == null) return makeMovementFlags(dragFlags, 0)
        val swipeFlags = if(parentView.x + parentView.width == childView.x){
            makeFlag(ACTION_STATE_IDLE, END)
        } else {
            makeFlag(ACTION_STATE_IDLE, START)
        }
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        listener.moveItem(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.itemSwiped(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ACTION_STATE_IDLE){
            if(viewHolder is BaseViewHolder<*>){
                viewHolder.itemSelected()
            }
        }

        if(actionState == ACTION_STATE_DRAG){
            viewHolder?.itemView?.rotation = 2f
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if(viewHolder is BaseViewHolder<*>){
            viewHolder.itemCleared()
            viewHolder.itemView.rotation = 0f
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if(actionState == ACTION_STATE_SWIPE){
            val parentView = (viewHolder.itemView.findViewById<View>(parentId))
            val childView = (viewHolder.itemView.findViewById<View>(childId))

            if(parentView == null || childView == null){
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                return
            }
            Log.e("parent -> child", "" + (parentView.x + parentView.width).toString() + " -> " + childView.x + " -> " + dX)
            if(parentView.x + parentView.width > childView.x && dX <= childView.x){
                parentView.translationX = dX
                if(parentView.x + parentView.width < childView.x){
                    parentView.x = childView.x - parentView.width
                }
            } else {

            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return !useViewForDrag
    }
}

interface ItemMoveListener{
    fun moveItem(oldPosition: Int, newPosition: Int)
    fun itemSwiped(position: Int)
}