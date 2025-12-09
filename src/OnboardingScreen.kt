import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(onStart: () -> Unit)
{

    // Dégradé de fond (Violet pâle à Vert Menthe pâle)
    val gradientBackground = Brush.radialGradient(
        colors = listOf(
            Color(0xFFB5A7F9),
            Color(0xFF9AE8C4)
        ),
        center = Offset(600f, 300f),
        radius = 900f
    )

    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(0.85f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Illustration centrale (cœur dans le cercle)
            IllustrationSection()

            Spacer(Modifier.height(30.dp))

            // Titre
            Text(
                text = "Construisez votre\nmeilleure version pour une vie meilleure",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B1B1B),
                lineHeight = 36.sp,
                textAlign = TextAlign.Center
            )

            // Sous-titre
            Text(
                text = "Suivez vos objectifs, jour après jour,\navec simplicité et sérénité.",
                fontSize = 14.sp,
                color = Color(0xFF4A4A4A),
                lineHeight = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            // Bouton
            Button(
                onClick = { onStart()},
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9D84FF)),
                shape = CircleShape,
                elevation = ButtonDefaults.elevation(0.dp)
            ) {
                Text(
                    text = "Commenter l'Aventure",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }
            // Après le bouton, ajoute ce Row pour les indicateurs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PageIndicator(isActive = false, color = Color.White)
                Spacer(Modifier.width(8.dp))
                PageIndicator(isActive = true, color = Color.White)
                Spacer(Modifier.width(8.dp))
                PageIndicator(isActive = false, color = Color.White)
            }
        }
    }
}

@Composable
fun IllustrationSection() {
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {

        // Halo blanc progressif (glow)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val maxRadius = size.minDimension / 2

            val haloColors = listOf(0.3f, 0.2f, 0.1f)
            val haloRadii = listOf(maxRadius * 1f, maxRadius * 1.1f, maxRadius * 1.2f)

            haloColors.zip(haloRadii).forEach { (alpha, radius) ->
                drawCircle(
                    color = Color.White.copy(alpha = alpha),
                    radius = radius
                )
            }
        }

        // Cercle principal avec dégradé violet -> turquoise
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFB5A7F9),
                            Color(0xFF9AE8C4)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Bord blanc très fin autour du cercle
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.8f),
                    style = Stroke(width = 6f)
                )
            }

            // Icône cœur
            HeartIconWhite(modifier = Modifier.size(100.dp))
        }
    }
}

/** Dessine un cœur stylisé sur un Canvas. */
@Composable
fun HeartIconWhite(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val stroke = 5.dp.toPx()
        val color = Color.White

        val path = Path().apply {
            moveTo(w * 0.5f, h * 0.75f) // bas du cœur

            cubicTo(
                w * 0.2f, h * 0.55f,  // contrôle gauche
                w * 0.25f, h * 0.25f, // point haut gauche
                w * 0.5f, h * 0.35f    // sommet du cœur
            )

            cubicTo(
                w * 0.75f, h * 0.25f, // point haut droit
                w * 0.8f, h * 0.55f,  // contrôle droit
                w * 0.5f, h * 0.75f   // retour au bas
            )

            close()
        }

        drawPath(path, color, style = Stroke(stroke))
    }
}
@Composable
fun PageIndicator(isActive: Boolean, color: Color) {
    val size = if (isActive) 10.dp else 8.dp
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}