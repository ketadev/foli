# Foli

**Leer un poco hoy hace más fácil volver mañana.** Foli es una aplicación móvil para Android e iOS que ayuda a construir un hábito de lectura con sesiones breves, metas de páginas elegidas por la persona y progreso visible. Cuando una lectura deja algo valioso, también permite guardar una idea y convertirla en una acción.

## ¿Por qué Foli?

Empezar a leer suele costar más que leer unas páginas. Los libros pendientes se acumulan, el avance pasa inadvertido y un registro demasiado exigente convierte el hábito en otra tarea. Foli busca acortar el camino entre **«quiero leer»** y **«estoy leyendo»**: abrir la app, continuar un libro, registrar el avance y tener una razón amable para regresar al día siguiente.

La prioridad es la constancia, no el tiempo dentro de la aplicación. La experiencia se plantea como personal, tranquila y privada, sin rankings ni actividad pública.

## Características principales

### 📚 Tu biblioteca, a tu ritmo

Agrega libros manualmente o búscalos en un catálogo. Organiza tus pendientes y mantén hasta dos lecturas activas. Para cada libro, elige si quieres *solo leer*, *leer y recordar* o *leer y aplicar*.

### 📖 Sesiones fáciles de empezar

Fija una meta de páginas, lee sin distracciones y registra hasta dónde llegaste. Al cerrar la sesión, verás de inmediato cuánto avanzaste en el libro.

### 🌱 Motivación para volver

Sigue tu constancia con rachas, pequeños logros y recordatorios respetuosos. Si interrumpes una racha, podrás recuperarla sin que volver a leer se sienta como un castigo.

### 💭 Ideas que puedes conservar

Después de leer, guarda una idea o reflexión si algo merece recordarse. Este paso es opcional: tu sesión ya estará completada.

### ⚡ De una idea a una acción

Cuando quieras aplicar lo aprendido, convierte una idea en una acción concreta y sigue su resultado. Una sugerencia de IA editable podrá ayudarte a definirla, sin decidir por ti.

El recorrido principal sigue siendo simple: **elegir libro → fijar una meta → leer → registrar el avance → volver a leer**.

## Tecnología y arquitectura

Foli parte de **Kotlin Multiplatform** para compartir lógica entre Android e iOS y de **Compose Multiplatform + Material 3** para la interfaz. La propuesta del MVP organiza el código en módulos de funciones (`library`, `reading`, `streaks`, `ideas`, `actions`) y módulos centrales para modelos, datos, base de datos, diseño y adaptadores de plataforma. `shared` ensambla la navegación y las dependencias; `androidApp` e `iosApp` son las entradas nativas.

| Necesidad | Elección definida |
| --- | --- |
| Estado y tareas asíncronas | ViewModel multiplataforma, Coroutines y Flow |
| Navegación | Navigation Compose multiplataforma con rutas tipadas |
| Datos locales | Room KMP sobre SQLite; DataStore Preferences para ajustes pequeños |
| Catálogo y red | Google Books detrás de un contrato propio; Ktor Client y Kotlin Serialization |
| Portadas | Coil 3 |
| Dependencias | Koin, con definiciones compartidas y adaptadores por plataforma |
| Recordatorios | Notificaciones locales mediante adaptadores nativos |
| Diseño | Tokens en [`design/tokens/design-tokens.json`](design/tokens/design-tokens.json) |

> **Estado del repositorio:** actualmente solo están configurados `androidApp` y `shared`, con la pantalla inicial de la plantilla. Los módulos, flujos y dependencias de la tabla describen la arquitectura **propuesta**, no funciones ya terminadas.

Las decisiones y límites completos están en la [arquitectura](docs/architecture.md); el problema, los flujos y el alcance del producto están en el [PRD](docs/foli-prd.md).

## Ejecutar el proyecto actual

- **Android:** `./gradlew :androidApp:assembleDebug` o ejecutar `androidApp` desde el IDE.
- **iOS:** abrir [`iosApp/iosApp.xcodeproj`](iosApp/iosApp.xcodeproj) en Xcode y ejecutar la app.
