package com.example.studentapp.database.entity

import androidx.room.Entity
import androidx.room.ColumnInfo

/**
 * 课程表实体类
 * 
 * @property courseName 课程名称（主键的一部分）
 * @property daysOfWeek 上课星期（主键的一部分）
 * @property classroom 上课教室
 * @property startTime 上课时间（主键的一部分）
 * @property endTime 下课时间
 */
@Entity(
    tableName = "schedule",
    primaryKeys = ["course_name", "days_of_week", "start_time"]
)
data class Schedule(
    @ColumnInfo(name = "course_name")
    val courseName: String,
    
    @ColumnInfo(name = "days_of_week")
    val daysOfWeek: String,
    
    val classroom: String,
    
    @ColumnInfo(name = "start_time")
    val startTime: String,
    
    @ColumnInfo(name = "end_time")
    val endTime: String
)
