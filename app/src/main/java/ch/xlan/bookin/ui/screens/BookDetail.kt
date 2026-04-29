package ch.xlan.bookin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.xlan.bookin.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(book: Book, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Détails") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = book.title, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(text = book.author, fontSize = 20.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = book.description ?: "Aucune description disponible pour ce livre.",
                fontSize = 16.sp
            )
        }
    }
}
