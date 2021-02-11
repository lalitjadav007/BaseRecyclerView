package `in`.thejadav.baserecyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/*
*  Currently available for only LinearLayoutManager
* */
abstract class ScrollListener(private val layoutManager: RecyclerView.LayoutManager): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleCount = layoutManager.childCount
        val totalItems = layoutManager.itemCount

        val firstVisibleIndex = if(layoutManager is LinearLayoutManager){
             layoutManager.findFirstCompletelyVisibleItemPosition()
        } else {
            0
        }

        if(!isLoading() && !isLastPage()){
            if ((visibleCount + firstVisibleIndex) >= totalItems && firstVisibleIndex >= 0){
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    public abstract fun getTotalPageCounts() : Int
    public abstract fun isLastPage() : Boolean
    public abstract fun isLoading(): Boolean
}