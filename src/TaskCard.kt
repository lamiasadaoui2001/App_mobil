import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Pause
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Delete

val PrimaryPurple = Color(0xFF6C63FF)

@Composable
fun TaskCard(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit , // ← Ajout de ce paramètre
    onClick: () -> Unit
) {
    val isDone = task.state == "done"
    val iconRight = if (isDone) Icons.Default.Check else Icons.Default.Pause
    val bgRight = when (task.state) {
        "done" -> Color(0xFF4CAF50)
        "in_progress" -> PrimaryPurple
        else -> Color(0xFFF44336)
    }

    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(task.iconLeft, contentDescription = null, tint = Color.Black, modifier = Modifier.size(30.dp))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(task.label, fontSize = 18.sp)
                    if (task.tool.isNotBlank()) {
                        Text(
                            task.tool,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(bgRight, CircleShape)
                        .clickable { onToggle() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(iconRight, contentDescription = null, tint = Color.White)
                }

                // Bouton supprimer
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Gray)
                }
            }
        }
    }
}