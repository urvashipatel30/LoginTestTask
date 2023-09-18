package com.interview.login.ui.login.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_data")
data class UserDataEntity (
    @PrimaryKey
    val userId: String,
    val userName: String,
    val isDeleted: Boolean,
    val xAcc: String
)