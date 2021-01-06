package `in`.thejadav.baserecyclerviewexample

import BaseViewHolder
import android.graphics.Color
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.item_example.view.*

class ExampleListHolder(view: View) : BaseViewHolder<String>(view) {

    init {
        itemView.ivDelete.setOnClickListener {
            listener?.holderItemClicked(adapterPosition, it.id, Bundle())
        }
    }

    override fun setItem(t: String) {
        itemView.textView.text = t
    }

    override fun itemSelected() {
        super.itemSelected()
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun itemCleared() {
        super.itemCleared()
        itemView.setBackgroundColor(Color.WHITE)
    }
}