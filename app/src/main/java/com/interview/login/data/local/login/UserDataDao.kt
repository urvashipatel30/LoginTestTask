package com.interview.login.data.local.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.interview.login.ui.login.entity.UserDataEntity

@Dao
interface UserDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserData(userData: UserDataEntity)
}