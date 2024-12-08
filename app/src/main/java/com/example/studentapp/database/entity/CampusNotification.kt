package com.example.studentapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 校园公告实体类
 * 
 * @property link 通知链接（主键）
 * @property title 通知标题
 * @property date 通知发表日期
 */
@Entity(tableName = "campus_notification")
data class CampusNotification(
    @PrimaryKey
    val link: String,
    val title: String,
    val date: String
)
