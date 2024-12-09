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
                courseName = "빅 데이터",
                location = "310관 727호",
                time = "11:00 - 12:59"
            ),
            ScheduleData(
                courseName = "모바일 앱 개발",
                location = "310관 729호",
                time = "13:00 - 14:59"
            ),
            ScheduleData(
                courseName = "고급 IoT 프로젝트",
                location = "310관 B311호",
                time = "15:00 - 17:59"
            )
        )
    }
}