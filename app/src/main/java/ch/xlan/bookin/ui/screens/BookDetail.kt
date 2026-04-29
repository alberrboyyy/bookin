package ch.xlan.bookin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.xlan.bookin.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(book: Book, onBack: () -> Unit, showMessage: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    // On récupère le contexte pour accéder au stockage caché
    val context = LocalContext.current

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
                text = book.description ?: "Aucune description disponible.",
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    coroutineScope.launch {
                        try {
                            showMessage("Downloading")

                            withContext(Dispatchers.IO) {
                                val url = URL("http://10.0.200.3:6732/book/${book.id}/file")
                                val connection = url.openConnection()
                                connection.connect()

                                val file = File(context.filesDir, "${book.title}.epub")

                                connection.getInputStream().use { input ->
                                    file.outputStream().use { output ->
                                        input.copyTo(output)
                                    }
                                }
                            }

                        } catch (e: Exception) {
                            showMessage("${e.message}")
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
        }
    }
}
