package com.example.studentapp.database.test

import android.content.Context
import android.util.Log
import com.example.studentapp.database.AppDatabase
import com.example.studentapp.database.entity.*
import com.example.studentapp.database.repository.DatabaseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.io.File

private const val TAG = "DatabaseTest"

/**
 * 数据库测试类
 * 用于验证数据库的基本功能
 */
class DatabaseTest(private val context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val repository = DatabaseRepository(context)

    /**
     * 测试校园公告功能
     */
    fun testCampusNotification() = runBlocking {
        try {
            Log.d(TAG, "开始测试校园公告功能...")
            val dao = db.campusNotificationDao()
            
            // 创建测试数据
            val notification = CampusNotification(
                link = "https://www.cau.ac.kr/cms/FR_CON/BoardView.do?MENU_ID=100",
                title = "2025年度奖学金申请公告",
                date = "2024.12.05"
            )

            // 插入数据
            dao.insert(notification)
            Log.d(TAG, "插入校园公告成功")

            // 查询数据
            val notifications = dao.getAllNotifications().first()
            assert(notifications.isNotEmpty()) { "数据库应该包含至少一条校园公告" }
            assert(notifications[0].title == "2025年度奖学金申请公告") { "标题应该匹配" }
            Log.d(TAG, "校园公告查询测试通过")

            // 测试JSON导入
            val jsonFile = File(context.getExternalFilesDir(null), "sample_data/CampusNotification.json")
            if (jsonFile.exists()) {
                repository.importCampusNotifications(jsonFile)
                val importedNotifications = dao.getAllNotifications().first()
                assert(importedNotifications.size > 1) { "应该成功导入JSON数据" }
                Log.d(TAG, "校园公告JSON导入测试通过")
            }
            
            Log.d(TAG, "校园公告功能测试全部通过！")
        } catch (e: Exception) {
            Log.e(TAG, "校园公告测试失败：${e.message}", e)
            throw e
        }
    }

    /**
     * 测试食堂菜单功能
     */
    fun testCanteenMenu() = runBlocking {
        try {
            Log.d(TAG, "开始测试食堂菜单功能...")
            val dao = db.canteenMenuDao()

            // 创建测试数据
            val menu = CanteenMenu(
                date = "2024.12.09",
                canteen = "참슬기식당(310관 B4층)",
                time = "11:00~13:30",
                mealType = "중식(한식)",
                price = "4,000 원"
            )

            // 插入数据
            dao.insert(menu)
            Log.d(TAG, "插入食堂菜单成功")

            // 查询数据
            val menus = dao.getMenusByDate("2024.12.09").first()
            assert(menus.isNotEmpty()) { "数据库应该包含至少一条菜单记录" }
            assert(menus[0].price == "4,000 원") { "价格应该匹配" }
            Log.d(TAG, "食堂菜单查询测试通过")

            // 测试JSON导入
            val jsonFile = File(context.getExternalFilesDir(null), "sample_data/CanteenMenu.json")
            if (jsonFile.exists()) {
                repository.importCanteenMenus(jsonFile)
                val importedMenus = dao.getMenusByDate("2024.12.09").first()
                assert(importedMenus.size > 1) { "应该成功导入JSON数据" }
                Log.d(TAG, "食堂菜单JSON导入测试通过")
            }
            
            Log.d(TAG, "食堂菜单功能测试全部通过！")
        } catch (e: Exception) {
            Log.e(TAG, "食堂菜单测试失败：${e.message}", e)
            throw e
        }
    }

    /**
     * 测试教授信息功能
     */
    fun testProfessor() = runBlocking {
        try {
            Log.d(TAG, "开始测试教授信息功能...")
            val dao = db.professorDao()

            // 创建测试数据
            val professor = Professor(
                imageLink = "https://cse.cau.ac.kr//image/sub/people/hckang.jpg",
                name = "강현철",
                university = "Univ. of Maryland",
                degree = "공학박사",
                courses = "자료구조, 데이타베이스시스템",
                email = "hckang@cau.ac.kr",
                labLink = "https://yy-ko.github.io/"
            )

            // 插入数据
            dao.insert(professor)
            Log.d(TAG, "插入教授信息成功")

            // 查询数据
            val professors = dao.getAllProfessors().first()
            assert(professors.isNotEmpty()) { "数据库应该包含至少一条教授信息" }
            assert(professors[0].name == "강현철") { "教授姓名应该匹配" }
            Log.d(TAG, "教授信息查询测试通过")
            
            Log.d(TAG, "教授信息功能测试全部通过！")
        } catch (e: Exception) {
            Log.e(TAG, "教授信息测试失败：${e.message}", e)
            throw e
        }
    }

    /**
     * 测试作业通知功能
     */
    fun testHomeworkNotification() = runBlocking {
        try {
            Log.d(TAG, "开始测试作业通知功能...")
            val dao = db.homeworkNotificationDao()

            // 创建测试数据
            val homework = HomeworkNotification(
                link = "https://eclass3.cau.ac.kr/courses/111728/assignments/1489463",
                courseName = "모바일 앱 개발 02분반",
                title = "9/24 과제1",
                description = "第一次作业",
                dueDate = "9월 24일 오후 11:59",
                intDueDate = "09242359"
            )

            // 插入数据
            dao.insert(homework)
            Log.d(TAG, "插入作业通知成功")

            // 查询数据
            val homeworks = dao.getAllHomework().first()
            assert(homeworks.isNotEmpty()) { "数据库应该包含至少一条作业通知" }
            assert(homeworks[0].title == "9/24 과제1") { "作业标题应该匹配" }
            Log.d(TAG, "作业通知查询测试通过")
            
            Log.d(TAG, "作业通知功能测试全部通过！")
        } catch (e: Exception) {
            Log.e(TAG, "作业通知测试失败：${e.message}", e)
            throw e
        }
    }

    /**
     * 运行所有测试
     */
    fun runAllTests() {
        try {
            Log.d(TAG, "开始运行所有数据库测试...")
            
            testCampusNotification()
            testCanteenMenu()
            testProfessor()
            testHomeworkNotification()
            
            Log.d(TAG, "所有数据库测试通过！")
        } catch (e: Exception) {
            Log.e(TAG, "数据库测试失败：${e.message}", e)
            throw e
        }
    }
}
