package com.example.postfetchermvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.postfetchermvvm.R
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        val intent = intent
        tv_post_title.text = intent.getStringExtra("postTitle")
        tv_post_author_name.text = intent.getStringExtra("authorName")!!
        tv_post_body.text = intent.getStringExtra("postBody")
        tv_post_comment_num.text = intent.getIntExtra("commentNum", 0).toString()
    }
}