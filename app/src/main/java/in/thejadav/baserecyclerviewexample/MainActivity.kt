package `in`.thejadav.baserecyclerviewexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ExampleStringListener {
    private lateinit var adapter: ExmpleStringAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ExmpleStringAdapter(R.id.ivHandle, R.id.child, R.id.parent,this)
        rvExample.layoutManager = LinearLayoutManager(this)
        rvExample.adapter = adapter

        val myList = arrayListOf("Hello", "Good Morning", "How", "Are", "You","Hello", "Good Morning", "How", "Are", "You")
        adapter.replaceAll(myList)
    }

    override fun deleteItem(s: String) {
        Toast.makeText(this, "Delete $s", Toast.LENGTH_SHORT).show()
    }

    override fun itemDraged() {
        Toast.makeText(this, "Item Draged", Toast.LENGTH_SHORT).show()
    }
}