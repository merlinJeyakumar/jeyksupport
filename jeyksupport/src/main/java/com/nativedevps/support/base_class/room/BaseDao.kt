package com.nativedevps.support.base_class.room


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

abstract class BaseDao<T : BaseEntity> {

    abstract val tableName: String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(obj: List<T>): LongArray

    @Update
    abstract fun update(obj: T)

    @Delete
    abstract fun delete(obj: T)

    fun find(key: String, value: String): T {
        val query = SimpleSQLiteQuery(
            "select * from $tableName where $key=?",
            arrayOf(value)
        )
        return doFind(query)
    }

    fun deleteAll() {
        val query = SimpleSQLiteQuery(
            "DELETE FROM $tableName"
        )
        doDeleteAll(query)
    }

    @RawQuery
    protected abstract fun doDeleteAll(query: SimpleSQLiteQuery): Int

    @RawQuery
    protected abstract fun doFind(query: SimpleSQLiteQuery): T


    fun findAllValid(): List<T> {
        val query = SimpleSQLiteQuery(
            "select * from $tableName where is_delete = 0 order by sortKey"
        )
        return doFindAllValid(query)
    }

    fun find(id: Long): T {
        val query = SimpleSQLiteQuery(
            "select * from $tableName where is_delete = 0 and id = ?",
            arrayOf(id)
        )
        return doFind(query)
    }

    @RawQuery
    protected abstract fun doFindAllValid(query: SupportSQLiteQuery): List<T>

    @RawQuery
    protected abstract fun doFind(query: SupportSQLiteQuery): T

    @Transaction
    open fun deleteAndCreate(users: List<T>) {
        deleteAll()
        insertAll(users)
    }
}
