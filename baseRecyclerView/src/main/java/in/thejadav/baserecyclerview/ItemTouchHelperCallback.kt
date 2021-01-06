package  `in`.thejadav.baserecyclerview

import android.graphics.Canvas
import android.view.View
import androidx.core.view.marginStart
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(private val listener: ItemMoveListener, private var hiddenViewId: Int, private  var mainViewId: Int, var useViewForDrag: Boolean = false) : ItemTouchHelper.Callback() {

    var moveStarted = -1
    private var dragEnabled = false

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = if (dragEnabled) {
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        } else {
            0
        }

        val adapter = recyclerView.adapter
        var openPosition = -1

        if (adapter is BaseRvAdapter<*,*>) {
            openPosition = adapter.getOpenPosition()
        }

        val swapFlags = if (viewHolder.adapterPosition == openPosition) {
            ItemTouchHelper.RIGHT
        } else {
            ItemTouchHelper.LEFT
        }
        return makeMovementFlags(dragFlags, swapFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        moveStarted = viewHolder.adapterPosition
        listener.moveItem(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.itemSwiped(viewHolder.adapterPosition, direction)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder is BaseViewHolder<*>){
                viewHolder.itemSelected()
            }
        }

        if(actionState == ItemTouchHelper.ACTION_STATE_DRAG){
            viewHolder?.itemView?.rotation = 2f
        }

        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (moveStarted != -1) {
            listener.itemMoved(viewHolder.adapterPosition)
            moveStarted = -1
        }

        if(viewHolder is BaseViewHolder<*>){
            viewHolder.itemView.rotation = 0f
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val hiddenView = viewHolder.itemView.findViewById<View>(hiddenViewId)
        val mainView = viewHolder.itemView.findViewById<View>(mainViewId)

        if(hiddenView == null || mainView == null){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            mainView.translationX = dX
            val adapter = recyclerView.adapter

            if (adapter is BaseRvAdapter<*,*>) {

                if (mainView.x + mainView.width > hiddenView.x + hiddenView.width) {
                    mainView.x = mainView.marginStart.toFloat()
                }

                if (mainView.x + mainView.width < hiddenView.x) {
                    mainView.x = hiddenView.x - mainView.width - hiddenView.marginStart
                }
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return !useViewForDrag
    }

    fun setDragEnable(b: Boolean) {
        dragEnabled = b
    }
}