package com.example.bookshelf.ui.screens
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookshelf.BookshelfApplication
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.BookshelfStatus
import com.example.bookshelf.ui.BookshelfViewModel

// Paso 10: Compose obtiene el ViewModel, observa sus flujos y elige qué contenido dibujar.
@Composable
fun BookshelfScreen(modifier: Modifier = Modifier) {
    val application = LocalContext.current.applicationContext as BookshelfApplication
    val factory = remember(application) {
        BookshelfViewModel.factory(application.container.booksRepository)
    }
    val viewModel: BookshelfViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val books by viewModel.books.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.bookshelf_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            when (uiState.status) {
                BookshelfStatus.LOADING -> LoadingContent()
                BookshelfStatus.ERROR -> ErrorContent(onRetry = viewModel::getBooks)
                BookshelfStatus.SUCCESS -> BooksContent(books)
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = stringResource(R.string.books_loading))
        }
    }
}

@Composable
private fun ErrorContent(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = stringResource(R.string.books_error))
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRetry) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Composable
private fun BooksContent(books: List<Book>) {
    // Por ahora solo se presenta la URL como texto; cargar las portadas será un paso posterior.
    val firstBookWithCover = books.firstOrNull { it.coverUrl != null }
    if (firstBookWithCover == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(
                    if (books.isEmpty()) R.string.books_empty else R.string.cover_url_unavailable
                )
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.first_book_cover_url_label),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = firstBookWithCover.coverUrl.orEmpty(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
