package com.example.postfetchermvvm.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.postfetchermvvm.R
import com.example.postfetchermvvm.model.IndividualPostInfo
import com.example.postfetchermvvm.view.PostDetailActivity
import java.util.ArrayList

class CustomAdapter(private val context: Context, val postDetailList: List<Pair<String, List<IndividualPostInfo>>>) : RecyclerView.Adapter<CustomViewHolder>(){
//    private var postDetailList: List<Pair<String, List<IndividualPostInfo>>>? = null
    private var titleList: MutableList<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.posts_by_author, parent, false))
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        System.out.println("onBindViewHolder")
        System.out.println("postDetailList element -> " + postDetailList!![position].first)
        val postCategory = postDetailList!![position]
        titleList = ArrayList()
        for (i in postCategory.second.indices) {
            postCategory.second[i].postTitle?.let { titleList!!.add(it) }
        }

        holder.tvAuthor.text = postCategory.first
        val adapter = ArrayAdapter(context, R.layout.listview_item, titleList!!)
        holder.lvPostOfAuthor.adapter = adapter
        holder.lvPostOfAuthor.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, l ->
            val n = Intent(context, PostDetailActivity::class.java)
            n.putExtra("postTitle", postCategory.second[position].postTitle)
            n.putExtra("authorName", postCategory.second[position].authorName)
            n.putExtra("postBody", postCategory.second[position].postBody)
            n.putExtra("commentNum", postCategory.second[position].postCommentNum)
            context.startActivity(n)
        }
    }

    override fun getItemCount(): Int {
        return if (postDetailList != null) postDetailList!!.size else 0
    }

    companion object {
        private val TAG = "PostDetailCustomAdapter"
    }
}
