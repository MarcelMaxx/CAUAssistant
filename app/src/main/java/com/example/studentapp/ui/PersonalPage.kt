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
import androidx.compose.foundation.background

import com.example.studentapp.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalPage() {
    Column(modifier = Modifier.fillMaxSize()) {

        // Title bar
        TopAppBar(
            title = { Text("개인", color = AppColors.OnPrimary) }, 
            backgroundColor = AppColors.Primary
        )

        // Notification content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(AppColors.Background)
        ) {
            item {
                SectionTitle(title = "강의 공지")
            }
            items(3) { index ->
                NotificationCard(
                    title = listOf(
                        "고급 IoT 프로젝트 | 2차 동료평가 안내",
                        "휴먼인터페이스미디어 | 눈이 많이 옵니다",
                        "모바일 앱 개발 | 팀별 프로젝트 추진 수업 관련 공지"
                    )[index],
                    date = listOf(
                        "11월 26일 오후 6:59",
                        "11월 27일 오전 10:39",
                        "11월 27일 오후 2:52"
                    )[index],
                    contentUrl = listOf(
                        "https://eclass3.cau.ac.kr/courses/111717/discussion_topics/373559",
                        "https://eclass3.cau.ac.kr/courses/111785/discussion_topics/373659",
                        "https://eclass3.cau.ac.kr/courses/111728/discussion_topics/373718"
                    )[index]
                )
            }
            

            item {
                SectionTitle(title = "과제 알림")
            }
            items(3) { index ->
                val homeworkUrl = "https://example.com/homework_${index + 1}"  // Simulated URL for homework reminders, replace with actual
                HomeworkCard(
                    courseName = listOf("소프트웨어 공학", "데이터베이스 설계", "인공지능")[index],
                    details = listOf(
                        "모바일 앱 개발 | 12/3 과제",
                        "휴먼인터페이스미디어 | Peer-review",
                        "CAU도전스타트업 | 소감문 제출요청"
                    )[index],
                    dueDate = listOf(
                        "마감: 12월 3일 오후 2:50",
                        "마감: 12월 10일 오전 10:00",
                        "마감: 마감일 없음"
                    )[index],
                    homeworkUrl = listOf(
                        "https://eclass3.cau.ac.kr/courses/111728/assignments/1505992",
                        "https://eclass3.cau.ac.kr/courses/111785/assignments/1507106",
                        "https://eclass3.cau.ac.kr/courses/114073/assignments/1506310"
                    )[index],
                )
            }

            // Schedule section
            item {
                SectionTitle(title = "시간표")
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
        style = MaterialTheme.typography.titleLarge, // Replaced h6
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
            Text(text = title, style = MaterialTheme.typography.titleLarge) // Replaced h6
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium, // Replaced body2
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
                text = "과목명: $courseName",
                style = MaterialTheme.typography.titleLarge // Replaced h6
            )
            Text(
                text = "과제 세부사항: $details",
                style = MaterialTheme.typography.bodyLarge // Replaced body1
            )
            Text(
                text = dueDate,
                style = MaterialTheme.typography.bodyMedium, // Replaced body2
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
                text = "과목명: $courseName",
                style = MaterialTheme.typography.titleLarge // Replaced h6
            )
            Text(
                text = "강의실: $location",
                style = MaterialTheme.typography.bodyLarge // Replaced body1
            )
            Text(
                text = "강의 시간: $time",
                style = MaterialTheme.typography.bodyMedium, // Replaced body2
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
        }
    }
}