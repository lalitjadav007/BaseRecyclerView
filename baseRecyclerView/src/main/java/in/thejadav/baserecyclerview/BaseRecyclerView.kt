package `in`.thejadav.baserecyclerview

import BaseRvAdapter
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class BaseRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr),
    DragListener {

    var touchHelperCallback: ItemMoveCallback? = null
    var touchHelper: ItemTouchHelper? = null

    init {

    }

    fun setAdapter(adapter: Adapter<*>?, parentViewId: Int, childViewId: Int){
        setAdapter(adapter)
        touchHelperCallback?.parentId = parentViewId
        touchHelperCallback?.childId = childViewId
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)

        if(adapter is ItemMoveListener){
            touchHelperCallback = ItemMoveCallback(adapter)
            touchHelper = ItemTouchHelper(touchHelperCallback!!)
            touchHelper?.attachToRecyclerView(this)

            if(adapter is BaseRvAdapter<*, *>){
                touchHelperCallback?.useViewForDrag = adapter.handleId != -1
                adapter.dragListener = this
            }
        }
    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        if(e != null){
            touchHelperCallback?.swipeBack = e.action == MotionEvent.ACTION_CANCEL || e.action == MotionEvent.ACTION_UP
            if(e.action == MotionEvent.ACTION_UP){
                performClick()
            }
        }
        return super.onTouchEvent(e)
    }

    override fun startDrag(holder: ViewHolder) {
        touchHelper?.startDrag(holder)
        Log.e("Drag", "drag")
    }
}