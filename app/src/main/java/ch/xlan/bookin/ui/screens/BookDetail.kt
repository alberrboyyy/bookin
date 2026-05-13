package ch.xlan.bookin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.xlan.bookin.model.Book
import ch.xlan.bookin.network.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    book: Book,
    onBack: () -> Unit,
    onRead: () -> Unit,
    showMessage: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val bookFile = File(context.filesDir, "book_${book.id}.epub")
    var isDownloaded by remember { mutableStateOf(false) }

    LaunchedEffect(book.id) {
        isDownloaded = bookFile.exists()
    }

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
                text = book.summary ?: "Aucune description disponible.",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            if (isDownloaded) {
                Button(
                    onClick = onRead,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Lecture", fontSize = 18.sp)
                }
            } else if (book.epubFileUrl != null) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                val url = book.epubFileUrl
                                withContext(Dispatchers.IO) {
                                    val response = ApiClient.instance.downloadBook(url)
                                    response.byteStream().use { input ->
                                        bookFile.outputStream().use { output ->
                                            input.copyTo(output)
                                        }
                                    }
                                }
                                isDownloaded = true
                            } catch (e: Exception) {
                                e.printStackTrace()
                                showMessage("Erreur : ${e.localizedMessage}")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Télécharger", fontSize = 18.sp)
                }
            } else {
                Text(
                    "Aucun fichier disponible",
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    color = Color.Red,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}
