package com.example.bookshelf.ui.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bookshelf.R
import com.example.bookshelf.model.Book
import com.example.bookshelf.ui.BookshelfStatus
import com.example.bookshelf.ui.BookshelfUiState
import com.example.bookshelf.ui.BookshelfViewModel

@Composable
fun BookshelfRoute(modifier: Modifier = Modifier) {
    val viewModel: BookshelfViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BookshelfScreen(
        uiState = uiState,
        onRetry = viewModel::getBooks,
        modifier = modifier
    )
}

@Composable
fun BookshelfScreen(
    uiState: BookshelfUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.status) {
                BookshelfStatus.LOADING -> LoadingContent()
                BookshelfStatus.ERROR -> ErrorContent(onRetry = onRetry)
                BookshelfStatus.SUCCESS -> BooksContent(uiState.books)
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
        AsyncImage(
            model = firstBookWithCover.coverUrl,
            contentDescription = firstBookWithCover.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}
