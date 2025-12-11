import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import androidx.compose.material.icons.Icons

val AppBackgroundGradient = Brush.radialGradient(
    colors = listOf(
        Color(0xFFB5A7F9), // violet pastel
        Color(0xFF9AE8C4)  // vert pastel
    ),
    center = Offset(600f, 300f),
    radius = 900f
)

val PrimaryPurpleHom = Color(0xFF6C63FF)

@Composable
fun HomeScreen(
    habitsByDay: List<MutableList<Task>>,
    onAddHabit: () -> Unit,
    onHabitClick: (Task, List<LocalDate>) -> Unit
) {

    // 🔥 BACKGROUND ICI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackgroundGradient)
    ) {

        // 👉 TOUT ton HomeScreen est dedans maintenant
        val today = LocalDate.now()
        val startOfWeek = today.with(DayOfWeek.MONDAY)
        val weekDays = (0..6).map { startOfWeek.plusDays(it.toLong()) }

        var selectedDayIndex by remember { mutableStateOf(today.dayOfWeek.value - 1) }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text("Mon Focus", style = MaterialTheme.typography.h4)
            Text("Cette semaine", style = MaterialTheme.typography.h5)
            Spacer(Modifier.height(20.dp))

            // Affichage des jours
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                weekDays.forEachIndexed { index, day ->
                    val allDone = habitsByDay[index].all { it.state == "done" }
                    val color = when {
                        index == selectedDayIndex -> Color(0xFFFF9800)
                        allDone -> Color(0xFF4CAF50)
                        else -> Color(0xFFF44336)
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .border(BorderStroke(2.dp, color), CircleShape)
                            .clickable { selectedDayIndex = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(day.dayOfMonth.toString())
                    }
                }
            }

            Spacer(Modifier.height(25.dp))

            // Liste des tâches
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                itemsIndexed(habitsByDay[selectedDayIndex]) { habitIndex, task ->
                    TaskCard(
                        task = task,
                        onToggle = {
                            val newState =
                                if (task.state == "done") "in_progress" else "done"
                            val updatedTask = task.copy(state = newState)

                            val selectedDate = weekDays[selectedDayIndex]

                            if (newState == "done") {
                                if (!updatedTask.validatedDays.contains(selectedDate)) {
                                    updatedTask.validatedDays.add(selectedDate)
                                }
                            } else {
                                updatedTask.validatedDays.remove(selectedDate)
                            }

                            habitsByDay[selectedDayIndex][habitIndex] = updatedTask
                        },
                        onDelete = {
                            habitsByDay[selectedDayIndex].removeAt(habitIndex)
                        },
                        onClick = {
                            val totalValidated =
                                getTotalValidatedDays(task, habitsByDay)
                            onHabitClick(task, totalValidated)
                        }
                    )
                }

                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        FloatingActionButton(
                            onClick = onAddHabit,
                            backgroundColor = PrimaryPurpleHom,
                            contentColor = Color.White
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = "Ajouter une habitude"
                            )
                        }
                    }
                }
            }
        }
    }
}

// Fusion des jours validés
fun getTotalValidatedDays(task: Task, habitsByDay: List<List<Task>>): List<LocalDate> {
    val validated = mutableSetOf<LocalDate>()
    habitsByDay.flatten()
        .filter { it.label == task.label }
        .forEach { validated.addAll(it.validatedDays) }
    return validated.toList()
}
