package com.example.studentapp.database.dao

import androidx.room.*
import com.example.studentapp.database.entity.CampusNotification
import kotlinx.coroutines.flow.Flow

/**
 * 校园公告数据访问对象
 */
@Dao
interface CampusNotificationDao {
    /**
     * 插入一条校园公告
     * 如果已存在相同link的公告，则替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: CampusNotification)

    /**
     * 批量插入校园公告
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<CampusNotification>)

    /**
     * 删除一条校园公告
     */
    @Delete
    suspend fun delete(notification: CampusNotification)

    /**
     * 获取所有校园公告
     * 使用Flow实现响应式查询
     */
    @Query("SELECT * FROM campus_notification ORDER BY date DESC")
    fun getAllNotifications(): Flow<List<CampusNotification>>

    /**
     * 根据日期范围查询校园公告
     */
    @Query("SELECT * FROM campus_notification WHERE date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getNotificationsByDateRange(startDate: String, endDate: String): Flow<List<CampusNotification>>

    /**
     * 根据标题关键词搜索校园公告
     */
    @Query("SELECT * FROM campus_notification WHERE title LIKE '%' || :keyword || '%' ORDER BY date DESC")
    fun searchNotificationsByKeyword(keyword: String): Flow<List<CampusNotification>>
}
