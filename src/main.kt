import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.dp
@Composable
@Preview
fun App() {


    var currentScreen by remember { mutableStateOf("onboarding") }

    // Habits by day : toutes tes habitudes déjà présentes
    val habitsByDay = remember { mutableStateListOf(*Array(7) { mutableStateListOf<Task>() }) }

    // Remplissage des habitudes existantes (copie de ton code)
    LaunchedEffect(Unit) {
        habitsByDay[0].addAll(
            listOf(
                Task("Sport 1h", Icons.Default.RunCircle, "in_progress"),
                Task("Lire 30 min", Icons.Default.MenuBook, "done"),
                Task("Boire un verre d’eau", Icons.Default.InvertColors, "done"),
                Task("Pratiquer 1 compétence", Icons.Default.TipsAndUpdates, "done"),
                Task("Limiter réseaux < 1h", Icons.Default.NoAccounts, "done"),
                Task("Noter 3 réussites", Icons.Default.Star, "done")
            )
        )
        habitsByDay[1].addAll(listOf(
            Task("Sport 1h", Icons.Default.RunCircle, "done"),
            Task("Méditer 15 min", Icons.Default.SelfImprovement, "in_progress")
        ))
        habitsByDay[2].addAll(listOf(
            Task("Faire 10 min de marche", Icons.Default.DirectionsWalk, "done"),
            Task("Lire 20 min", Icons.Default.MenuBook, "in_progress")
        ))
        habitsByDay[3].addAll(listOf(
            Task("Sport 30 min", Icons.Default.RunCircle, "in_progress"),
            Task("Boire un verre d’eau", Icons.Default.InvertColors, "done"),
            Task("Compétence 1h", Icons.Default.TipsAndUpdates, "done")
        ))
        habitsByDay[4].addAll(listOf(
            Task("Méditer 10 min", Icons.Default.SelfImprovement, "done"),
            Task("Lire 20 min", Icons.Default.MenuBook, "in_progress")
        ))
        habitsByDay[5].addAll(listOf(
            Task("Sport 1h", Icons.Default.RunCircle, "not_done"),
            Task("Repos", Icons.Default.SelfImprovement, "done")
        ))
        habitsByDay[6].addAll(listOf(
            Task("Repos", Icons.Default.SelfImprovement, "done")
        ))
    }

    val createTask: (String) -> Task = { name ->
        Task(label = name, iconLeft = Icons.Default.FavoriteBorder, state = "not_done")
    }

    MaterialTheme {
        when (currentScreen) {
            "onboarding" -> OnboardingScreen(onStart = { currentScreen = "home" })
            "home" -> HomeScreen(
                habitsByDay = habitsByDay,
                onAddHabit = { currentScreen = "newHabit" }
            )
            "newHabit" -> NewHabitScreen(
                onBack = { currentScreen = "home" },
                onHabitSave = { habitName, habitTool, selectedDays ->
                    val newTask = Task(
                        label = habitName,
                        iconLeft = getIconForHabit(habitName),
                        state = "not_done" ,
                        tool = habitTool  // ← ici
                    )
                    selectedDays.forEach { dayIndex ->
                        if (dayIndex in 0..6) habitsByDay[dayIndex].add(newTask)
                    }
                    currentScreen = "home"
                }
            )
        }
    }

}

fun main() = application {
    Window(
        title = "Mobile Preview",
        state = rememberWindowState(width = 430.dp, height = 700.dp),
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
