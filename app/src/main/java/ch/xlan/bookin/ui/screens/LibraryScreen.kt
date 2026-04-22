package ch.xlan.bookin.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.xlan.bookin.model.Book
import ch.xlan.bookin.ui.components.BookItem
import ch.xlan.bookin.ui.components.SearchBar

@Composable
fun LibraryScreen(
    books: List<Book>,
    onBookClick: (Book) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* TODO */ }) {
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
            Text(text = "Bibliothèque", fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            SearchBar(query = searchQuery, onQueryChange = { searchQuery = it })

            Spacer(modifier = Modifier.height(24.dp))

            val filteredBooks = books.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.author.contains(searchQuery, ignoreCase = true)
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(filteredBooks) { book ->
                    BookItem(book = book, onClick = { onBookClick(book) })
                }
            }
        }
    }
}
