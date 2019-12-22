package com.example.postfetchermvvm.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commentData")
data class CommentEntity(@PrimaryKey var id: Int,
                       @ColumnInfo(name = "postId") var postId: Int) {
    constructor():this(0,0)
}