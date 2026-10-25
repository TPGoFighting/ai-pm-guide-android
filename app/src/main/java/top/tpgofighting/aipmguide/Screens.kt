package top.tpgofighting.aipmguide

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

fun chapterIcon(id: String): ImageVector = when (id) {
    "takeaways" -> Icons.Filled.Star
    "skills" -> Icons.Filled.Explore
    "thinking" -> Icons.Filled.Lightbulb
    "resume" -> Icons.Filled.Description
    "workflow" -> Icons.Filled.Refresh
    "plan" -> Icons.Filled.CheckCircle
    else -> Icons.Filled.Info
}

@Composable
fun ScreenColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) { content() }
}

/* 组页平铺用的无滚动内容列：外层 ScreenColumn 负责滚动与边距 */
@Composable
fun BodyColumn(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) { content() }
}

@Composable
fun ChapterCard(chapter: Chapter, stamped: Boolean, onClick: () -> Unit) {
    SketchCard(onClick = onClick) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.Top) {
            Icon(chapterIcon(chapter.id), contentDescription = null, tint = Ink, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text("${chapter.num} · ${chapter.title}", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Spacer(Modifier.height(4.dp))
                Text(chapter.intro, color = Grey, fontSize = 12.sp, lineHeight = 18.sp)
            }
            Spacer(Modifier.width(10.dp))
            Surface(
                color = if (stamped) Accent else Color.Transparent,
                shape = RoundedCornerShape(99.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, if (stamped) Ink else Grey),
            ) {
                Text(
                    if (stamped) "已盖章" else "未读",
                    fontSize = 11.sp, fontWeight = FontWeight.ExtraBold,
                    color = if (stamped) Color.White else Grey,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
        }
    }
}

fun groupIcon(id: String): ImageVector = when (id) {
    "direction" -> Icons.Filled.Explore
    "method" -> Icons.Filled.Lightbulb
    else -> Icons.Filled.CheckCircle
}

@Composable
fun GroupCard(group: Group, stamped: Boolean, onClick: () -> Unit) {
    SketchCard(onClick = onClick) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.Top) {
            Icon(groupIcon(group.id), contentDescription = null, tint = Ink, modifier = Modifier.size(28.dp))
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(group.title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                Spacer(Modifier.height(4.dp))
                Text(group.intro, color = Grey, fontSize = 12.sp, lineHeight = 18.sp)
            }
            Spacer(Modifier.width(10.dp))
            Surface(
                color = if (stamped) Accent else Color.Transparent,
                shape = RoundedCornerShape(99.dp),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, if (stamped) Ink else Grey),
            ) {
                Text(
                    if (stamped) "已读" else "未读",
                    fontSize = 11.sp, fontWeight = FontWeight.ExtraBold,
                    color = if (stamped) Color.White else Grey,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                )
            }
        }
    }
}

@Composable
fun HomeScreen(nav: NavController, visited: Set<String>) {
    ScreenColumn {
        Image(painterResource(R.drawable.logo), contentDescription = "TP Logo", modifier = Modifier.size(76.dp))
        Text("AI PRODUCT · CAREER PLAYBOOK", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
        Text("从技术出发，\n成为 AI 产品经理。", fontWeight = FontWeight.ExtraBold, fontSize = 38.sp, lineHeight = 46.sp)
        Text(HERO_INTRO, color = InkSoft, fontSize = 15.sp, lineHeight = 26.sp)
        FormulaRow()
        SketchCard(containerColor = DarkCard) {
            Column(Modifier.padding(24.dp)) {
                Text("THE CORE IDEA / 001", color = Color(0xFFC6C6BD), fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(16.dp))
                Text(HERO_CORE, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, lineHeight = 32.sp)
                Spacer(Modifier.height(12.dp))
                Text(HERO_CORE_CHAIN, color = Color(0xFFC5C6BE), fontSize = 13.sp, lineHeight = 20.sp)
                Spacer(Modifier.height(18.dp))
                FlowChips(HERO_CORE_TAGS)
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
            HERO_ROUTE.forEachIndexed { i, (t, d) ->
                Box(Modifier.weight(1f)) {
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Ink),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(t, fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                            Text(d, color = Grey, fontSize = 11.sp)
                        }
                    }
                }
                if (i < HERO_ROUTE.lastIndex) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Accent, modifier = Modifier.size(18.dp).align(Alignment.CenterVertically))
                }
            }
        }
        Text("CONTENTS / 三篇手账", color = Grey, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, letterSpacing = 1.sp)
        GROUPS.forEach { g ->
            GroupCard(g, g.chapters.all { visited.contains(it) }) { nav.navigate("group/${g.id}") }
        }
        FinaleCard()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormulaRow() {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        HERO_FORMULA.forEachIndexed { i, f ->
            Surface(
                color = if (i == HERO_FORMULA.lastIndex) Ink else Color.White,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Ink),
            ) {
                Text(f, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = if (i == HERO_FORMULA.lastIndex) Color.White else Ink, modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp))
            }
            if (i < HERO_FORMULA.lastIndex) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Accent, modifier = Modifier.size(18.dp).align(Alignment.CenterVertically))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowChips(tags: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        tags.forEach { t ->
            Surface(color = Color.Transparent, shape = RoundedCornerShape(99.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, Color.White.copy(alpha = 0.3f))) {
                Text(t, fontSize = 11.sp, color = Color(0xFFEEEEEE), modifier = Modifier.padding(horizontal = 11.dp, vertical = 5.dp))
            }
        }
    }
}

@Composable
fun FinaleCard() {
    SketchCard {
        Column(Modifier.padding(24.dp)) {
            Text("THE TAKEAWAY", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
            Spacer(Modifier.height(12.dp))
            Box(Modifier.border(androidx.compose.foundation.BorderStroke(0.dp, Color.Transparent)).padding(start = 14.dp)) {
                Text(FINALE_QUOTE, fontWeight = FontWeight.Bold, fontSize = 19.sp, lineHeight = 30.sp)
            }
            Spacer(Modifier.height(16.dp))
            Text(DISCLAIMER, color = Grey, fontSize = 12.sp, lineHeight = 20.sp)
        }
    }
}

/* 组页：点底部导航图标后直接打开融好的文章，页内不再有章节号标题 */
@Composable
fun GroupScreen(group: Group, nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn {
        Text("TP · CAREER NOTES", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
        Text(group.title, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
        Text(group.intro, color = Grey, fontSize = 13.sp, lineHeight = 20.sp)
        group.chapters.forEach { id ->
            when (id) {
                "takeaways" -> TakeawaysBody(nav, onVisit, flat = true)
                "skills" -> SkillsBody(nav, onVisit, flat = true)
                "thinking" -> {
                    ThinkingBody(nav, onVisit, flat = true)
                    LabBody(onVisit)
                }
                "resume" -> ResumeBody(nav, onVisit, flat = true)
                "workflow" -> WorkflowBody(nav, onVisit, flat = true)
                "plan" -> PlanBody(onVisit, flat = true)
                "details" -> DetailsBody(nav, onVisit, flat = true)
            }
        }
    }
}

@Composable
fun TakeawaysScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn { TakeawaysBody(nav, onVisit) }
}

@Composable
fun TakeawaysBody(nav: NavController, onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("takeaways") }
    val ch = chapterOf("takeaways")
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        TAKEAWAYS.forEach { t ->
            SketchCard(onClick = if (flat) null else ({ nav.navigate("takeaway/${t.id}") })) {
                Column(Modifier.padding(20.dp)) {
                    Text(t.idx, color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(t.title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(t.body, color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp)
                    Spacer(Modifier.height(12.dp))
                    TagChip(t.tag)
                }
            }
        }
    }
}

@Composable
fun TakeawayDetailScreen(id: String, nav: NavController) {
    val i = TAKEAWAYS.indexOfFirst { it.id == id }
    if (i < 0) return
    val t = TAKEAWAYS[i]
    ScreenColumn {
        BackRow({ nav.popBackStack() }, "01 最重要的 5 个结论")
        SketchCard {
            Column(Modifier.padding(24.dp)) {
                Text(t.idx, color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(t.title, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, lineHeight = 32.sp)
                Spacer(Modifier.height(12.dp))
                Text(t.body, color = InkSoft, fontSize = 15.sp, lineHeight = 26.sp)
                Spacer(Modifier.height(16.dp))
                Text("关键词：${t.tag}", fontSize = 14.sp, color = InkSoft)
            }
        }
        PrevNextRow(
            TAKEAWAYS.getOrNull(i - 1)?.title, { nav.navigate("takeaway/${TAKEAWAYS[i - 1].id}") { popUpTo("takeaway/$id") { inclusive = true } } }.takeIf { i > 0 },
            TAKEAWAYS.getOrNull(i + 1)?.title, { nav.navigate("takeaway/${TAKEAWAYS[i + 1].id}") { popUpTo("takeaway/$id") { inclusive = true } } }.takeIf { i < TAKEAWAYS.lastIndex },
        )
    }
}

@Composable
fun SkillsScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn { SkillsBody(nav, onVisit) }
}

@Composable
fun SkillsBody(nav: NavController, onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("skills") }
    val ch = chapterOf("skills")
    var selected by remember { mutableStateOf(0) }
    val s = SKILLS[selected]
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        Text("点一点下面的能力，看要求和证明方式。", color = Grey, fontSize = 13.sp)
        FlowChipsGrid(selected) { selected = it }
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Text("SKILL 0${selected + 1} / 能力要求", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(6.dp))
                Text(s.name, fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
                Spacer(Modifier.height(8.dp))
                Text(s.req, color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp)
                Spacer(Modifier.height(12.dp))
                Text("如何证明：${s.proof}", fontSize = 13.sp, lineHeight = 20.sp, color = InkSoft)
                if (!flat) {
                    Spacer(Modifier.height(12.dp))
                    Button(onClick = { nav.navigate("skill/${s.id}") }, colors = ButtonDefaults.buttonColors(containerColor = Ink)) {
                        Text("在单页里看")
                    }
                }
            }
        }
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Text("优先建立的三项优势", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Spacer(Modifier.height(6.dp))
                Text(SKILL_CALLOUT, color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowChipsGrid(selected: Int, onSelect: (Int) -> Unit) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(10.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        SKILLS.forEachIndexed { i, s ->
            val on = i == selected
            Surface(
                color = if (on) Ink else Color.White,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Ink),
                modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                onClick = { onSelect(i) },
            ) {
                Text(
                    s.name, fontSize = 13.sp, fontWeight = FontWeight.ExtraBold,
                    color = if (on) Color.White else Ink,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                )
            }
        }
    }
}

@Composable
fun SkillDetailScreen(id: String, nav: NavController) {
    val i = SKILLS.indexOfFirst { it.id == id }
    if (i < 0) return
    val s = SKILLS[i]
    ScreenColumn {
        BackRow({ nav.popBackStack() }, "02 产品经理能力模型")
        SketchCard {
            Column(Modifier.padding(24.dp)) {
                Text("SKILL 0${i + 1} / 能力要求", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(s.name, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
                Spacer(Modifier.height(12.dp))
                Text(s.req, color = InkSoft, fontSize = 15.sp, lineHeight = 26.sp)
                Spacer(Modifier.height(16.dp))
                Text("如何证明：${s.proof}", fontSize = 14.sp, lineHeight = 22.sp, color = InkSoft)
                if (s.priority.isNotEmpty()) {
                    Spacer(Modifier.height(14.dp))
                    Surface(color = AccentSoft, shape = RoundedCornerShape(12.dp), border = androidx.compose.foundation.BorderStroke(1.5.dp, Accent.copy(alpha = 0.4f))) {
                        Text(s.priority, fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(12.dp))
                    }
                }
            }
        }
        PrevNextRow(
            SKILLS.getOrNull(i - 1)?.name, { nav.navigate("skill/${SKILLS[i - 1].id}") { popUpTo("skill/$id") { inclusive = true } } }.takeIf { i > 0 },
            SKILLS.getOrNull(i + 1)?.name, { nav.navigate("skill/${SKILLS[i + 1].id}") { popUpTo("skill/$id") { inclusive = true } } }.takeIf { i < SKILLS.lastIndex },
        )
    }
}

@Composable
fun ThinkingScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn { ThinkingBody(nav, onVisit) }
}

@Composable
fun ThinkingBody(nav: NavController, onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("thinking") }
    val ch = chapterOf("thinking")
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        SketchCard(containerColor = DarkCard) {
            Column(Modifier.padding(22.dp)) {
                Text("CASE QUESTION", color = Color(0xFFFF9A78), fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text("上海一天能卖出多少份热狗？", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 21.sp, lineHeight = 28.sp)
                Spacer(Modifier.height(6.dp))
                Text("重点不是猜中一个精确数字，而是让假设、推导过程和验证方法清晰可见。", color = Color(0xFFC8C9C2), fontSize = 13.sp, lineHeight = 20.sp)
            }
        }
        THINK_STEPS.forEachIndexed { i, s ->
            SketchCard {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(Paper).border(androidx.compose.foundation.BorderStroke(1.5.dp, Ink), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center,
                    ) { Text("0${i + 1}", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp) }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(s.name, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                        Text(s.body, color = Grey, fontSize = 13.sp, lineHeight = 20.sp)
                    }
                }
            }
        }
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Text("核心方法：先需求侧，再供给侧。", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Spacer(Modifier.height(6.dp))
                Text(THINK_METHOD, color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp)
            }
        }
        if (!flat) {
            Button(
                onClick = { nav.navigate("lab") },
                colors = ButtonDefaults.buttonColors(containerColor = Ink),
                modifier = Modifier.fillMaxWidth(),
            ) { Text("进入估算实验室", modifier = Modifier.padding(vertical = 6.dp)) }
        }
    }
}

@Composable
fun ResumeScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn { ResumeBody(nav, onVisit) }
}

@Composable
fun ResumeBody(nav: NavController, onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("resume") }
    val ch = chapterOf("resume")
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        RESUME_PANELS.forEach { p ->
            var open by remember { mutableStateOf(p.id == "tech") }
            SketchCard {
                Column(Modifier.padding(18.dp).animateContentSize()) {
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Text(p.title, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, modifier = Modifier.weight(1f))
                        androidx.compose.material3.TextButton(onClick = { open = !open }) {
                            Text(if (open) "收起" else "展开", color = Accent, fontWeight = FontWeight.Bold)
                        }
                    }
                    if (open) {
                        p.qs.forEach { q ->
                            Text("•  $q", color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp, modifier = Modifier.padding(vertical = 2.dp))
                        }
                        if (!flat) {
                            Spacer(Modifier.height(10.dp))
                            androidx.compose.material3.TextButton(onClick = { nav.navigate("resumePanel/${p.id}") }) {
                                Text("在单页里看", color = Accent, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Text("项目描述的推荐结构", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Spacer(Modifier.height(12.dp))
                ChainChips(RESUME_CHAIN)
                Spacer(Modifier.height(10.dp))
                Text(RESUME_CHAIN_NOTE, color = InkSoft, fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun ResumePanelDetailScreen(id: String, nav: NavController) {
    val i = RESUME_PANELS.indexOfFirst { it.id == id }
    if (i < 0) return
    val p = RESUME_PANELS[i]
    ScreenColumn {
        BackRow({ nav.popBackStack() }, "04 把已有经历写成岗位证据")
        SketchCard {
            Column(Modifier.padding(24.dp)) {
                Text("RESUME EVIDENCE / 面试官会追问", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(p.title, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
                Spacer(Modifier.height(14.dp))
                p.qs.forEach { q -> Text("•  $q", fontSize = 14.sp, lineHeight = 26.sp, color = InkSoft) }
            }
        }
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Text("项目描述的推荐结构", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Spacer(Modifier.height(12.dp))
                ChainChips(RESUME_CHAIN)
                Spacer(Modifier.height(10.dp))
                Text(RESUME_CHAIN_NOTE, color = InkSoft, fontSize = 13.sp)
            }
        }
        PrevNextRow(
            RESUME_PANELS.getOrNull(i - 1)?.title, { nav.navigate("resumePanel/${RESUME_PANELS[i - 1].id}") { popUpTo("resumePanel/$id") { inclusive = true } } }.takeIf { i > 0 },
            RESUME_PANELS.getOrNull(i + 1)?.title, { nav.navigate("resumePanel/${RESUME_PANELS[i + 1].id}") { popUpTo("resumePanel/$id") { inclusive = true } } }.takeIf { i < RESUME_PANELS.lastIndex },
        )
    }
}

@Composable
fun WorkflowScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn { WorkflowBody(nav, onVisit) }
}

@Composable
fun WorkflowBody(nav: NavController, onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("workflow") }
    val ch = chapterOf("workflow")
    var selected by remember { mutableStateOf(0) }
    val s = WORK_STEPS[selected]
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        WORK_STEPS.forEachIndexed { i, step ->
            val on = i == selected
            SketchCard(
                onClick = { selected = i },
                containerColor = if (on) Ink else Color.White,
            ) {
                Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(12.dp)).background(if (on) Color.White.copy(alpha = 0.15f) else Paper).border(androidx.compose.foundation.BorderStroke(1.5.dp, if (on) Color.White else Ink), RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center,
                    ) { Text("0${i + 1}", fontWeight = FontWeight.ExtraBold, fontSize = 12.sp, color = if (on) Color.White else Ink) }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text(step.title, fontWeight = FontWeight.ExtraBold, fontSize = 15.sp, color = if (on) Color.White else Ink)
                        Text(step.body, color = if (on) Color(0xFFC8C9C2) else Grey, fontSize = 13.sp, lineHeight = 20.sp)
                    }
                }
            }
        }
        if (!flat) {
            androidx.compose.material3.TextButton(onClick = { nav.navigate("workflowStep/${s.id}") }) {
                Text("在单页里看：${s.title}", color = Accent, fontWeight = FontWeight.Bold)
            }
        }
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Text("需要了解的协作基础", fontWeight = FontWeight.ExtraBold, fontSize = 15.sp)
                Spacer(Modifier.height(6.dp))
                Text(WORK_CALLOUT, color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp)
            }
        }
    }
}

@Composable
fun WorkflowStepDetailScreen(id: String, nav: NavController) {
    val i = WORK_STEPS.indexOfFirst { it.id == id }
    if (i < 0) return
    val s = WORK_STEPS[i]
    ScreenColumn {
        BackRow({ nav.popBackStack() }, "05 产品经理的工作闭环")
        SketchCard {
            Column(Modifier.padding(24.dp)) {
                Text("STEP 0${i + 1} / 工作闭环", color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(s.title, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
                Spacer(Modifier.height(12.dp))
                Text(s.body, color = InkSoft, fontSize = 15.sp, lineHeight = 26.sp)
            }
        }
        PrevNextRow(
            WORK_STEPS.getOrNull(i - 1)?.title, { nav.navigate("workflowStep/${WORK_STEPS[i - 1].id}") { popUpTo("workflowStep/$id") { inclusive = true } } }.takeIf { i > 0 },
            WORK_STEPS.getOrNull(i + 1)?.title, { nav.navigate("workflowStep/${WORK_STEPS[i + 1].id}") { popUpTo("workflowStep/$id") { inclusive = true } } }.takeIf { i < WORK_STEPS.lastIndex },
        )
    }
}

@Composable
fun DetailsScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn { DetailsBody(nav, onVisit) }
}

@Composable
fun DetailsBody(nav: NavController, onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("details") }
    val ch = chapterOf("details")
    val bgColors = listOf(Color.White, Color(0xFFFFF7D6), Color(0xFFEAFBE0), Color(0xFFFFEEF2))
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        TIPS.forEachIndexed { i, t ->
            SketchCard(
                onClick = if (flat) null else ({ nav.navigate("tip/${t.id}") }),
                containerColor = bgColors[i % bgColors.size],
                modifier = Modifier.graphicsLayer { rotationZ = if (i % 2 == 0) -0.4f else 0.5f },
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(t.idx, color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(t.title, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                    Spacer(Modifier.height(6.dp))
                    Text(t.body, color = InkSoft, fontSize = 13.sp, lineHeight = 22.sp)
                }
            }
        }
    }
}

@Composable
fun TipDetailScreen(id: String, nav: NavController) {
    val i = TIPS.indexOfFirst { it.id == id }
    if (i < 0) return
    val t = TIPS[i]
    ScreenColumn {
        BackRow({ nav.popBackStack() }, "07 三个容易忽略的细节")
        SketchCard {
            Column(Modifier.padding(24.dp)) {
                Text(t.idx, color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text(t.title, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, lineHeight = 32.sp)
                Spacer(Modifier.height(12.dp))
                Text(t.body, color = InkSoft, fontSize = 15.sp, lineHeight = 26.sp)
            }
        }
        PrevNextRow(
            TIPS.getOrNull(i - 1)?.title, { nav.navigate("tip/${TIPS[i - 1].id}") { popUpTo("tip/$id") { inclusive = true } } }.takeIf { i > 0 },
            TIPS.getOrNull(i + 1)?.title, { nav.navigate("tip/${TIPS[i + 1].id}") { popUpTo("tip/$id") { inclusive = true } } }.takeIf { i < TIPS.lastIndex },
        )
    }
}
