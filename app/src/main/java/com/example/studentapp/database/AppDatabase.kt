package com.example.studentapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentapp.database.dao.*
import com.example.studentapp.database.entity.*

/**
 * 应用数据库类
 * 
 * 这是整个应用的主数据库类，它：
 * 1. 定义了所有的数据库实体
 * 2. 设定数据库版本
 * 3. 提供所有的DAO访问接口
 * 4. 使用单例模式确保数据库实例唯一
 */
@Database(
    entities = [
        CampusNotification::class,
        CanteenMenu::class,
        Professor::class,
        HomeworkNotification::class,
        CourseNotification::class,
        Schedule::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    // 定义所有DAO的抽象获取方法
    abstract fun campusNotificationDao(): CampusNotificationDao
    abstract fun canteenMenuDao(): CanteenMenuDao
    abstract fun professorDao(): ProfessorDao
    abstract fun homeworkNotificationDao(): HomeworkNotificationDao
    abstract fun courseNotificationDao(): CourseNotificationDao
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * 获取数据库实例
         * 使用双重检查锁定模式确保单例
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_app_database"
                )
                    .fallbackToDestructiveMigration() // 在数据库升级时如果没有迁移对象，则重新创建数据库
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
