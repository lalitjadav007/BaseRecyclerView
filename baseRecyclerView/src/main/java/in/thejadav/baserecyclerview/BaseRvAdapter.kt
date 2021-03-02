package `in`.thejadav.baserecyclerview

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<V: BaseViewHolder<T>, T>(private var hiddenViewId: Int = -1, private  var mainViewId: Int = -1, var handleId: Int = -1, var dragEnabled: Boolean = false) : RecyclerView.Adapter<V>(), BaseHolderListener,
    ItemMoveListener {

    open val list: ArrayList<T> = ArrayList()
    open val filteredList: ArrayList<T> = ArrayList()
    private var searchText = ""
    protected var openedItemPosition = -1

    private var touchHelperCallback: ItemTouchHelperCallback = ItemTouchHelperCallback(this, hiddenViewId, mainViewId)
    private var touchHelper: ItemTouchHelper = ItemTouchHelper(touchHelperCallback)

    init {
        touchHelperCallback.setDragEnable(dragEnabled)
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        bindHolder(holder, position)
    }

//    override fun onBindViewHolder(holder: V, position: Int, payloads: MutableList<Any>) {
//
//    }

    private fun bindHolder(holder: V, position: Int) {
        holder.listener = this
        holder.useViewForDrag(handleId)
        holder.setItem(filteredList[position])
        if(position == openedItemPosition){
            holder.openItem(hiddenViewId, mainViewId)
        } else {
            holder.hideItem(hiddenViewId, mainViewId)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        touchHelper.attachToRecyclerView(recyclerView)
        touchHelperCallback.useViewForDrag = handleId != -1
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    fun replaceAll(newList: ArrayList<T>){
        this.list.clear()
        this.list.addAll(newList)
        getFilteredData()
    }

    private fun getFilteredData() {
        filteredList.clear()
        if(searchText.isEmpty()){
            filteredList.addAll(list)
        } else {
            filteredList.addAll(list.filter { searchCriteria( searchText , it) })
        }
        notifyDataSetChanged()
    }

    open fun searchCriteria(searchText: String, it: T): Boolean{ return true}

    fun addAll(newList: List<T>) {
        addAll(list.size, newList)
        getFilteredData()
    }

    fun addAll(position: Int, newList: List<T>) {
        this.list.addAll(position, newList)
        getFilteredData()
    }

    fun removeAll(newList: List<T>) {
        this.list.removeAll(newList)
        getFilteredData()
    }

    override fun moveItem(oldPosition: Int, newPosition: Int) {
        val item = list[oldPosition]
        list.removeAt(oldPosition)
        list.add(newPosition, item)
        notifyItemMoved(oldPosition, newPosition)
    }

    override fun itemSwiped(position: Int) {
        setItemOpen(position, openedItemPosition != position)

//        if(direction == ItemTouchHelper.START){
//            setItemOpen(position, true)
//        } else {
//            setItemOpen(position, false)
//        }
    }

    fun setItemOpen(adapterPosition: Int, isOpen: Boolean) {
        val oldPosition = openedItemPosition
        openedItemPosition = -1
        notifyItemChanged(oldPosition, null)
        if(isOpen){
            openedItemPosition = adapterPosition
            notifyItemChanged(openedItemPosition, null)
        }
//        notifyDataSetChanged()
    }

    override fun startDrag(baseViewHolder: BaseViewHolder<*>) {
        touchHelper.startDrag(baseViewHolder)
    }

    fun clear(){
        list.clear()
        filteredList.clear()
        notifyDataSetChanged()
    }

    fun search(text: String){
        searchText = text
        getFilteredData()
    }

    fun getOpenPosition(): Int {
        return openedItemPosition
    }

    fun notifyOnly(){
        getFilteredData()
    }

    fun getSearchedText() = searchText

}
