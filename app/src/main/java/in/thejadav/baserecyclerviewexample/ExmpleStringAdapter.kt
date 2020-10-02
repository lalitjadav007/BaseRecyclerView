package `in`.thejadav.baserecyclerviewexample

import BaseRvAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

class ExmpleStringAdapter(handleId: Int, var listener: ExampleStringListener) : BaseRvAdapter<ExampleListHolder, String>(handleId = handleId) {
    override fun holderItemClicked(position: Int, bundle: Bundle?) {
        listener.deleteItem(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleListHolder {
        return ExampleListHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_example, parent, false))
    }
}

interface ExampleStringListener{
    fun deleteItem(s: String)
}