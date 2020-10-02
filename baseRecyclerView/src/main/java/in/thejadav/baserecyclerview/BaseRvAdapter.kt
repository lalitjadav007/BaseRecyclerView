import `in`.thejadav.baserecyclerview.DragListener
import `in`.thejadav.baserecyclerview.ItemMoveListener
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<V: BaseViewHolder<T>, T>(var dragListener: DragListener? = null, var handleId: Int = -1) : RecyclerView.Adapter<V>(), BaseHolderListener,
    ItemMoveListener {

    open val list: ArrayList<T> = ArrayList()

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.listener = this
        holder.useViewForDrag(handleId)
        holder.setItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun replaceAll(newList: ArrayList<T>){
        this.list.clear()
        this.list.addAll(newList)
        notifyDataSetChanged()
    }

    fun addAll(newList: List<T>) {
        addAll(list.size, newList)
    }

    fun addAll(position: Int, newList: List<T>) {
        this.list.addAll(position, newList)
        notifyDataSetChanged()
    }

    fun removeAll(newList: List<T>) {
        this.list.removeAll(newList)
        notifyDataSetChanged()
    }

    override fun moveItem(oldPosition: Int, newPosition: Int) {
        val item = list[oldPosition]
        list.removeAt(oldPosition)
        list.add(newPosition, item)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun itemSwiped(position: Int) {
        notifyDataSetChanged()
    }

    override fun startDrag(baseViewHolder: BaseViewHolder<*>) {
        dragListener?.startDrag(baseViewHolder)
    }
}