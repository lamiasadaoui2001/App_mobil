import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.YearMonth
import java.time.DayOfWeek


val LavenderBlue = Color(0xFF7F9CF5)
val MintGreen = Color(0xFF9AE8C4)
val LightGrayBox = Color(0xFFEAEAEA)

@Composable
fun HabitDetailScreen(
    habitName: String,
    validatedDays: SnapshotStateList<LocalDate>,
    onBack: () -> Unit
) {
    val currentStreak = calculateCurrentStreak(validatedDays)
    val daysInMonth = YearMonth.now().lengthOfMonth()
    val totalCompleted = validatedDays.size
    val successRate = if (totalCompleted == 0) 0 else (totalCompleted * 100 / daysInMonth)

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        // Bouton retour
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Retour",
                modifier = Modifier.clickable { onBack() }
            )
        }

        Spacer(Modifier.height(20.dp))

        // Nom de l'habitude
        Text(habitName, style = MaterialTheme.typography.h4)

        Spacer(Modifier.height(16.dp))

        // Streak + total jours complétés + taux de réussite
        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("Streak: $currentStreak jours", color = LavenderBlue)
            Text("Jours complétés: $totalCompleted", color = LavenderBlue)
            Text("Taux de réussite: $successRate%", color = LavenderBlue)
        }

        Spacer(Modifier.height(20.dp))

        // Calendrier
        CalendarView(validatedDays)

        Spacer(Modifier.height(20.dp))

        // Progression hebdomadaire
        Text("Progression Hebdomadaire", color = LavenderBlue, fontSize = 16.sp)
        WeekBarChart(validatedDays)
    }
}

@Composable
fun CalendarView(validatedDays: List<LocalDate>) {
    val currentMonth = YearMonth.now()
    val days = (1..currentMonth.lengthOfMonth()).toList()

    days.chunked(7).forEach { week ->
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            week.forEach { day ->
                val date = LocalDate.of(currentMonth.year, currentMonth.month, day)
                DayBox(day, date in validatedDays)
            }
        }
        Spacer(Modifier.height(6.dp))
    }
}

@Composable
fun DayBox(day: Int, isValidated: Boolean) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                if (isValidated) MintGreen else LightGrayBox,
                shape = RoundedCornerShape(6.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(day.toString())
    }
}

@Composable
fun WeekBarChart(validatedDays: List<LocalDate>) {
    val today = LocalDate.now()
    val startOfWeek = today.with(DayOfWeek.MONDAY)
    val weekDates = (0..6).map { startOfWeek.plusDays(it.toLong()) }

    val dayLabels = listOf("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim")

    Row(Modifier.fillMaxWidth().height(180.dp)) {
        // Axe Y
        Column(
            Modifier.width(30.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Text("100%", fontSize = 10.sp)
            Text("75%", fontSize = 10.sp)
            Text("50%", fontSize = 10.sp)
            Text("25%", fontSize = 10.sp)
            Text("0%", fontSize = 10.sp)
        }

        Spacer(Modifier.width(8.dp))

        // Barres + labels X
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            weekDates.forEachIndexed { index, date ->
                val isCompleted = validatedDays.any { it == date }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Barre
                    Box(
                        modifier = Modifier
                            .height(if (isCompleted) 120.dp else 24.dp)
                            .width(20.dp)
                            .background(
                                if (isCompleted) MintGreen else LightGrayBox,
                                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                            )
                    )

                    Spacer(Modifier.height(4.dp))

                    // Label X
                    Text(dayLabels[index], fontSize = 12.sp)
                }
            }
        }
    }
}

fun calculateCurrentStreak(dates: List<LocalDate>): Int {
    if (dates.isEmpty()) return 0
    val sorted = dates.map { it.dayOfMonth }.sorted()
    var streak = 1
    var max = 1
    for (i in 1 until sorted.size) {
        if (sorted[i] == sorted[i - 1] + 1) streak++ else streak = 1
        if (streak > max) max = streak
    }
    return max
}
