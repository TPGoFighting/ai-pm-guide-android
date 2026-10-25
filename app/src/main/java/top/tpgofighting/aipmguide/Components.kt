package top.tpgofighting.aipmguide

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SketchCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    shape: androidx.compose.ui.graphics.Shape = SketchShape,
    containerColor: Color = Color.White,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier.then(if (onClick != null) Modifier.clickable { onClick() } else Modifier),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(2.dp, Ink),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) { content() }
}

@Composable
fun Stamp(num: String, stamped: Boolean) {
    Box(
        modifier = Modifier
            .size(52.dp)
            .clip(CircleShape)
            .background(if (stamped) Accent else Color.Transparent)
            .border(2.dp, if (stamped) Ink else Grey, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = num,
            color = if (stamped) Color.White else Grey,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
        )
    }
}

@Composable
fun ChapterHeader(chapter: Chapter, stamped: Boolean) {
    Row(verticalAlignment = Alignment.Top) {
        Stamp(chapter.num, stamped)
        Spacer(Modifier.width(14.dp))
        Column {
            Text(chapter.kicker, color = Accent, fontWeight = FontWeight.ExtraBold, fontSize = 11.sp, letterSpacing = 1.sp)
            Spacer(Modifier.height(4.dp))
            Text(chapter.title, fontWeight = FontWeight.ExtraBold, fontSize = 26.sp, lineHeight = 32.sp)
            Spacer(Modifier.height(8.dp))
            Text(chapter.intro, color = Grey, fontSize = 13.sp, lineHeight = 20.sp)
        }
    }
}

@Composable
fun TagChip(text: String) {
    Surface(
        color = AccentSoft,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Accent.copy(alpha = 0.4f)),
    ) {
        Text(text, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF925239), modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.dp))
    }
}

@Composable
fun BackRow(onBack: () -> Unit, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBack) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
        }
        Text(title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
    }
}

@Composable
fun PrevNextRow(prevLabel: String?, onPrev: (() -> Unit)?, nextLabel: String?, onNext: (() -> Unit)?) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        if (prevLabel != null && onPrev != null) {
            SketchCard(modifier = Modifier.weight(1f), onClick = onPrev) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = Accent, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text("上一个", fontSize = 10.sp, color = Grey, fontWeight = FontWeight.ExtraBold)
                        Text(prevLabel, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
        if (nextLabel != null && onNext != null) {
            SketchCard(modifier = Modifier.weight(1f), onClick = onNext) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text("下一个", fontSize = 10.sp, color = Grey, fontWeight = FontWeight.ExtraBold)
                        Text(nextLabel, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                    }
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Accent, modifier = Modifier.size(18.dp))
                }
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChainChips(items: List<String>) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items.forEachIndexed { i, item ->
            Surface(color = AccentSoft, shape = RoundedCornerShape(99.dp), border = BorderStroke(1.5.dp, Ink)) {
                Text(item, fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp))
            }
            if (i < items.lastIndex) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = Accent, modifier = Modifier.size(16.dp).align(Alignment.CenterVertically))
            }
        }
    }
}

@Composable
fun SmallIcon(icon: ImageVector, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Accent, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(4.dp))
        Text(label, fontSize = 12.sp, color = Grey)
    }
}
