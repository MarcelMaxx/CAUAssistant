package com.example.studentapp.ui


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.browser.customtabs.CustomTabsIntent

import android.content.Intent



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsPage() {
    Column(modifier = Modifier.fillMaxSize()) {

        // 标题栏
        TopAppBar(title = { Text("Notifications") }, backgroundColor = MaterialTheme.colorScheme.primary)

        // 通知内容
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            item {
                SectionTitle(title = "讲义公告")
            }
            items(3) { index ->
                val contentUrl = "https://example.com/lecture_${index + 1}"  // 模拟讲义公告对应的网页链接，实际需替换
                NotificationCard(
                    title = "讲义 ${index + 1}",
                    date = "发布日期：2024-11-${28 + index}",
                    contentUrl = contentUrl
                )
            }

            item {
                SectionTitle(title = "作业提醒")
            }
            items(3) { index ->
                val homeworkUrl = "https://example.com/homework_${index + 1}"  // 模拟作业提醒对应的网页链接，实际需替换
                HomeworkCard(
                    courseName = listOf("软件工程", "数据库设计", "人工智能")[index],
                    details = listOf(
                        "完成项目文档撰写",
                        "设计数据库模式",
                        "完成AI案例研究"
                    )[index],
                    dueDate = "截止日期：2024-12-0${index + 1}",
                    homeworkUrl = homeworkUrl
                )
            }

            // 课程表部分
            item {
                SectionTitle(title = "课程表")
            }
            items(ScheduleDataSource.getScheduleDataList()) { scheduleData ->
                ScheduleCard(
                    courseName = scheduleData.courseName,
                    location = scheduleData.location,
                    time = scheduleData.time
                )
            }
        }

    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge, // 替换 h6
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun NotificationCard(title: String, date: String, contentUrl: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(contentUrl))
                val customTabsIntent = CustomTabsIntent.Builder().build()
                customTabsIntent.launchUrl(context, intent.data!!)
            },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleLarge) // 替换 h6
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium, // 替换 body2
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun HomeworkCard(courseName: String, details: String, dueDate: String, homeworkUrl: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, android.net.Uri.parse(homeworkUrl))
                val customTabsIntent = CustomTabsIntent.Builder().build()
                intent.data?.let { customTabsIntent.launchUrl(context, it) }
            },
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "课程名称：$courseName",
                style = MaterialTheme.typography.titleLarge // 替换 h6
            )
            Text(
                text = "作业详情：$details",
                style = MaterialTheme.typography.bodyLarge // 替换 body1
            )
            Text(
                text = dueDate,
                style = MaterialTheme.typography.bodyMedium, // 替换 body2
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun ScheduleCard(courseName: String, location: String, time: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "课程名称：$courseName",
                style = MaterialTheme.typography.titleLarge // 替换 h6
            )
            Text(
                text = "上课地点：$location",
                style = MaterialTheme.typography.bodyLarge // 替换 body1
            )
            Text(
                text = "上课时间：$time",
                style = MaterialTheme.typography.bodyMedium, // 替换 body2
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
    }
}