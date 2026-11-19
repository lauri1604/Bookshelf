# MEMORY.md — Bookshelf
## Estado actual
- La pantalla consulta Open Library (`search.json?q=jazz history`, límite de 20) usando Retrofit y coroutines.
- Estructura MVVM manual: `model`, `network`, `data` (repositorio y contenedor), `ui` (ViewModel y estados).
- `BookshelfUiState` contiene solo el estado de carga/error/éxito; la lista de libros se expone aparte desde el ViewModel.
- La pantalla muestra carga, error y reintento; en éxito muestra como texto la URL de portada del primer libro que trae `cover_i`.
- `GreetingScreen.kt` de la plantilla se eliminó; la pantalla activa es `BookshelfScreen`.
- La carga de portadas está pendiente; todavía no integrar Coil.
- Los comentarios numerados explican el recorrido empezando por UiState y ViewModel, luego network y las capas de datos.
## Decisiones
- Derivar la URL de Open Library Covers desde `cover_i` y mostrarla en texto; la descarga/renderizado queda para una tarea posterior.
- La inyección manual sigue el contenedor y la factory descritos en los apuntes de conexión a Internet.