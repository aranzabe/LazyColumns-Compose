
* * *

**LazyColumns y LazyGrids en Jetpack Compose**
=========================================================

**1\. Introducción**
--------------------

En **Jetpack Compose**, las listas y grids se manejan de manera declarativa usando componentes llamados **LazyLayouts**:

*   **LazyColumn** → Lista vertical (equivalente a un `RecyclerView` vertical en XML).
*   **LazyRow** → Lista horizontal (equivalente a un `RecyclerView` horizontal).
*   **LazyVerticalGrid / LazyHorizontalGrid** → Grids verticales u horizontales.

La palabra `Lazy` significa que **los elementos se crean bajo demanda**, es decir, solo se renderizan los que son visibles en pantalla, mejorando el rendimiento cuando trabajamos con listas grandes.

* * *

**2\. Componentes básicos**
---------------------------

### **2.1 LazyColumn**

Se utiliza para listas verticales:

```kotlin
LazyColumn(
    verticalArrangement = Arrangement.spacedBy(4.dp), // Espaciado entre items
) {
    item { Text("Cabecera de la lista") }           // Item individual
    items(listaDeDatos) { item ->
        Text(text = "Elemento: $item")             // Elementos de la lista
    }
}
```

*   `item {}` → Sirve para un **único elemento**.
*   `items(lista)` → Sirve para **iterar sobre colecciones**.

### **2.2 LazyRow**

Similar a `LazyColumn` pero horizontal:

```kotlin
LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
    items(listaDeDatos) { item ->
        Text(text = "Elemento: $item")
    }
}
```

### **2.3 LazyVerticalGrid**

Se usa para mostrar elementos en **grillas**:

```kotlin
LazyVerticalGrid(
    columns = GridCells.Fixed(3),               // Número fijo de columnas
    verticalArrangement = Arrangement.spacedBy(4.dp),
    contentPadding = PaddingValues(4.dp)
) {
    items(listaDeUsuarios) { usuario ->
        ItemUsuario(u = usuario)
    }
}
```

*   `columns = GridCells.Fixed(n)` → Número fijo de columnas.
*   `GridCells.Adaptive(minSize)` → Columnas adaptativas según el tamaño mínimo de cada item.

* * *

**3\. Elementos clicables y gestos**
------------------------------------

En Jetpack Compose existen varias formas de detectar **interacciones** con los items:

### **3.1 clickable**

```kotlin
Text("Click aquí", modifier = Modifier.clickable {
    // Acción al click
})
```

### **3.2 combinedClickable**

Permite **click, doble click y long press** en un mismo elemento:

```kotlin
Modifier.combinedClickable(
    onClick = { /* click simple */ },
    onDoubleClick = { /* doble click */ },
    onLongClick = { /* pulsación larga */ }
)
```

### **3.3 pointerInput + detectTapGestures**

Para gestos más complejos:

```kotlin
Modifier.pointerInput(Unit) {
    detectTapGestures(
        onPress = { /* presionado */ },
        onTap = { /* tap */ },
        onDoubleTap = { /* doble tap */ },
        onLongPress = { /* pulsación larga */ }
    )
}
```

> En tu ejemplo, `ItemUsuario` usa `pointerInput` mientras que `ItemUsuarioLista` usa `combinedClickable`. Ambas estrategias son válidas, pero **no deben combinarse con `.clickable`**, porque se excluyen entre sí.

* * *

**4\. Agrupar elementos (Sticky Headers)**
------------------------------------------

Podemos crear **cabeceras pegajosas** para agrupar elementos en la lista:

```kotlin
@OptIn(ExperimentalFoundationApi::class)
LazyColumn {
    usuariosAgrupados.forEach { nombre, listaUsuarios ->
        stickyHeader {
            Text(
                text = nombre,
                modifier = Modifier.fillMaxWidth().background(Color.LightGray),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        items(listaUsuarios) { usuario ->
            ItemUsuarioLista(u = usuario) { u, tipo -> ... }
        }
    }
}
```

*   `stickyHeader {}` → Mantiene la cabecera visible mientras se hace scroll.

* * *

**5\. Estado de la lista y scroll**
-----------------------------------

Podemos recordar **el estado de scroll** usando `rememberLazyListState`:

```kotlin
val rvEstado = rememberLazyListState()
LazyColumn(state = rvEstado) {
    items(listaUsuarios) { usuario ->
        ItemUsuario(u = usuario) { ... }
    }
}
```

*   `rvEstado.firstVisibleItemIndex` → Índice del primer item visible.
*   `rvEstado.firstVisibleItemScrollOffset` → Desplazamiento parcial del primer item.

* * *

**6\. Tipos de LazyLayouts en Jetpack Compose**
-----------------------------------------------

| Componente | Orientación | Uso principal |
| --- | --- | --- |
| `LazyColumn` | Vertical | Listas simples verticales |
| `LazyRow` | Horizontal | Listas simples horizontales |
| `LazyVerticalGrid` | Vertical | Grillas con columnas fijas o adaptativas |
| `LazyHorizontalGrid` | Horizontal | Grillas horizontales (menos común) |

> Todos los LazyLayouts son **“perezosos”**, solo renderizan lo visible.

* * *

**7\. Buenas prácticas**
------------------------

1.  **Evitar cargar listas enormes en memoria**, usar `Lazy` para rendimiento.
2.  **Usar `remember`** para estados de elementos (clicks, selección).
3.  **Separar la UI de la lógica de negocio** (tu función `generarUsuarios()` hace un buen ejemplo de generación de datos de prueba).
4.  **Usar padding y arrangement** para espaciar elementos y mejorar la estética.
5.  **Evitar combinar `clickable` con `pointerInput`** en el mismo elemento.

* * *

**8\. Referencias oficiales**
-----------------------------

*   LazyColumn
*   LazyRow
*   LazyVerticalGrid
*   Gestures y clicks en Compose
*   Compose State

* * *


**9\. Tipos de Lazy en el ejemplo**
-----------------------------------
*   Tipo de LazyLayout
*   Orientación
*   Elementos clicables
*   Notas sobre uso

| Función / Ejemplo | LazyLayout | Orientación | Tipo de click soportado | Notas |
| --- | --- | --- | --- | --- |
| `SimpleRecyclerView()` | `LazyColumn` | Vertical | Click simple (por item) | Lista básica de Strings y números. |
| `RVUsuariosColsSencillo()` | `LazyColumn` | Vertical | Click simple (edad ≥18) | Cada usuario es un `Card` con botón. |
| `RVUsuariosFilas()` | `LazyRow` | Horizontal | Click simple | Muestra usuarios en fila horizontal. |
| `GridUsuarios()` | `LazyVerticalGrid` | Vertical | Click simple | Grid de 3 columnas, padding de items ajustable. |
| `RVUsuariosCols()` | `LazyColumn` | Vertical | Click / DoubleClick / LongClick | Cada item usa `ItemUsuarioLista2` con `combinedClickable`. |
| `RVUsuariosSticky()` | `LazyColumn` | Vertical | Click / LongClick / DoubleClick | Lista con **sticky headers** agrupando por nombre. |
| `RVUsuariosControlsView()` | `LazyColumn` | Vertical | Click / LongClick / DoubleClick | Permite controlar scroll con `rememberLazyListState`. |
| `ItemUsuario()` | `Card` dentro de LazyLayouts | Vertical | Click / LongPress / DoubleTap (via `pointerInput`) | Ejemplo de gestos con `detectTapGestures`. |
| `ItemUsuarioLista()` | `Card` dentro de LazyLayouts | Vertical | Click / LongClick / DoubleClick / Botón | Usa `combinedClickable` y botón independiente para seleccionar. |

