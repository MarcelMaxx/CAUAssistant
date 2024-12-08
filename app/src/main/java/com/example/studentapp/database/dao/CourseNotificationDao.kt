package com.example.studentapp.database.dao

import androidx.room.*
import com.example.studentapp.database.entity.CourseNotification
import kotlinx.coroutines.flow.Flow

/**
 * 课程公告数据访问对象
 */
@Dao
interface CourseNotificationDao {
    /**
     * 插入一条课程公告
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notification: CourseNotification)

    /**
     * 批量插入课程公告
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(notifications: List<CourseNotification>)

    /**
     * 删除一条课程公告
     */
    @Delete
    suspend fun delete(notification: CourseNotification)

    /**
     * 获取所有课程公告，按时间排序
     */
    @Query("SELECT * FROM course_notification ORDER BY int_date DESC")
    fun getAllNotifications(): Flow<List<CourseNotification>>

    /**
     * 获取指定课程的公告
     */
    @Query("SELECT * FROM course_notification WHERE course_name = :courseName ORDER BY int_date DESC")
    fun getNotificationsByCourse(courseName: String): Flow<List<CourseNotification>>

    /**
     * 获取最近的公告（最近7天）
     */
    @Query("SELECT * FROM course_notification WHERE int_date >= :startDate ORDER BY int_date DESC")
    fun getRecentNotifications(startDate: Long): Flow<List<CourseNotification>>
}
