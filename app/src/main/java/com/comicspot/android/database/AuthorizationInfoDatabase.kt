package com.comicspot.android.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.comicspot.android.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [AuthorizationInfo::class], version = 1, exportSchema = false)
abstract class AuthorizationInfoDatabase : RoomDatabase() {

    abstract fun authInfoDao(): AuthorizationInfoDao

    private class AuthInfoDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE.let { database ->
                scope.launch {
                    database?.authInfoDao()?.insert(
                        AuthorizationInfo(
                            1, Date().time,
                            Constants.DUMMY_VALUE, Constants.DUMMY_VALUE
                        )
                    )
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthorizationInfoDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AuthorizationInfoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AuthorizationInfoDatabase::class.java,
                    "auth_info_database"
                )
                    .addCallback(AuthInfoDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}