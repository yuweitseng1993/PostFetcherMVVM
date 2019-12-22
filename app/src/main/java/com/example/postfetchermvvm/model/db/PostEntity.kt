package com.example.postfetchermvvm.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "postData")
data class PostEntity(@PrimaryKey var id: Int,
                    @ColumnInfo(name = "userId") var userId: Int,
                    @ColumnInfo(name = "title") var title: String,
                    @ColumnInfo(name = "body") var body: String) {
    constructor():this(0, 0, "", "")
}