package ch.xlan.bookin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ch.xlan.bookin.model.Book
import ch.xlan.bookin.ui.screens.LibraryScreen
import ch.xlan.bookin.ui.theme.BookInTheme

val sampleBooks = listOf(
    Book(1, "Le Seigneur des Anneaux", "J.R.R. Tolkien", listOf("Fantasy", "Aventure")),
    Book(2, "Le Petit Prince", "Antoine de Saint-Exupéry", listOf("Conte")),
    Book(3, "1984", "George Orwell", listOf("Dystopie")),
    Book(4, "Le Grimoire d'Arkandias", "Eric Boisset", listOf("Jeunesse", "Magie"))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookInTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LibraryScreen(
                        books = sampleBooks,
                        onBookClick = {  }
                    )
                }
            }
        }
    }
}