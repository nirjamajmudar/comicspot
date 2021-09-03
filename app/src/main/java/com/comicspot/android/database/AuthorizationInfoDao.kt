package com.comicspot.android.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AuthorizationInfoDao {

    @Query("SELECT * FROM auth_info_table")
    fun getAuthInfo(): LiveData<AuthorizationInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(authInfo: AuthorizationInfo)

    @Update
    suspend fun update(authInfo: AuthorizationInfo)

}