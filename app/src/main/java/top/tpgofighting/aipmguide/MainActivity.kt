package top.tpgofighting.aipmguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private data class BottomItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

private val BOTTOM_ITEMS = listOf(
    BottomItem("home", "首页", Icons.Filled.Home),
    BottomItem("group/direction", "方向", Icons.Filled.Explore),
    BottomItem("group/method", "方法", Icons.Filled.Lightbulb),
    BottomItem("group/delivery", "执行", Icons.Filled.CheckCircle),
)

private fun chapterForRoute(route: String?): String? = when {
    route == null -> null
    route == "home" -> null
    route.startsWith("group/") -> null
    route == "takeaways" || route.startsWith("takeaway/") -> "takeaways"
    route == "skills" || route.startsWith("skill/") -> "skills"
    route == "thinking" || route == "lab" -> "thinking"
    route == "resume" || route.startsWith("resumePanel/") -> "resume"
    route == "workflow" || route.startsWith("workflowStep/") -> "workflow"
    route == "plan" -> "plan"
    route == "details" || route.startsWith("tip/") -> "details"
    else -> null
}

private fun bottomForRoute(route: String?): String = when {
    route == "home" -> "home"
    route?.startsWith("group/") == true -> route
    else -> chapterForRoute(route)?.let { "group/${groupOfChapter(it).id}" } ?: "home"
}

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuideTheme {
                val nav = rememberNavController()
                val visited = remember { mutableStateListOf<String>() }
                val onVisit: (String) -> Unit = { id -> if (!visited.contains(id)) visited.add(id) }
                val backStack by nav.currentBackStackEntryAsState()
                val currentRoute = backStack?.destination?.route
                val selectedBottom = bottomForRoute(currentRoute)

                Scaffold(
                    containerColor = Paper,
                    topBar = {
                        TopAppBar(
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(painterResource(R.drawable.logo), contentDescription = "TP Logo", modifier = Modifier.size(30.dp))
                                    Spacer(Modifier.width(10.dp))
                                    Text("TP · Career Notes", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                                }
                            },
                            actions = {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = Gold, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text("${visited.size} / 7 章", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Spacer(Modifier.width(8.dp))
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = Paper2),
                        )
                    },
                    bottomBar = {
                        NavigationBar(containerColor = Paper2) {
                            BOTTOM_ITEMS.forEach { item ->
                                NavigationBarItem(
                                    selected = selectedBottom == item.route,
                                    onClick = {
                                        nav.navigate(item.route) {
                                            popUpTo(nav.graph.startDestinationId) { saveState = true }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(item.icon, contentDescription = item.label) },
                                    label = { Text(item.label, fontSize = 11.sp) },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = Color.White,
                                        selectedTextColor = Ink,
                                        indicatorColor = Ink,
                                        unselectedIconColor = InkSoft,
                                        unselectedTextColor = InkSoft,
                                    ),
                                )
                            }
                        }
                    },
                ) { innerPadding ->
                    NavHost(
                        navController = nav,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable("home") { HomeScreen(nav, visited.toSet()) }
                        composable("group/{gid}") { backStack ->
                            val gid = backStack.arguments?.getString("gid") ?: "direction"
                            val group = GROUPS.firstOrNull { it.id == gid } ?: GROUPS[0]
                            GroupScreen(group, nav, onVisit)
                        }
                        composable("takeaways") { TakeawaysScreen(nav, onVisit) }
                        composable("takeaway/{id}") { backStack ->
                            TakeawayDetailScreen(backStack.arguments?.getString("id") ?: "", nav)
                        }
                        composable("skills") { SkillsScreen(nav, onVisit) }
                        composable("skill/{id}") { backStack ->
                            SkillDetailScreen(backStack.arguments?.getString("id") ?: "", nav)
                        }
                        composable("thinking") { ThinkingScreen(nav, onVisit) }
                        composable("lab") { LabScreen(nav, onVisit) }
                        composable("resume") { ResumeScreen(nav, onVisit) }
                        composable("resumePanel/{id}") { backStack ->
                            ResumePanelDetailScreen(backStack.arguments?.getString("id") ?: "", nav)
                        }
                        composable("workflow") { WorkflowScreen(nav, onVisit) }
                        composable("workflowStep/{id}") { backStack ->
                            WorkflowStepDetailScreen(backStack.arguments?.getString("id") ?: "", nav)
                        }
                        composable("plan") { PlanScreen(onVisit) }
                        composable("details") { DetailsScreen(nav, onVisit) }
                        composable("tip/{id}") { backStack ->
                            TipDetailScreen(backStack.arguments?.getString("id") ?: "", nav)
                        }
                    }
                }
            }
        }
    }
}
