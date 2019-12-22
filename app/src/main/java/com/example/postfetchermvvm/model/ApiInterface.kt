package com.example.postfetchermvvm.model

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    //http://jsonplaceholder.typicode.com/posts
    @GET("posts")
//    fun getPosts(): Call<ArrayList<Post>>
    fun getPosts(): Observable<ArrayList<Post>>

    //http://jsonplaceholder.typicode.com/users
    @GET("users")
//    fun getUsers(): Call<ArrayList<User>>
    fun getUsers(): Observable<ArrayList<User>>

    //http://jsonplaceholder.typicode.com/comments
    @GET("comments")
//    fun getComments(): Call<ArrayList<Comment>>
    fun getComments(): Observable<ArrayList<Comment>>

    companion object {
        fun create(): ApiInterface {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }
}