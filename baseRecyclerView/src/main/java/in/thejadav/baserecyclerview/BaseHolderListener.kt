import android.os.Bundle

interface BaseHolderListener {
    fun holderItemClicked(position: Int, bundle: Bundle? = null)
}