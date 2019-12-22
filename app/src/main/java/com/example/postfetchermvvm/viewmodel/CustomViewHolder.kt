package com.example.postfetchermvvm.viewmodel

import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.postfetchermvvm.R

class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvAuthor: TextView = itemView.findViewById(R.id.tv_author_name)
    val lvPostOfAuthor: ListView = itemView.findViewById(R.id.lv_post_titles)

}