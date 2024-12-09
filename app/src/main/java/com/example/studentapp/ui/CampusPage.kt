package com.example.studentapp.ui

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studentapp.ui.theme.AppColors

@Composable
fun CampusPage() {
    val navController = rememberNavController()
    var selectedTab by remember { mutableStateOf("학부공지") } // State for switching buttons
    var isCalendarClicked by remember { mutableStateOf(false) } // Control display of calendar page
    var isMapClicked by remember { mutableStateOf(false) } // Control display of map page

    Column(modifier = Modifier.fillMaxSize()) {
        // Top title bar
        TopBar()

        // Switch button area
        SwitchButtons(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        // Announcement list area
        AnnouncementList(selectedTab)

        // Bottom function button area, passing NavController for page navigation
        BottomFunctionButtons(navController)

        // Calendar and map card area
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
        // Calendar card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCalendarClick() }
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "학사일정", 
                    fontSize = 24.sp, 
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = AppColors.OnBackground
                )
                Text(
                    text = "바로보기", 
                    fontSize = 16.sp, 
                    color = AppColors.Secondary, 
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Map card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onMapClick() }
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = AppColors.CardBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "학교지도", 
                    fontSize = 24.sp, 
                    modifier = Modifier.align(Alignment.CenterVertically),
                    color = AppColors.OnBackground
                )
                Text(
                    text = "바로보기", 
                    fontSize = 16.sp, 
                    color = AppColors.Secondary, 
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun WebViewPage(url: String, onBack: () -> Unit) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val webViewHeight = screenHeight * 0.7f

    val webView = remember { WebView(context) }
    webView.loadUrl(url)
    webView.settings.javaScriptEnabled = true
    webView.webViewClient = WebViewClient()

    var isLoading by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Button(onClick = onBack) {
            Text("돌아가기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(webViewHeight)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Back button
        Button(onClick = onBack) {
            Text("돌아가기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading && !hasError) {
            // Show loading animation
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!hasError) {
            AsyncImage(
                model = url,
                contentDescription = "Map Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 500.dp),
                contentScale = ContentScale.Fit,
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
            // Show error message
            Text(
                text = "지도 로딩에 실패했습니다. 네트워크 연결이나 링크를 확인해 주세요.",
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
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
                text = "캠퍼스",
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
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
            onClick = { onTabSelected("학부공지") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == "학부공지") MaterialTheme.colorScheme.primary else Color.Gray
            ),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        ) {
            Text(text = "학부공지", color = Color.White)
        }

        Button(
            onClick = { onTabSelected("학과공지") },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (selectedTab == "학과공지") MaterialTheme.colorScheme.primary else Color.Gray
            ),
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
        ) {
            Text(text = "학과공지", color = Color.White)
        }
    }
}

@Composable
fun AnnouncementList(selectedTab: String) {
    val context = LocalContext.current
    val announcements = if (selectedTab == "학부공지") {
        listOf(
            "2024학년도 동계 디지털 직무 아카데미 / 코딩자격증 준비..." to "https://www.cau.ac.kr/cms/FR_CON/BoardView.do?MENU_ID=100&CONTENTS_NO=1&SITE_NO=2&P_TAB_NO=&TAB_NO=&BOARD_SEQ=4&BOARD_CATEGORY_NO=&BBS_SEQ=28391&pageNo=1",
            "학교법인 중앙대학교 2024학년도 5차 이사회 개최 안내" to "https://www.cau.ac.kr/cms/FR_CON/BoardView.do?MENU_ID=100&CONTENTS_NO=1&SITE_NO=2&P_TAB_NO=&TAB_NO=&BOARD_SEQ=4&BOARD_CATEGORY_NO=&BBS_SEQ=28419&pageNo=1",
            "한국지도자육성장학재단 2025학년도 신규장학생 선발 공고" to "https://www.cau.ac.kr/cms/FR_CON/BoardView.do?MENU_ID=100&CONTENTS_NO=1&SITE_NO=2&P_TAB_NO=&TAB_NO=&BOARD_SEQ=4&BOARD_CATEGORY_NO=&BBS_SEQ=28418&pageNo=1"
        )
    } else {
        listOf(
            "2025학년도 1학기 재입학 시행 안내" to "https://cse.cau.ac.kr/sub05/sub0501.php?nmode=view&code=oktomato_bbs05&uid=3063&search=&keyword=&temp1=&offset=1",
            "2025학년도 전과(부) 시행 안내" to "https://cse.cau.ac.kr/sub05/sub0501.php?nmode=view&code=oktomato_bbs05&uid=3062&search=&keyword=&temp1=&offset=1",
            "2024-2학기 기말고사 성적입력 및 최종성적평가 안내" to "https://cse.cau.ac.kr/sub05/sub0501.php?nmode=view&code=oktomato_bbs05&uid=3061&search=&keyword=&temp1=&offset=1"
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
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE0F7FA)
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
fun BottomFunctionButtons(navController: NavController) {
    val functions = listOf("빈 강의실", "식당 메뉴", "교수 정보")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            functions.forEach { function ->
                Button(
                    onClick = {
                        when (function) {
                            "빈 강의실" -> navController.navigate("EmptyClassroom")
                            "식당 메뉴" -> navController.navigate("CanteenMenu")
                            "교수 정보" -> navController.navigate("Professor")
                            else -> {}
                        }
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = function,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "Campus"
    ) {
        composable("Campus") {
            CampusPage()
        }
        composable("EmptyClassroom") {
            EmptyClassroomScreen()
        }
        composable("CanteenMenu") {
            CanteenMenuScreen()
        }
        composable("Professor") {
            ProfessorScreen()
        }
    }
}