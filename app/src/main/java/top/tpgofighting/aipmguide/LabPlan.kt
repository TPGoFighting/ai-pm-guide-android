package top.tpgofighting.aipmguide

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.roundToInt

private fun fmt(n: Double): String = if (n >= 10000) {
    val v = n / 10000
    val s = "%.1f".format(v)
    (if (s.endsWith(".0")) s.dropLast(2) else s) + "万"
} else {
    "%,d".format(n.roundToInt())
}

@Composable
fun LabScreen(nav: NavController, onVisit: (String) -> Unit) {
    ScreenColumn {
        BackRow({ nav.popBackStack() }, "03 用一道估算题训练思维")
        LabBody(onVisit)
    }
}

@Composable
fun LabBody(onVisit: (String) -> Unit) {
    LaunchedEffect(Unit) { onVisit("thinking") }
    var pop by remember { mutableStateOf(2400f) }
    var rate by remember { mutableStateOf(1.0f) }
    var stores by remember { mutableStateOf(800f) }
    var per by remember { mutableStateOf(120f) }

    val dem = pop * 10000 * (rate / 100)
    val sup = stores * per
    val demL = dem * 0.7; val demH = dem * 1.3
    val supL = sup * 0.7; val supH = sup * 1.3
    val max = maxOf(demH, supH)
    val bindDemand = dem <= sup
    val lo = if (bindDemand) demL else supL
    val hi = if (bindDemand) demH else supH
    val demFrac by animateFloatAsState((dem / max).toFloat(), animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow))
    val supFrac by animateFloatAsState((sup / max).toFloat(), animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow))

    BodyColumn {
        SketchCard(containerColor = DarkCard) {
            Column(Modifier.padding(22.dp)) {
                Text("CASE QUESTION · 互动实验室", color = Color(0xFFFF9A78), fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
                Spacer(Modifier.height(8.dp))
                Text("上海一天能卖出多少份热狗？", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 21.sp, lineHeight = 28.sp)
                Spacer(Modifier.height(6.dp))
                Text("重点不是猜中一个精确数字，而是让假设、推导过程和验证方法清晰可见。拖动下面的假设，结果只会给你一个区间——这正是这道题要教的东西。", color = Color(0xFFC8C9C2), fontSize = 13.sp, lineHeight = 20.sp)
                Spacer(Modifier.height(18.dp))
                LabSlider(Icons.Filled.Person, "潜在消费者（万人）", "${pop.roundToInt()}万", pop, 500f..2500f, 39) { pop = it }
                LabSlider(Icons.Filled.Percent, "当天购买率", "%.1f%%".format(rate), rate, 0.2f..5f, 47) { rate = it }
                LabSlider(Icons.Filled.Store, "供给侧：摊位数", "${stores.roundToInt()} 家", stores, 200f..2000f, 35) { stores = it }
                LabSlider(Icons.Filled.ShoppingBag, "单店日销量（份）", "${per.roundToInt()} 份", per, 50f..300f, 24) { per = it }
                Spacer(Modifier.height(16.dp))
                LabBar("需求侧", demFrac, "${fmt(dem.toDouble())} 份", Color.White)
                Spacer(Modifier.height(10.dp))
                LabBar("供给侧", supFrac, "${fmt(sup.toDouble())} 份", Accent)
                Spacer(Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(14.dp)).background(Color.White.copy(alpha = 0.07f)).padding(16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("约 ${fmt(lo)} – ${fmt(hi)} 份 / 天", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "瓶颈在${if (bindDemand) "需求侧" else "供给侧"}（取小值交叉验证）。拖动假设，区间跟着变——区间本身就是答案的一部分，别把它写成一个精确数字。",
                            color = Color(0xFFC8C9C2), fontSize = 12.sp, lineHeight = 18.sp,
                        )
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
    }
}

@Composable
fun LabSlider(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, valueText: String, value: Float, range: ClosedFloatingPointRange<Float>, steps: Int, onChange: (Float) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Gold, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(7.dp))
        Text(label, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(valueText, color = Gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
    Slider(
        value = value,
        onValueChange = onChange,
        valueRange = range,
        steps = steps,
        colors = SliderDefaults.colors(thumbColor = Accent, activeTrackColor = Accent, inactiveTrackColor = Color.White.copy(alpha = 0.25f)),
    )
}

@Composable
fun LabBar(label: String, frac: Float, text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Color(0xFFC8C9C2), fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(48.dp))
        Box(modifier = Modifier.weight(1f).height(16.dp).clip(RoundedCornerShape(99.dp)).background(Color.White.copy(alpha = 0.12f))) {
            Box(modifier = Modifier.fillMaxWidth(frac.coerceIn(0f, 1f)).height(16.dp).clip(RoundedCornerShape(99.dp)).background(color))
        }
        Spacer(Modifier.width(8.dp))
        Text(text, color = Color(0xFFC8C9C2), fontSize = 11.sp, modifier = Modifier.width(74.dp))
    }
}

@Composable
fun PlanScreen(onVisit: (String) -> Unit) {
    ScreenColumn { PlanBody(onVisit) }
}

@Composable
fun PlanBody(onVisit: (String) -> Unit, flat: Boolean = false) {
    LaunchedEffect(Unit) { onVisit("plan") }
    val ch = chapterOf("plan")
    val ctx = LocalContext.current
    val prefs = remember { ctx.getSharedPreferences("plan", android.content.Context.MODE_PRIVATE) }
    val checked = remember {
        mutableStateListOf<Boolean>().apply {
            PLAN_ITEMS.indices.forEach { i -> add(prefs.getBoolean("item_$i", false)) }
        }
    }
    val n = checked.count { it }
    val progress by animateFloatAsState(
        targetValue = n / PLAN_ITEMS.size.toFloat(),
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessLow),
    )
    BodyColumn {
        if (!flat) ChapterHeader(ch, true)
        SketchCard {
            Column(Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("已完成 $n / ${PLAN_ITEMS.size} 项", color = Grey, fontSize = 12.sp, modifier = Modifier.weight(1f))
                    TextButton(onClick = {
                        for (i in PLAN_ITEMS.indices) {
                            checked[i] = false
                            prefs.edit().putBoolean("item_$i", false).apply()
                        }
                    }) { Text("重置清单", color = Grey, fontSize = 12.sp) }
                }
                Spacer(Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(99.dp)),
                    color = Accent,
                    trackColor = Paper,
                )
                Spacer(Modifier.height(8.dp))
                PLAN_ITEMS.forEachIndexed { i, item ->
                    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(vertical = 12.dp)) {
                        Checkbox(
                            checked = checked[i],
                            onCheckedChange = {
                                checked[i] = it
                                prefs.edit().putBoolean("item_$i", it).apply()
                            },
                            colors = CheckboxDefaults.colors(checkedColor = Accent),
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                item.title,
                                fontSize = 14.sp, fontWeight = FontWeight.Bold,
                                color = if (checked[i]) Grey else Ink,
                                textDecoration = if (checked[i]) androidx.compose.ui.text.style.TextDecoration.LineThrough else null,
                            )
                            Text(item.desc, fontSize = 12.sp, color = Grey, lineHeight = 18.sp)
                        }
                    }
                }
                Spacer(Modifier.height(12.dp))
                Text(
                    when {
                        n == 0 -> "先从第 1 项开始：方向定了，后面的每一步才有靶子。"
                        n == PLAN_ITEMS.size -> "7 项全勾了——下一步就是拿着这份清单去面谈。"
                        else -> "已完成 $n 项，还差 ${PLAN_ITEMS.size - n} 项，继续。"
                    },
                    color = Grey, fontSize = 11.sp,
                )
            }
        }
    }
}
