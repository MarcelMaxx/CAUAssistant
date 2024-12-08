package com.example.studentapp.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.studentapp.R

@Composable
fun HomePage() {
    var selectedTab by remember { mutableStateOf("校公告") } // 切换按钮的状态
    var isCalendarClicked by remember { mutableStateOf(false) } // 控制日历页面的显示
    var isMapClicked by remember { mutableStateOf(false) } // 控制地图页面的显示

    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部标题栏
        TopBar()

        // 切换按钮区域
        SwitchButtons(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        // 公告列表区域
        AnnouncementList(selectedTab)

        // 底部功能按钮区域
        BottomFunctionButtons()

        // 日历和地图卡片区域
        if (isCalendarClicked) {
            WebViewPage(url = "https://www.cau.ac.kr/cms/FR_CON/index.do?MENU_ID=590", onBack = { isCalendarClicked = false })
        } else if (isMapClicked) {
            MapImagePage(url = "https://www.cau.ac.kr/cau/img/about/caupusmap/map.png", onBack = { isMapClicked = false })
        } else {
            CalendarAndMapCards(
                onCalendarClick = { isCalendarClicked = true },
                onMapClick = { isMapClicked = true }
            )
        }
    }
}

@Composable
fun CalendarAndMapCards(onCalendarClick: () -> Unit, onMapClick: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        // 日历卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCalendarClick() }
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "日历", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterVertically))
                Text(text = "点击查看", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 地图卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMapClick() }
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "地图", fontSize = 24.sp, modifier = Modifier.align(Alignment.CenterVertically))
                Text(text = "点击查看", fontSize = 16.sp, color = Color.Gray, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
    }
}

@Composable
fun WebViewPage(url: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current // 获取屏幕配置
    val screenHeight = configuration.screenHeightDp.dp // 屏幕高度（dp）
    val webViewHeight = (screenHeight * 0.7f) // 计算 70% 的高度

    val webView = remember { WebView(context) }
    webView.loadUrl(url)
    webView.settings.javaScriptEnabled = true
    webView.webViewClient = WebViewClient()

    var isLoading by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("返回")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(webViewHeight) // 动态设置高度为屏幕 70%
        ) {
            AndroidView(
                factory = { webView },
                modifier = Modifier.fillMaxSize(),
                update = {
                    it.webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                        }
                    }
                }
            )
        }
    }
}
@Composable
fun MapImagePage(url: String, onBack: () -> Unit) {
    var isLoading by remember { mutableStateOf(true) }
    var hasError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 返回按钮
        Button(onClick = onBack) {
            Text("返回")
        }

        Spacer(modifier = Modifier.height(100.dp))

        if (isLoading && !hasError) {
            // 显示加载动画
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (!hasError) {
            AsyncImage(
                model = url,
                contentDescription = "Map Image",
                modifier = Modifier.fillMaxSize(),
                onLoading = { isLoading = true },
                onSuccess = { isLoading = false },
                onError = {
                    Log.e("MapImagePage", "Image loading failed: $it")
                    isLoading = false
                    hasError = true
                }

            )
        }

        if (hasError) {
            // 显示错误信息
            Text(
                text = "地图加载失败，请检查网络或链接。",
                color = Color.Red,
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Home",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary // 背景颜色
        ),
        modifier = Modifier.fillMaxWidth().height(56.dp)
    )
}

@Composable
fun SwitchButtons(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { onTabSelected("校公告") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == "校公告") MaterialTheme.colorScheme.primary else Color.Gray
            ),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        ) {
            Text(text = "校公告", color = Color.White)
        }

        Button(
            onClick = { onTabSelected("科公告") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == "科公告") MaterialTheme.colorScheme.primary else Color.Gray
            ),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        ) {
            Text(text = "科公告", color = Color.White)
        }
    }
}

@Composable
fun AnnouncementList(selectedTab: String) {
    val context = LocalContext.current
    val announcements = if (selectedTab == "校公告") {
        listOf(
            "校庆活动安排通知" to "https://example.com/event",
            "运动会报名开始" to "https://example.com/sports",
            "图书馆开放时间调整" to "https://example.com/library"
        )
    } else {
        listOf(
            "软件工程课程更新" to "https://example.com/software",
            "人工智能课程案例研究" to "https://example.com/ai",
            "数据库设计作业提醒" to "https://example.com/database"
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        items(announcements.size) { index ->
            AnnouncementCard(
                title = announcements[index].first,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(announcements[index].second))
                    context.startActivity(intent)
                }
            )
        }
    }
}


@Composable
fun AnnouncementCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors( // 设置背景颜色
            containerColor = Color(0xFFE0F7FA) // 浅蓝色
        )
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 14.sp
        )
    }
}


@Composable
fun BottomFunctionButtons() {
    val functions = listOf("功能1", "功能2", "功能3", "功能4", "功能5", "功能6")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        for (row in functions.chunked(3)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { function ->
                    Button(
                        onClick = { /* 跳转功能 */ },
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = function, fontSize = 14.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
