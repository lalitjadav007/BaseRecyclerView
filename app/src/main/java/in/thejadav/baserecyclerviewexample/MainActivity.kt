package `in`.thejadav.baserecyclerviewexample

import `in`.thejadav.baserecyclerview.ScrollListener
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ExampleStringListener {
    private lateinit var adapter: ExmpleStringAdapter
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ExmpleStringAdapter(R.id.ivHandle, R.id.child, R.id.parent,this)
        val lManager = LinearLayoutManager(this)
        rvExample.layoutManager = lManager
        rvExample.adapter = adapter
        val animator = DefaultItemAnimator()
        rvExample.itemAnimator = animator

        val myList = arrayListOf("Hello", "Good Morning", "How", "Are", "You","Hello", "Good Morning", "How", "Are", "You")
        adapter.replaceAll(myList)

        rvExample.addOnScrollListener(object : ScrollListener(lManager){
            override fun loadMoreItems() {
                Handler().postDelayed(Runnable {
                    adapter.addAll(myList)
                }, 1000)
            }

            override fun getTotalPageCounts(): Int {
                return 10
            }

            override fun isLastPage(): Boolean {
                return false
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })

    }

    override fun deleteItem(s: String) {
        Toast.makeText(this, "Delete $s", Toast.LENGTH_SHORT).show()
    }

    override fun itemDraged() {
        Toast.makeText(this, "Item Draged", Toast.LENGTH_SHORT).show()
    }
}