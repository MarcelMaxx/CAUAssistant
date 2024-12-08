package com.example.studentapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

/**
 * 作业通知实体类
 * 
 * @property link 作业详情链接（主键）
 * @property courseName 课程名称
 * @property title 作业标题
 * @property description 作业描述
 * @property dueDate 提交截止日期
 * @property intDueDate 提交截止日期（用于排序）
 */
@Entity(tableName = "homework_notification")
data class HomeworkNotification(
    @PrimaryKey
    val link: String,
    
    @ColumnInfo(name = "course_name")
    val courseName: String,
    
    val title: String,
    val description: String,
    
    @ColumnInfo(name = "due_date")
    val dueDate: String,
    
    @ColumnInfo(name = "int_due_date")
    val intDueDate: String
)
