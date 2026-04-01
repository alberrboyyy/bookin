package ch.xlan.bookin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.xlan.bookin.model.Book
import ch.xlan.bookin.ui.components.BookItem
import ch.xlan.bookin.ui.theme.BookInTheme

val sampleBooks = listOf(
    Book(1, "Le Chuchotement des Étoiles", "Jules Verne", listOf("Science-Fiction", "Aventure")),
    Book(2, "Policier & Thriller", "Jules Verne", listOf("Littérature Blanche")),
    Book(3, "La Fantaisie des lières", "Jules Verne", listOf("Fantaisie")),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookInTheme {
                LibraryScreen(books = sampleBooks)
            }
        }
    }
}

@Composable
fun LibraryScreen(books: List<Book>) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO: Modale des filtres */ }) {
                Text("Y")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Bibliothèque",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Barre de recherche à venir...", color = MaterialTheme.colorScheme.secondary)

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(books) { book ->
                    BookItem(book = book)
                }
            }
        }
    }
}