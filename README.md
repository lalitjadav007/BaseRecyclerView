# BaseRecyclerView
This is base recyclerview library.

Step 1. Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.lalitjadav007:BaseRecyclerView:Tag'
	}


Use this in App : 

just extend recyclerview adapter like below:

import BaseRvAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup

class ExmpleStringAdapter : BaseRvAdapter<ExampleListHolder, String>() {
    override fun holderItemClicked(position: Int, bundle: Bundle?) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleListHolder {
        return ExampleListHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_example, parent, false))
    }
}

extend view holder

import BaseViewHolder
import android.view.View

class ExampleListHolder(view: View) : BaseViewHolder<String>(view) {
    override fun setItem(t: String) {
	itemView.textView.text = t
    }
}

assign adapter in activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ExmpleStringAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = ExmpleStringAdapter()
        rvExample.layoutManager = LinearLayoutManager(this)
        rvExample.adapter = adapter

        val myList = arrayListOf("Hello", "Good Morning")
        adapter.replaceAll(myList)
    }
}

All set.





