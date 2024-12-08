package com.example.studentapp.database.dao

import androidx.room.*
import com.example.studentapp.database.entity.Schedule
import kotlinx.coroutines.flow.Flow

/**
 * 课程表数据访问对象
 */
@Dao
interface ScheduleDao {
    /**
     * 插入一条课程表记录
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(schedule: Schedule)

    /**
     * 批量插入课程表记录
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(schedules: List<Schedule>)

    /**
     * 删除一条课程表记录
     */
    @Delete
    suspend fun delete(schedule: Schedule)

    /**
     * 获取所有课程表记录
     */
    @Query("SELECT * FROM schedule ORDER BY days_of_week, start_time")
    fun getAllSchedules(): Flow<List<Schedule>>

    /**
     * 获取指定星期的课程
     */
    @Query("SELECT * FROM schedule WHERE days_of_week = :dayOfWeek ORDER BY start_time")
    fun getSchedulesByDay(dayOfWeek: String): Flow<List<Schedule>>

    /**
     * 获取指定课程的所有课程时间
     */
    @Query("SELECT * FROM schedule WHERE course_name = :courseName ORDER BY days_of_week, start_time")
    fun getSchedulesByCourse(courseName: String): Flow<List<Schedule>>
}
