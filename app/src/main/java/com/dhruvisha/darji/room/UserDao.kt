package com.dhruvisha.darji.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Query("SELECT * FROM UserData")
    fun getAll(): List<UserData>

    @Query("SELECT * FROM UserData WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserData>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): UserData

    @Insert
    fun insertAll(vararg users: List<UserData>)

    @Delete
    fun delete(user: UserData)
}