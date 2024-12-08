package com.example.studentapp.database.repository

import android.content.Context
import com.example.studentapp.database.AppDatabase
import com.example.studentapp.database.entity.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import com.squareup.moshi.Types
import java.lang.reflect.Type

/**
 * 数据仓库类
 * 
 * 这个类是整个数据库模块的核心，它：
 * 1. 提供统一的数据访问接口
 * 2. 处理JSON文件的导入
 * 3. 管理数据的缓存和更新
 * 4. 提供数据查询接口
 */
class DatabaseRepository(private val context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    // 校园公告相关操作
    fun getAllCampusNotifications(): Flow<List<CampusNotification>> {
        return database.campusNotificationDao().getAllNotifications()
    }

    suspend fun importCampusNotifications(jsonFile: File) {
        withContext(Dispatchers.IO) {
            val type: Type = Types.newParameterizedType(List::class.java, CampusNotification::class.java)
            val adapter = moshi.adapter<List<CampusNotification>>(type)
            val notifications = adapter.fromJson(jsonFile.readText())
            notifications?.let {
                database.campusNotificationDao().insertAll(it)
            }
        }
    }

    // 食堂菜单相关操作
    fun getCanteenMenusByDate(date: String): Flow<List<CanteenMenu>> {
        return database.canteenMenuDao().getMenusByDate(date)
    }

    suspend fun importCanteenMenus(jsonFile: File) {
        withContext(Dispatchers.IO) {
            val type: Type = Types.newParameterizedType(List::class.java, CanteenMenu::class.java)
            val adapter = moshi.adapter<List<CanteenMenu>>(type)
            val menus = adapter.fromJson(jsonFile.readText())
            menus?.let {
                database.canteenMenuDao().insertAll(it)
            }
        }
    }

    // 教授信息相关操作
    fun getAllProfessors(): Flow<List<Professor>> {
        return database.professorDao().getAllProfessors()
    }

    suspend fun importProfessors(jsonFile: File) {
        withContext(Dispatchers.IO) {
            val type: Type = Types.newParameterizedType(List::class.java, Professor::class.java)
            val adapter = moshi.adapter<List<Professor>>(type)
            val professors = adapter.fromJson(jsonFile.readText())
            professors?.let {
                database.professorDao().insertAll(it)
            }
        }
    }

    // 作业通知相关操作
    fun getAllHomeworkNotifications(): Flow<List<HomeworkNotification>> {
        return database.homeworkNotificationDao().getAllHomework()
    }

    suspend fun importHomeworkNotifications(jsonFile: File) {
        withContext(Dispatchers.IO) {
            val type: Type = Types.newParameterizedType(List::class.java, HomeworkNotification::class.java)
            val adapter = moshi.adapter<List<HomeworkNotification>>(type)
            val homeworks = adapter.fromJson(jsonFile.readText())
            homeworks?.let {
                database.homeworkNotificationDao().insertAll(it)
            }
        }
    }

    // 课程公告相关操作
    fun getAllCourseNotifications(): Flow<List<CourseNotification>> {
        return database.courseNotificationDao().getAllNotifications()
    }

    suspend fun importCourseNotifications(jsonFile: File) {
        withContext(Dispatchers.IO) {
            val type: Type = Types.newParameterizedType(List::class.java, CourseNotification::class.java)
            val adapter = moshi.adapter<List<CourseNotification>>(type)
            val notifications = adapter.fromJson(jsonFile.readText())
            notifications?.let {
                database.courseNotificationDao().insertAll(it)
            }
        }
    }

    // 课程表相关操作
    fun getAllSchedules(): Flow<List<Schedule>> {
        return database.scheduleDao().getAllSchedules()
    }

    suspend fun importSchedules(jsonFile: File) {
        withContext(Dispatchers.IO) {
            val type: Type = Types.newParameterizedType(List::class.java, Schedule::class.java)
            val adapter = moshi.adapter<List<Schedule>>(type)
            val schedules = adapter.fromJson(jsonFile.readText())
            schedules?.let {
                database.scheduleDao().insertAll(it)
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DatabaseRepository? = null

        fun getInstance(context: Context): DatabaseRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = DatabaseRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}
