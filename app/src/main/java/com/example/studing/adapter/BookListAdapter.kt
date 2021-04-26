package com.example.studing.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.studing.R
import com.example.studing.network.VolumeInfo
import kotlinx.android.synthetic.main.recycler_list_row.view.*

class BookListAdapter: RecyclerView.Adapter<BookListAdapter.MyViewHolder>() {
    var bookListData = ArrayList<VolumeInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return bookListData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(bookListData[position])
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.tvTitle
        val tvPublisher = view.tvPublisher
        val tvDescription = view.tvDescription
        val thumbImageView = view.thumbImageView

        fun bind(data: VolumeInfo) {
            tvTitle.text = data.volumeInfo.title
            tvPublisher.text = data.volumeInfo.publisher
            tvDescription.text = data.volumeInfo.description

            val url = data.volumeInfo.imageLinks.smallThumbnail
            Glide.with(thumbImageView)
                .load(url)
                .circleCrop()
                .into(thumbImageView)

        }
    }
}