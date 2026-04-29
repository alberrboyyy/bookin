package ch.xlan.bookin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.xlan.bookin.model.Book
import ch.xlan.bookin.network.ApiClient
import ch.xlan.bookin.ui.screens.LibraryScreen
import ch.xlan.bookin.ui.theme.BookInTheme
import ch.xlan.bookin.ui.screens.BookDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookInTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var apiBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
                    var isLoading by remember { mutableStateOf(true) }
                    var errorMessage by remember { mutableStateOf<String?>(null) }

                    LaunchedEffect(Unit) {
                        try {
                            apiBooks = ApiClient.instance.getBooks()
                        } catch (e: Exception) {
                            errorMessage = e.toString()
                        } finally {
                            isLoading = false
                        }
                    }

                    if (isLoading) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else if (errorMessage != null) {
                        Box(modifier = Modifier.fillMaxSize().padding(16.dp), contentAlignment = Alignment.Center) {
                            Text(text = "Erreur: $errorMessage")
                        }
                    } else {
                        LibraryApp(books = apiBooks)
                    }
                }
            }
        }
    }
}

@Composable
fun LibraryApp(books: List<Book>) {
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    if (selectedBook == null) {
        LibraryScreen(
            books = books,
            onBookClick = { book ->
                selectedBook = book
            }
        )
    } else {
        BookDetailScreen(
            book = selectedBook!!,
            onBack = {
                selectedBook = null
            }
        )
    }
}
