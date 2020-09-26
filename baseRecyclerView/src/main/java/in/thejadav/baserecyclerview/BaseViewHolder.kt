import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    private var listener: BaseHolderListener? = null

    fun <V> setListener(listener: BaseHolderListener) : Unit {
        this.listener = listener
    }

    abstract fun setItem(t: T)

    init {
        itemView.setOnClickListener {
            listener?.holderItemClicked(adapterPosition)
        }
    }
}