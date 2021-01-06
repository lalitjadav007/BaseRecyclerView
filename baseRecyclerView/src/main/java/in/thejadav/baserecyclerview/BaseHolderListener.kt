import android.os.Bundle

interface BaseHolderListener {
    fun holderItemClicked(position: Int, viewId: Int? = null, bundle: Bundle? = null)
    fun startDrag(baseViewHolder: BaseViewHolder<*>)
}