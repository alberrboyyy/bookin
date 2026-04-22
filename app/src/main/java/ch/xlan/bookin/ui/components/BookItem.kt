package ch.xlan.bookin.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.xlan.bookin.model.Book

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 80.dp, height = 120.dp)
                .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = book.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = book.author,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }
    }
}
