package com.example.studentapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 教授信息实体类
 * 
 * @property imageLink 头像链接（主键）
 * @property name 教授姓名
 * @property university 毕业院校
 * @property degree 学位
 * @property courses 开设课程
 * @property email 邮箱地址
 * @property labLink 实验室链接
 */
@Entity(tableName = "professor")
data class Professor(
    @PrimaryKey
    val imageLink: String,
    val name: String,
    val university: String,
    val degree: String,
    val courses: String,
    val email: String,
    val labLink: String
)
