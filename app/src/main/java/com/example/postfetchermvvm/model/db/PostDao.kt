package com.example.postfetchermvvm.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostDao {
    @Query("SELECT * from PostData")
    fun getPostData(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPost(postData: PostEntity)
}