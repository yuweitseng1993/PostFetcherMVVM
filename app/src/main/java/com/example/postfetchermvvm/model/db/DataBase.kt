package com.example.postfetchermvvm.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PostEntity::class, UserEntity::class, CommentEntity::class], version = 1)
abstract class DataBase : RoomDatabase(){
    abstract fun postDao(): PostDao
    abstract fun userDao(): UserDao
    abstract fun commentDao(): CommentDao

    companion object{
        var INSTANCE: DataBase? = null

        fun getDataBase(context: Context): DataBase? {
            if (INSTANCE == null){
                synchronized(DataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, DataBase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}