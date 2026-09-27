# Arquitectura del MVP de Foli

**Estado:** decisión de arquitectura propuesta para el MVP. Este documento describe la estructura objetivo; los módulos y dependencias todavía no están creados en Gradle.

## Objetivo y límites

Foli será una aplicación Android e iOS con interfaz y lógica compartidas mediante Kotlin Multiplatform y Compose Multiplatform. El MVP funcionará sin cuenta y guardará de forma privada los datos en el dispositivo. Empezar una sesión y registrar el avance debe ser posible sin conexión. Las ideas, acciones y sugerencias son opcionales: nunca bloquean el cierre de una sesión.

La arquitectura debe permitir añadir usuarios y sincronización entre dispositivos más adelante sin rehacer el modelo de lectura. **Preparar esa posibilidad no significa implementar ahora autenticación, servidor ni un motor de sincronización.**

## Estructura de módulos

```text
androidApp/                 Entrada Android y configuración propia del sistema
iosApp/                     Entrada iOS y configuración propia del sistema
shared/                     Shell Compose: tema, navegación y ensamblado de dependencias
core/
  model/                    Modelos y reglas compartidas, sin UI ni infraestructura
  data/                     Contratos de repositorio e implementaciones locales/remotas
  database/                 Room, entidades de almacenamiento, DAO y migraciones
  designsystem/             Tema y componentes compartidos basados en design tokens
  platform/                 Interfaces y adaptadores Android/iOS: reloj, avisos, permisos
feature/
  library/                  Biblioteca, alta de libros e intención de lectura
  reading/                  Meta, sesión, cierre y progreso
  streaks/                  Rachas, recuperación y logros
  ideas/                    Captura y consulta de ideas
  actions/                  Acciones, seguimiento, evaluación y sugerencias
```

Cada directorio Kotlin de `core` y `feature` será un módulo Gradle KMP con `commonMain`, `androidMain` e `iosMain` cuando necesite código específico. `androidApp` e `iosApp` siguen siendo las entradas nativas. `shared` queda como módulo de composición de la aplicación: configura el grafo de navegación, crea dependencias y expone `App()`; no concentra la lógica de las funciones.

Los módulos de funciones contienen pantallas, estado de UI, ViewModels y los casos de uso que aporten lógica propia. `core:model` conserva los tipos y reglas que cruzan funciones. `core:data` define contratos como `BookRepository`, `ReadingRepository`, `IdeaRepository`, `ActionRepository`, `BookCatalog` y `ActionSuggestionService`; sus implementaciones usan `core:database` y, donde corresponda, red. Esto evita que una pantalla conozca entidades de Room o respuestas de una API.

```text
androidApp / iosApp → shared → feature:* → core:model + core:data + core:designsystem
                         └──────────────→ core:platform (mediante interfaces)
shared → core:data → core:database
```

Las dependencias solo apuntan hacia la derecha o hacia abajo en ese esquema. Una función no importa clases internas de otra función. Cuando dos funciones necesitan la misma información, la obtienen mediante un contrato compartido, no mediante una referencia directa entre pantallas. Evitar un módulo `core:common` genérico: cada pieza compartida debe tener una responsabilidad reconocible.

## Datos y reglas de dominio

El almacenamiento principal es SQLite mediante Room KMP. Las tablas iniciales corresponden a `Book`, `ReadingSession`, `Idea` y `Action`. Las preferencias pequeñas, como tema y horario de recordatorio, van a DataStore Preferences. Los tipos de dominio se mantienen separados de entidades de base de datos y DTO de red.

- `Book` tiene intención (`SOLO_LEER`, `RECORDAR`, `APLICAR`), estado, páginas totales y página actual. El identificador local es propio de Foli; un identificador de catálogo externo es opcional.
- `ReadingSession` registra libro, página inicial, página final, meta, fecha local de lectura y momento de finalización. Cerrar la sesión y actualizar el progreso ocurre en una transacción. La sesión queda completada antes de ofrecer guardar una idea.
- `Idea` pertenece a un libro y puede vincularse a una sesión; guardar una idea siempre es opcional.
- `Action` pertenece a una idea. Se aplica la regla de una acción como máximo por idea, con estados pendiente, en curso, aplicada y descartada, y evaluación final cuando corresponda.
- La racha se deriva de los días con sesiones válidas; no se usa un contador mutable como única fuente de verdad. Las reglas de fecha, zona horaria, recuperación de racha y edición de sesiones deben quedar explícitas y cubiertas por pruebas de dominio.
- El límite de dos libros activos, los cambios de intención y la validación de páginas se aplican en casos de uso, no solo en la UI.

Los repositorios exponen `Flow` para lecturas observables y funciones `suspend` para cambios. Los ViewModels transforman esos flujos en un estado de pantalla y envían operaciones a un caso de uso cuando existe lógica que encapsular, o directamente al contrato del repositorio en operaciones simples. El estado persistente reside en los repositorios; el ViewModel conserva solo estado transitorio de interfaz.

### Criterio para crear casos de uso

Un caso de uso debe tener un propósito identificable: aplicar una regla de negocio, transformar datos, combinar resultados de varios repositorios o coordinar una operación que requiera consistencia. Por ejemplo, `CompleteReadingSession` puede validar páginas, guardar la sesión y actualizar el progreso en una transacción. Su nombre y pruebas deben expresar ese comportamiento.

Si una clase solo recibe un argumento y llama al mismo método de un repositorio sin añadir ninguna regla, transformación u orquestación, no se crea ese caso de uso. El ViewModel puede recibir directamente el contrato del repositorio. Esta regla se revisa cuando el flujo crece; no obliga a introducir una capa vacía para cada operación CRUD.

## Stack del MVP

| Necesidad | Elección | Ubicación principal |
|---|---|---|
| UI compartida | Compose Multiplatform + Material 3 | `shared`, `feature:*`, `core:designsystem` |
| Estado y concurrencia | ViewModel multiplataforma, Coroutines y Flow | `feature:*`, `core:data` |
| Navegación | Navigation Compose multiplataforma, rutas tipadas | `shared` |
| Datos estructurados | Room KMP + SQLite; KSP para el compilador de Room | `core:database` |
| Preferencias | DataStore Preferences KMP | `core:data` y adaptadores de plataforma |
| HTTP y JSON | Ktor Client + Kotlin Serialization | `core:data` |
| Portadas | Coil 3 para Compose Multiplatform | `feature:library` y pantallas que muestran libros |
| Catálogo inicial | Google Books detrás de `BookCatalog`, con alta manual como alternativa | `core:data`, `feature:library` |
| Inyección de dependencias | Koin desde el inicio, con definiciones compartidas y aportes específicos de Android/iOS | `shared` y módulos que aportan dependencias |
| Notificaciones locales | Adaptadores nativos Android/iOS detrás de un contrato compartido | `core:platform` |

En Ktor se usa un motor compatible con cada plataforma, por ejemplo OkHttp en Android y Darwin en iOS. Las versiones de las nuevas bibliotecas se fijarán en `gradle/libs.versions.toml` cuando se implementen y se comprobará que compilen para ambos destinos. El catálogo no es la fuente de verdad del libro guardado: el usuario puede corregir edición y número de páginas.

El archivo [`design/tokens/design-tokens.json`](../design/tokens/design-tokens.json) es la fuente de los colores de día y noche, tipografía, espacios y radios. `core:designsystem` los traduce a tipos y componentes Compose. No se debe leer ese JSON en cada pantalla durante la ejecución.

### Sistema de diseño y tokens

Los tokens de `design/tokens/design-tokens.json` se traducen a mano a valores Kotlin compilados dentro de `core:designsystem` (`FoliColors`, `FoliSpacing`, `FoliRadius`, `FoliSize`, `FoliTypography`): paletas de día y noche como `Color`, espacios y radios como `Dp`, y estilos tipográficos como `TextStyle`. El módulo no depende de un lector de JSON ni de recursos empaquetados; el archivo JSON sigue siendo la fuente de diseño, no un recurso que la app lea en tiempo de ejecución. Una prueba en `core/designsystem/src/commonTest` compara una muestra representativa de esos valores compilados con el JSON.

El composable `FoliTheme` (en `core/designsystem`) construye el `ColorScheme` y la `Typography` de Material 3 a partir de esas paletas según el tema día/noche, y expone los tokens propios de Foli que no tienen un slot directo en `ColorScheme` (por ejemplo `leaf400` o `peach500`) a través de `FoliTheme.colors`. `shared/src/commonMain/kotlin/com/ketadev/foli/App.kt` envuelve la pantalla inicial con `FoliTheme` en lugar de `MaterialTheme` directo.

Las tipografías del JSON, **Young Serif** (familia `display`) y **Nunito** (familia `sans`), no están empaquetadas todavía: no hay archivos de fuente ni licencia confirmada en este repositorio. Mientras tanto, `FoliTypography` usa `FontFamily.Serif` y `FontFamily.SansSerif` como alternativas explícitas, documentadas en el KDoc del archivo. Agregar las fuentes reales más adelante solo requiere cambiar esas dos referencias.

### Decisión sobre inyección de dependencias

**Koin es la librería de inyección de dependencias del MVP**, desde las primeras funciones. Cada clase declara sus dependencias por constructor. Los módulos `core` y `feature` aportan definiciones propias; `shared` reúne esas definiciones y arranca Koin una sola vez desde la entrada de cada plataforma. Los objetos que requieren `Context` u otras API nativas se definen en Android o iOS y se exponen mediante contratos compartidos. Las pantallas obtienen sus ViewModels del grafo de Koin.

Mantener los módulos de Koin pequeños y alineados con los módulos Gradle, sin usar el contenedor como localizador global de servicios dentro de la lógica de dominio. Registrar y verificar el grafo para Android y el simulador iOS al implementar la primera función. La [guía oficial de Koin para KMP](https://insert-koin.io/docs/reference/koin-core/kmp-setup/) documenta soporte para ambas plataformas; la versión concreta se fijará en `gradle/libs.versions.toml` al agregar las dependencias.

### Arranque de Koin propiedad de cada plataforma nativa

FOLI-00 implementa el arranque de Koin desde la entrada nativa de cada plataforma, no desde `shared`: la `Application` de Android (`FoliApplication.onCreate`) y el inicializador `@main App.init()` de SwiftUI son los únicos dueños del arranque, porque una `Activity` o una vista SwiftUI pueden recrearse varias veces por proceso mientras que la entrada de aplicación se ejecuta una sola vez. `shared/src/commonMain/kotlin/com/ketadev/foli/di/AppKoin.kt` separa la construcción de la lista de módulos (`foliModules`) del arranque (`startFoliKoin`): esa separación permite que producción y pruebas compartan exactamente la misma composición de módulos sin tocar el contexto global de Koin en las pruebas.

`startFoliKoin` es una guarda idempotente: una segunda llamada del propio proceso de Foli devuelve la instancia ya iniciada sin reiniciar nada, y si Koin ya fue iniciado por un dueño ajeno (otra librería, un host de pruebas) lanza `IllegalStateException` en lugar de adoptar ese grafo en silencio. La comprobación usa `org.koin.mp.KoinPlatformTools.defaultContext()` (la API común multiplataforma; `GlobalContext` de `koin-core-jvm` no existe en `commonMain`). El único supuesto documentado es que el arranque ocurre en el hilo principal desde el dueño nativo antes de crear cualquier UI, por lo que la referencia al `KoinApplication` propio no usa sincronización adicional; si en el futuro aparece un segundo llamador legítimo fuera del hilo principal, esa guarda debe revisarse.

Las pruebas de `shared` (`SharedLogicAndroidHostTest`, `SharedLogicIOSTest`) usan `koinApplication { modules(foliModules(platformModule)) }` para construir un grafo aislado, resolver `PlatformInfo` y cerrarlo, sin registrar nada en el contexto global; y usan `startFoliKoin` directamente (visibilidad `internal`, compartida entre los source sets de un mismo módulo Gradle) para probar el no-op de una segunda llamada y el error ante un Koin ajeno ya iniciado, restableciendo el estado global en cada `tearDown`.

`core:platform` aporta el primer binding real de este grafo: la interfaz `PlatformInfo` (sin efectos secundarios) con `AndroidPlatformInfo` (`Build.VERSION.SDK_INT`) e `IosPlatformInfo` (`UIDevice.currentDevice`), usada únicamente para probar que el ensamblado por plataforma funciona; no representa todavía un contrato de dominio.

## Sugerencias de IA

La interfaz `ActionSuggestionService` recibe solo el texto de la idea que el usuario decidió compartir y devuelve una propuesta editable. La acción se guarda únicamente tras aceptación del usuario. El resto del flujo funciona sin IA y sin conexión.

Si se incluye un proveedor remoto de IA en la primera publicación, hará falta un servicio intermediario mínimo para proteger la clave, aplicar límites de uso y enviar únicamente la nota necesaria. La aplicación móvil no almacenará una clave privada del proveedor. La elección del proveedor y el despliegue de ese servicio quedan pendientes de la decisión de alcance de la primera versión pública.

## Camino hacia usuarios y sincronización

El diseño local deja puntos de extensión concretos:

1. Usar IDs propios estables para libros, sesiones, ideas y acciones; no depender de IDs de Google Books ni de IDs autoincrementales como identidad de sincronización.
2. Registrar `createdAt` y `updatedAt` de forma consistente y prever borrado lógico para entidades que puedan sincronizarse. Los campos se introducen cuando se definan sus reglas de actualización, evitando metadatos sin uso.
3. Mantener toda escritura detrás de repositorios y transacciones. Una futura implementación podrá añadir una cola local de cambios y un `SyncCoordinator` sin modificar las pantallas.
4. Cuando existan cuentas, definir la asociación entre datos locales y cuenta, migración al iniciar sesión, conflictos entre dispositivos y comportamiento al cerrar sesión **antes** de activar sincronización. Los datos locales actuales no deben perderse por crear una cuenta.
5. Mantener la base local como fuente para la UI incluso con sincronización futura: se escribe localmente y se sincroniza cuando haya red. La política de conflictos se decidirá por tipo de entidad; una regla global de «última escritura gana» podría sobrescribir sesiones o ideas.

En el MVP no se añaden tablas de cola, API de usuarios ni estados de sincronización sin un flujo real que los utilice.

## Secuencia de implementación

1. Extraer `core:model`, `core:designsystem` y el shell de `shared`; configurar Koin y comprobar el grafo y la compilación en Android/iOS.
2. Añadir `core:database` y `core:data`; implementar biblioteca local y alta manual. Integrar búsqueda de catálogo después de tener este flujo funcional.
3. Implementar `feature:reading` y las transacciones de cierre; probar límites de páginas y continuidad sin conexión.
4. Añadir rachas/logros, ideas y acciones, manteniendo opcionales los pasos posteriores a la lectura.
5. Añadir recordatorios locales y, si entra en el alcance de publicación, el servicio de sugerencias de IA.

El criterio de arquitectura para cada paso es que la misma función compile y se pueda recorrer en Android e iOS. Las pruebas prioritarias son las de reglas de dominio y persistencia: cierre de sesión, progreso, límite de libros, rachas y relación idea–acción.

## Estado actual del repositorio

`settings.gradle.kts` incluye `:androidApp`, `:shared` y los cinco módulos `:core:model`, `:core:data`, `:core:database`, `:core:designsystem` y `:core:platform`; ningún módulo `:feature:*` existe todavía. La dirección de dependencias implementada hasta FOLI-00 es `androidApp` / `iosApp` → `shared` → `core:model` + `core:data` + `core:database` + `core:designsystem` + `core:platform`, y `core:data` → `core:model` + `core:database`; `core:designsystem` y `core:platform` no dependen de ningún otro módulo `core`.

`core:database` y `core:data` solo traen configurado Room/KSP, DataStore, Ktor y serialización (sin entidad, DAO, migración, repositorio ni llamada de red reales) para probar que compilan en Android e iOS; `core:designsystem` expone los tokens compilados y `FoliTheme`; `core:platform` expone `PlatformInfo` y sus adaptadores Android/iOS. `shared` sigue siendo la pantalla de plantilla del starter (`App.kt`, `Greeting`, `Platform` expect/actual) envuelta en `FoliTheme`, más el ensamblado de Koin (`di/AppKoin*.kt`) y el arranque nativo desde `FoliApplication` (Android) y `@main App.init()` (iOS). No hay todavía ninguna integración externa (Google Books, IA, sincronización) ni credenciales de servicio en el repositorio.
