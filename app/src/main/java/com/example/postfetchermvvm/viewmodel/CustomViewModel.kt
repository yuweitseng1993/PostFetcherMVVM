package com.example.postfetchermvvm.viewmodel

import android.util.Log
import android.util.Pair
import android.widget.Toast
import java.util.ArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.postfetchermvvm.model.Comment
import com.example.postfetchermvvm.model.IndividualPostInfo
import com.example.postfetchermvvm.model.Post
import com.example.postfetchermvvm.model.User
import com.example.postfetchermvvm.model.ApiInterface
import com.example.postfetchermvvm.model.db.CommentEntity
import com.example.postfetchermvvm.model.db.DataBase
import com.example.postfetchermvvm.model.db.PostEntity
import com.example.postfetchermvvm.model.db.UserEntity
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CustomViewModel : ViewModel() {
    private var userIdName: MutableList<Pair<Int, String>>? = null
    private var localPosts: List<Post>? = null
    private var postIdCommentNum: MutableList<Int>? = null
    private var postDetailByAuthor: MutableLiveData<ArrayList<Pair<String, List<IndividualPostInfo>>>>? = null
    private var tempList: MutableList<Pair<String, List<IndividualPostInfo>>>? = null
    private var mDb: DataBase? = null
    var post: List<Post>? = null
    var user: List<User>? = null
    var comment: List<Comment>? = null

    init{
        postDetailByAuthor = MutableLiveData()
        tempList = ArrayList()
    }

    fun getDetailPosts(): MutableLiveData<ArrayList<Pair<String, List<IndividualPostInfo>>>>? {
        System.out.println("getDetailPosts")
//            setPostDetail()
//            postDetailByAuthor = MutableLiveData()
//            loadPosts()
//            loadUsers()
//            loadComments()
            return postDetailByAuthor
        }

    fun setDB(db: DataBase) {
        mDb = db
    }

    fun getDataFromRoom() {
        System.out.println("getDataFromRoom")
        post = ArrayList()
        user = ArrayList()
        comment = ArrayList()
        val thread = Thread {
            val postData = mDb?.postDao()?.getPostData()
            val userData = mDb?.userDao()?.getUserData()
            val commentData = mDb?.commentDao()?.getCommentData()
            if(postData == null || postData.isEmpty() || userData == null || userData.isEmpty() || commentData == null || commentData.isEmpty()){
                System.out.println("Sorry, part of the data required is missing from the database,\n please connect to the internet to fetch the data.")
            }
            else{
                for(p in postData){
                    (post as ArrayList<Post>).add(Post(p.userId, p.id, p.title, p.body))
                }
                for(u in userData){
                    (user as ArrayList<User>).add(User(u.id, u.name))
                }
                for(c in commentData){
                    (comment as ArrayList<Comment>).add(Comment(c.postId, c.id))
                }
                onPostDataSuccess(post as ArrayList<Post>, false)
                onUserDataSuccess(user as ArrayList<User>, false)
                onCommentDataSuccess(comment as ArrayList<Comment>, false)
            }
        }
        thread.start()
        setPostDetail()
    }

    fun loadPosts() {
        System.out.println("loadPosts()")
        ApiInterface.create().getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<Post>> {

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(posts: ArrayList<Post>) {
                System.out.println("loadPosts onNext")
//                postList!!.setValue(posts)
                onPostDataSuccess(posts, true)
            }

            override fun onError(e: Throwable) {
                Log.d(TAG, "onError: " + e.message)
            }

            override fun onComplete() {

            }
        })
    }

    fun loadUsers(){
        System.out.println("loadUsers()")
        ApiInterface.create().getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<User>> {

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(users: ArrayList<User>) {
//                    userList!!.setValue(users)
                    onUserDataSuccess(users, true)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {

                }
            })
    }

    fun loadComments(){
        System.out.println("loadComments()")
        ApiInterface.create().getComments()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ArrayList<Comment>> {

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(comments: ArrayList<Comment>) {
//                    commentList!!.setValue(comments)
                    onCommentDataSuccess(comments, true)
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: " + e.message)
                }

                override fun onComplete() {

                }
            })
    }

    fun onPostDataSuccess(posts: List<Post>, insertPost: Boolean) {
        System.out.println("onPostDataSuccess")
        localPosts = posts
        if(insertPost){
            val thread = Thread {
                for(l in localPosts!!){
                    var postData = PostEntity()
                    postData.userId = l.userId
                    postData.id = l.id
                    postData.title = l.title
                    postData.body = l.body
                    mDb?.postDao()?.insertPost(postData)
                }
            }
            thread.start()
        }
    }

    fun onUserDataSuccess(users: List<User>, insertUser: Boolean) {
        System.out.println("onUserDataSuccess")
        userIdName = ArrayList()
        if (insertUser) {
            val thread = Thread {
                for (u in users) {
                    userIdName!!.add(Pair(u.id, u.name))
                    var userData = UserEntity()
                    userData.id = u.id
                    userData.name = u.name
                    mDb?.userDao()?.insertUser(userData)
                }
            }
            thread.start()
        } else {
            for (u in users) {
                userIdName!!.add(Pair(u.id, u.name))
            }
        }
    }

    fun onCommentDataSuccess(comments: List<Comment>, insertComment: Boolean) {
        System.out.println("onCommentDataSuccess")
        postIdCommentNum = ArrayList()
//        tempList = ArrayList()
        var indicator = comments[0].postId
        var counter = 0
        if(insertComment){
            for (c in comments) {
                if (c.postId == indicator) {
                    counter++
                } else {
                    postIdCommentNum!!.add(counter)
                    indicator = c.postId
                    counter = 1
                }
                val thread = Thread {
                    var commentData = CommentEntity()
                    commentData.id = c.id
                    commentData.postId = c.postId
                    mDb?.commentDao()?.insertComment(commentData)
                }
                thread.start()
            }
            postIdCommentNum!!.add(counter)
        }
        else{
            for (c in comments) {
                if (c.postId == indicator) {
                    counter++
                } else {
                    postIdCommentNum!!.add(counter)
                    indicator = c.postId
                    counter = 1
                }
            }
            postIdCommentNum!!.add(counter)

        }

        var detailList: MutableList<IndividualPostInfo> = ArrayList()
        for (pi in userIdName!!) {
            for (p in localPosts!!) {
                if (pi.first == p.userId) {
                    detailList.add(IndividualPostInfo(p.title, p.body, pi.second, postIdCommentNum!![p.id!! - 1]))
                }
            }
            tempList!!.add(Pair(pi.second, detailList))
            detailList = ArrayList()
        }
        System.out.println("tempList: -> " + tempList!!.size)
        if(insertComment){
            setPostDetail()
        }
    }

    fun setPostDetail(){
        postDetailByAuthor!!.value = tempList as ArrayList<Pair<String, List<IndividualPostInfo>>>
    }

    companion object {
        private val TAG = "CustomViewModel"
    }
}
