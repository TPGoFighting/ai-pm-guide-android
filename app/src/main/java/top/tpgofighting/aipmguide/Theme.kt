package top.tpgofighting.aipmguide

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val Paper = Color(0xFFF3EFE6)
val Paper2 = Color(0xFFFAF8F2)
val Ink = Color(0xFF1C1C1E)
val InkSoft = Color(0xFF3A3A3C)
val Grey = Color(0xFF8E8E93)
val Accent = Color(0xFFFF6B35)
val AccentSoft = Color(0xFFFFF0E9)
val DarkCard = Color(0xFF22231F)
val Gold = Color(0xFFF7B32B)

/** 手绘不规则圆角：每边不一样，像手画的卡片 */
val SketchShape = RoundedCornerShape(topStart = 24.dp, topEnd = 4.dp, bottomEnd = 20.dp, bottomStart = 4.dp)
val SketchShape2 = RoundedCornerShape(topStart = 4.dp, topEnd = 20.dp, bottomEnd = 4.dp, bottomStart = 24.dp)

private val Scheme = lightColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    background = Paper,
    onBackground = Ink,
    surface = Color.White,
    onSurface = Ink,
    surfaceVariant = Paper2,
    onSurfaceVariant = InkSoft,
    outline = Ink,
)

@Composable
fun GuideTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Scheme, content = content)
}
