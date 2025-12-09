import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val PrimaryPurpleHome = Color(0xFF6C63FF)

val defaultHabits = listOf(
    "Boire 2L d'eau", "Faire du sport", "Lire 20 min", "Méditer 10 min"
)

@Composable
fun NewHabitScreen(
    onBack: () -> Unit,
    onHabitSave: (habitName: String, habitTool: String, selectedDays: List<Int>) -> Unit
) {
    var habitName by remember { mutableStateOf("") }
    var habitTool by remember { mutableStateOf("") }
    var selectedFrequency by remember { mutableStateOf("Jours spécifiques") }
    val daysOfWeek = listOf("L", "M", "M", "J", "V", "S", "D")
    var selectedDays by remember { mutableStateOf(setOf<Int>()) }
    var showDropdown by remember { mutableStateOf(false) }

    LaunchedEffect(selectedFrequency) {
        if (selectedFrequency == "Tous les jours") {
            selectedDays = (0..6).toSet()
        } else if (selectedFrequency == "Jours spécifiques" && selectedDays.isEmpty()) {
            val todayIndex = java.time.LocalDate.now().dayOfWeek.value - 1
            selectedDays = setOf(todayIndex)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nouvelle Habitude") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                backgroundColor = Color.White,
                contentColor = Color.Black,
                elevation = 0.dp
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = habitName,
                onValueChange = { habitName = it },
                label = { Text("Nom de l'habitude") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { showDropdown = !showDropdown }
                    )
                }
            )

            if (showDropdown) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray.copy(alpha = 0.2f))
                        .padding(8.dp)
                ) {
                    defaultHabits.forEach { habit ->
                        Text(
                            text = habit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    habitName = habit
                                    habitTool = habit // ← ici on met l’outil automatiquement
                                    showDropdown = false
                                }
                                .padding(8.dp)
                        )
                    }

                }
            }

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = habitTool,
                onValueChange = { habitTool = it },
                label = { Text("Outil / Suivi (ex: Boire 2L d'eau)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )


            Spacer(Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Tous les jours", "Jours spécifiques").forEach { freq ->
                    val isSelected = freq == selectedFrequency
                    Button(
                        onClick = { selectedFrequency = freq },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = if (isSelected) PrimaryPurpleHome else Color(0xFFEEEEEE),
                            contentColor = if (isSelected) Color.White else Color.Black
                        ),
                        shape = RoundedCornerShape(20.dp),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) { Text(freq, fontSize = 12.sp) }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (selectedFrequency == "Jours spécifiques") {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    daysOfWeek.forEachIndexed { index, day ->
                        val isSelected = selectedDays.contains(index)
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = if (isSelected) PrimaryPurpleHome else Color(0xFFEEEEEE),
                                    shape = CircleShape
                                )
                                .clickable {
                                    selectedDays = if (isSelected) selectedDays - index else selectedDays + index
                                },
                            contentAlignment = Alignment.Center
                        ) { Text(day, color = if (isSelected) Color.White else Color.Black, fontSize = 14.sp) }
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    val daysToSave = selectedDays.toList()
                    onHabitSave(habitName, habitTool, daysToSave)
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryPurpleHome),
                shape = RoundedCornerShape(12.dp),
                enabled = habitName.isNotBlank() && selectedDays.isNotEmpty()
            ) { Text("Enregistrer l'habitude", color = Color.White, fontSize = 18.sp) }
        }
    }
}
