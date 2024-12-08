package com.example.studentapp.ui

data class ScheduleData(
    val courseName: String,
    val location: String,
    val time: String
)

object ScheduleDataSource {
    fun getScheduleDataList(): List<ScheduleData> {
        return listOf(
            ScheduleData(
                courseName = "数学分析",
                location = "教学楼101",
                time = "11:00 - 12:59"
            ),
            ScheduleData(
                courseName = "大学物理",
                location = "实验楼202",
                time = "14:00 - 15:59"
            ),
            ScheduleData(
                courseName = "英语读写",
                location = "教学楼303",
                time = "09:00 - 10:59"
            )
        )
    }
}