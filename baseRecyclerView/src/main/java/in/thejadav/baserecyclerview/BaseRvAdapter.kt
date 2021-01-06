package `in`.thejadav.baserecyclerview

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<V: BaseViewHolder<T>, T>(private var hiddenViewId: Int = -1, private  var mainViewId: Int = -1, var handleId: Int = -1, var dragEnabled: Boolean = false) : RecyclerView.Adapter<V>(), BaseHolderListener,
    ItemMoveListener {

    open val list: ArrayList<T> = ArrayList()
    protected var openedItemPosition = -1
    private var changeView = false

    private var touchHelperCallback: ItemTouchHelperCallback = ItemTouchHelperCallback(this, hiddenViewId, mainViewId)
    private var touchHelper: ItemTouchHelper = ItemTouchHelper(touchHelperCallback)

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.listener = this
        touchHelperCallback.setDragEnable(dragEnabled)
        holder.useViewForDrag(handleId)
        holder.setItem(list[position])
        if(changeView){
            if(position == openedItemPosition){
                holder.openItem(hiddenViewId, mainViewId)
            } else {
                holder.hideItem(hiddenViewId, mainViewId)
            }
        } else {
            //DO NOTHING
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        touchHelper.attachToRecyclerView(recyclerView)
        touchHelperCallback.useViewForDrag = handleId != -1
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

    override fun itemSwiped(position: Int, direction: Int) {
        changeView = true
        if(direction == ItemTouchHelper.RIGHT){
            setItemOpen(position, false)
        } else {
            setItemOpen(position, true)
        }
    }

    fun setItemOpen(adapterPosition: Int, isOpen: Boolean) {
        openedItemPosition = if(isOpen){
            adapterPosition
        } else {
            -1
        }
        notifyDataSetChanged()
    }

    override fun startDrag(baseViewHolder: BaseViewHolder<*>) {
        touchHelper.startDrag(baseViewHolder)
    }

    fun getOpenPosition(): Int {
        return openedItemPosition
    }
}
