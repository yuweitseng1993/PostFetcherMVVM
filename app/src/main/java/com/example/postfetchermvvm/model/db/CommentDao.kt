package com.example.postfetchermvvm.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {
    @Query("SELECT * from commentData")
    fun getCommentData(): List<CommentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComment(commentData: CommentEntity)
}