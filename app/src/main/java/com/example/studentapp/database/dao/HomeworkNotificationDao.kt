package com.example.studentapp.database.dao

import androidx.room.*
import com.example.studentapp.database.entity.HomeworkNotification
import kotlinx.coroutines.flow.Flow

/**
 * 作业通知数据访问对象
 */
@Dao
interface HomeworkNotificationDao {
    /**
     * 插入一条作业通知
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(homework: HomeworkNotification)

    /**
     * 批量插入作业通知
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(homeworks: List<HomeworkNotification>)

    /**
     * 删除一条作业通知
     */
    @Delete
    suspend fun delete(homework: HomeworkNotification)

    /**
     * 获取所有作业通知，按截止日期排序
     */
    @Query("SELECT * FROM homework_notification ORDER BY int_due_date ASC")
    fun getAllHomework(): Flow<List<HomeworkNotification>>

    /**
     * 获取指定课程的作业通知
     */
    @Query("SELECT * FROM homework_notification WHERE course_name = :courseName ORDER BY int_due_date ASC")
    fun getHomeworkByCourse(courseName: String): Flow<List<HomeworkNotification>>

    /**
     * 获取即将截止的作业（未来7天内）
     */
    @Query("SELECT * FROM homework_notification WHERE int_due_date BETWEEN :startDate AND :endDate ORDER BY int_due_date ASC")
    fun getUpcomingHomework(startDate: String, endDate: String): Flow<List<HomeworkNotification>>
}
