package com.example.studentapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.studentapp.database.test.DatabaseTest
import com.example.studentapp.ui.HomePage
import com.example.studentapp.ui.MainScreen
import com.example.studentapp.ui.theme.StudentAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // 在后台线程中运行数据库测试
//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                val databaseTest = DatabaseTest(this@MainActivity)
//                databaseTest.runAllTests()
//                Log.d("DatabaseTest", "数据库测试全部通过！")
//            } catch (e: Exception) {
//                Log.e("DatabaseTest", "数据库测试失败：${e.message}", e)
//            }
//        }

        setContent {
            StudentAppTheme {
                MainScreen()
            }
        }
    }
}