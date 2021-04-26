package com.example.studing

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studing.adapter.BookListAdapter
import com.example.studing.network.BookListModel
import com.example.studing.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewmodel: MainActivityViewModel
    lateinit var bookListAdapter: BookListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSearchBox()
        initRecyclerView()
    }

    fun initSearchBox() {
        inputBookName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loadAPIData(s.toString())
            }
        })
    }

    fun initRecyclerView() {
        recyclerView.apply {
            val layoutManager = LinearLayoutManager(this@MainActivity)
            val decoration = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(decoration)
            bookListAdapter = BookListAdapter()
            recyclerView.layoutManager = layoutManager
            adapter = bookListAdapter
        }
    }

    fun loadAPIData(input: String) {
        viewmodel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewmodel.getBookListObserver().observe(this, Observer<BookListModel> {
            if (it != null) {
                //update adapter...
                bookListAdapter.bookListData = it.items
                bookListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error in feaching data", Toast.LENGTH_LONG).show()
            }
        })
        viewmodel.makeApiCall(input)
    }
}