package ch.xlan.bookin.ui.screens

import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ch.xlan.bookin.model.Book
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import nl.siegmann.epublib.domain.Book as EpubBook
import nl.siegmann.epublib.epub.EpubReader
import java.io.File
import java.io.FileInputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderScreen(book: Book, onBack: () -> Unit) {
    val context = LocalContext.current

    var epubBook by remember { mutableStateOf<EpubBook?>(null) }
    var currentChapterIndex by remember { mutableIntStateOf(0) }
    var chapterHtml by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(book.id) {
        withContext(Dispatchers.IO) {
            try {
                val file = File(context.filesDir, "book_${book.id}.epub")
                if (file.exists()) {
                    val fis = FileInputStream(file)
                    epubBook = EpubReader().readEpub(fis)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(epubBook, currentChapterIndex) {
        epubBook?.let { epub ->
            withContext(Dispatchers.IO) {
                try {
                    val spine = epub.spine.spineReferences
                    if (spine.isNotEmpty() && currentChapterIndex < spine.size) {
                        val resource = spine[currentChapterIndex].resource
                        chapterHtml = String(resource.data)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book.title, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quitter")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { if (currentChapterIndex > 0) currentChapterIndex-- },
                        enabled = currentChapterIndex > 0
                    ) {
                        Text("Précédent")
                    }

                    TextButton(
                        onClick = {
                            epubBook?.let {
                                if (currentChapterIndex < it.spine.spineReferences.size - 1) {
                                    currentChapterIndex++
                                }
                            }
                        },
                        enabled = epubBook != null && currentChapterIndex < (epubBook?.spine?.spineReferences?.size
                            ?: 0) - 1
                    ) {
                        Text("Suivant")
                    }
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (chapterHtml.isNotEmpty()) {
                AndroidView(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp),
                    factory = { ctx ->
                        WebView(ctx).apply {
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = false
                        }
                    },
                    update = { webView ->
                        webView.loadDataWithBaseURL(null, chapterHtml, "text/html", "UTF-8", null)
                    }
                )
            } else {
                Text("Erreur lors du chargement du livre.", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}
