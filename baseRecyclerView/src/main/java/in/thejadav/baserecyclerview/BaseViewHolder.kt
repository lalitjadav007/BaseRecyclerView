package `in`.thejadav.baserecyclerview
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    var listener: BaseHolderListener? = null

    abstract fun setItem(t: T)

    fun useViewForDrag(viewId: Int){
        if(viewId == -1) return
        itemView.findViewById<View>(viewId).setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                    listener?.startDrag(this)
                }

                MotionEvent.ACTION_UP -> {
                    view.performClick()
                }
            }
            false
        }
    }

    init {
        itemView.setOnClickListener {
            listener?.holderItemClicked(adapterPosition)
        }
    }

    open fun itemCleared(){

    }

    open fun itemSelected(){

    }

    fun openItem(hiddenViewId: Int, mainViewId: Int) {
        if(hiddenViewId == -1 || mainViewId == -1) return
        val mainView = itemView.findViewById<View>(mainViewId)
        val hiddenView = itemView.findViewById<View>(hiddenViewId)
        mainView.post {
            mainView.x = hiddenView.x - mainView.width - hiddenView.marginStart
        }
    }

    fun hideItem(hiddenViewId: Int, mainViewId: Int) {
        if(hiddenViewId == -1 || mainViewId == -1) return
        val mainView = itemView.findViewById<View>(mainViewId)
        mainView.post {
            mainView.x = mainView.marginStart.toFloat()
        }
    }
}