import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<V: BaseViewHolder<T>, T> : RecyclerView.Adapter<V>(), BaseHolderListener {

    open val list: ArrayList<T> = ArrayList()

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.setListener<V>(this)
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
}