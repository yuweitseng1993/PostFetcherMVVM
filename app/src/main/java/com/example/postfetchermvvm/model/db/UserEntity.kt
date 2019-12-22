package com.example.postfetchermvvm.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userData")
data class UserEntity(@PrimaryKey var id: Int,
                    @ColumnInfo(name = "name") var name: String) {
    constructor():this(0, "")
}