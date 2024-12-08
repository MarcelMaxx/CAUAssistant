package com.example.studentapp.database.entity

import androidx.room.Entity

/**
 * 食堂菜单实体类
 * 
 * @property date 菜单日期（主键的一部分）
 * @property canteen 餐厅位置（主键的一部分）
 * @property time 营业时间
 * @property mealType 餐食类别（主键的一部分）
 * @property price 价格
 */
@Entity(
    tableName = "canteen_menu",
    primaryKeys = ["date", "canteen", "mealType"]
)
data class CanteenMenu(
    val date: String,
    val canteen: String,
    val time: String,
    val mealType: String,
    val price: String
)
