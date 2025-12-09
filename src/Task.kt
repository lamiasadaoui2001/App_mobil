import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

data class Task(
    val label: String,
    val iconLeft: ImageVector,
    val state: String, // "done" | "in_progress" | "not_done",
    val tool: String = ""
)
fun getIconForHabit(habitName: String): ImageVector {
    return when (habitName) {
        "Sport 1h", "Sport 30 min" -> Icons.Default.RunCircle
        "Lire 20 min", "Lire 30 min" -> Icons.Default.MenuBook
        "Boire un verre d’eau", "Boire 2L d'eau" -> Icons.Default.InvertColors
        "Méditer 10 min", "Méditer 15 min" -> Icons.Default.SelfImprovement
        "Pratiquer 1 compétence", "Compétence 1h" -> Icons.Default.TipsAndUpdates
        "Limiter réseaux < 1h" -> Icons.Default.NoAccounts
        "Noter 3 réussites" -> Icons.Default.Star
        "Repos" -> Icons.Default.Bedtime
        else -> Icons.Default.FavoriteBorder
    }
}