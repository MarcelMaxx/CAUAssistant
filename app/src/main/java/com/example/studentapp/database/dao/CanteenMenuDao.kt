package com.example.studentapp.database.dao

import androidx.room.*
import com.example.studentapp.database.entity.CanteenMenu
import kotlinx.coroutines.flow.Flow

/**
 * 食堂菜单数据访问对象
 */
@Dao
interface CanteenMenuDao {
    /**
     * 插入一条菜单记录
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menu: CanteenMenu)

    /**
     * 批量插入菜单记录
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(menus: List<CanteenMenu>)

    /**
     * 删除一条菜单记录
     */
    @Delete
    suspend fun delete(menu: CanteenMenu)

    /**
     * 获取指定日期的所有菜单
     */
    @Query("SELECT * FROM canteen_menu WHERE date = :date")
    fun getMenusByDate(date: String): Flow<List<CanteenMenu>>

    /**
     * 获取指定餐厅的所有菜单
     */
    @Query("SELECT * FROM canteen_menu WHERE canteen = :canteen ORDER BY date")
    fun getMenusByCanteen(canteen: String): Flow<List<CanteenMenu>>

    /**
     * 获取指定日期和餐厅的菜单
     */
    @Query("SELECT * FROM canteen_menu WHERE date = :date AND canteen = :canteen")
    fun getMenusByDateAndCanteen(date: String, canteen: String): Flow<List<CanteenMenu>>
}
