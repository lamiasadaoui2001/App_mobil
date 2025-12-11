import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.mutableStateListOf
import java.time.LocalDate
import java.time.DayOfWeek
import java.time.YearMonth


data class Task(
    val label: String,
    val iconLeft: ImageVector,
    var state: String,
    val validatedDays: SnapshotStateList<LocalDate> = mutableStateListOf(), // ← mutable dès le départ
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