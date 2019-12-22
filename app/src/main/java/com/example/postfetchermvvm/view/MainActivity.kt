package com.example.postfetchermvvm.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Pair
import com.example.postfetchermvvm.R
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.postfetchermvvm.model.IndividualPostInfo
import com.example.postfetchermvvm.model.Post
import com.example.postfetchermvvm.model.db.DataBase
import com.example.postfetchermvvm.viewmodel.CustomAdapter
import com.example.postfetchermvvm.viewmodel.CustomViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var mDb: DataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDb = DataBase.getDataBase(this)
        rvContainer.layoutManager = LinearLayoutManager(this)

        val viewModel = ViewModelProvider(this)
            .get(CustomViewModel::class.java)
        mDb?.let { viewModel.setDB(it) }

        if(verifyAvailableNetwork(this)){
            System.out.println("there is internet")
            viewModel.loadPosts()
            viewModel.loadUsers()
            viewModel.loadComments()
        }
        else{
            System.out.println("there's no internet")
            viewModel.getDataFromRoom()
        }

        viewModel.getDetailPosts()!!.observe(this,
            Observer{
                val adapter = CustomAdapter(this, it)
                rvContainer.adapter = adapter
            })
    }

    fun verifyAvailableNetwork(activity:AppCompatActivity):Boolean{
        val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

}

