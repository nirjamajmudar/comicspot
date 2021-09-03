package com.comicspot.android.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_info_table")
class AuthorizationInfo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "timeStamp") val ts: Long,
    @ColumnInfo(name = "publicKey") val publicKey: String,
    @ColumnInfo(name = "hashValue") val hashValue: String
) {
}