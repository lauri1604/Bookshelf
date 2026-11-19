package com.example.bookshelf
import android.app.Application
import com.example.bookshelf.data.AppContainer
import com.example.bookshelf.data.DefaultAppContainer

// Paso 9: crear el contenedor al iniciar la app para que la UI pueda obtener sus dependencias.
class BookshelfApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
