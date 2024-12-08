package com.example.studentapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * 课程公告实体类
 * 
 * @property link 公告链接（主键）
 * @property courseName 课程名称
 * @property title 公告标题
 * @property date 公告日期
 * @property intDate 公告日期（用于排序）
 */
@Entity(tableName = "course_notification")
data class CourseNotification(
    @PrimaryKey
    val link: String,
    
    @ColumnInfo(name = "course_name")
    val courseName: String,
    
    val title: String,
    
    val date: String,
    
    @ColumnInfo(name = "int_date")
    val intDate: Long
)
