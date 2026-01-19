# Space Flight News 📰🚀

Una aplicación Android moderna desarrollada en Kotlin que permite a los usuarios explorar noticias y artículos sobre vuelos espaciales, lanzamientos de cohetes y eventos espaciales en tiempo real.

## 📋 Propósito

Space Flight News es una aplicación móvil diseñada para mantener a los entusiastas del espacio informados sobre las últimas noticias, lanzamientos y eventos relacionados con la exploración espacial. La aplicación consume datos de dos APIs principales:

- **Space Flight News API v4**: Proporciona artículos, blogs y reportes sobre noticias espaciales
- **Launch Library 2 API**: Proporciona información detallada sobre lanzamientos de cohetes y eventos espaciales

### Funcionalidades principales:

- 📰 **Explorar artículos espaciales**: Navega por una lista paginada de artículos de diferentes sitios de noticias espaciales
- 🔍 **Búsqueda avanzada**: Busca artículos por palabras clave o filtra por sitio de noticias
- 📖 **Vista detallada**: Explora detalles completos de cada artículo incluyendo autores, redes sociales, y enlaces relacionados
- 🚀 **Información de lanzamientos**: Visualiza información detallada de lanzamientos de cohetes con estado, proveedor, cohete, misión y ubicación
- 🌐 **Enlaces externos**: Abre artículos completos en el navegador y accede a las redes sociales de los autores
- 🎨 **Interfaz moderna**: Diseño moderno con Material Design 3 y soporte para modo oscuro

## 🏗️ Arquitectura

La aplicación sigue los principios de **Clean Architecture** con una arquitectura **MVVM (Model-View-ViewModel)**:

```
┌─────────────────────────────────────────────────┐
│           Presentation Layer                    │
│  (UI - Jetpack Compose, ViewModels)            │
├─────────────────────────────────────────────────┤
│            Domain Layer                         │
│  (Business Logic, Models, Repository Interface) │
├─────────────────────────────────────────────────┤
│             Data Layer                          │
│  (Repository Implementation, API Services)     │
└─────────────────────────────────────────────────┘
```

### Estructura del proyecto:

- **`presentation/`**: Contiene la capa de presentación con:
  - **UI**: Pantallas desarrolladas con Jetpack Compose (`HomeScreen`, `ArticleDetailScreen`, `SplashScreen`)
  - **ViewModels**: Lógica de presentación y gestión de estado (`HomeScreenViewModel`, `ArticleDetailViewModel`)
  - **Navigation**: Configuración de navegación con Jetpack Navigation Compose
  - **Theme**: Tema personalizado con Material Design 3

- **`domain/`**: Contiene la lógica de negocio con:
  - **Models**: Modelos de dominio puros (sin dependencias de frameworks)
  - **Repository Interface**: Contrato para acceso a datos

- **`data/`**: Contiene la capa de datos con:
  - **Remote**: Servicios de API (Retrofit) y DTOs
  - **Repository**: Implementación del repositorio
  - **Mapper**: Conversión entre DTOs y modelos de dominio
  - **DI**: Módulos de inyección de dependencias (Hilt)

### Principios aplicados:

- ✅ **Separación de responsabilidades**: Cada capa tiene una responsabilidad clara
- ✅ **Inversión de dependencias**: El dominio no depende de la capa de datos
- ✅ **Testabilidad**: Arquitectura que facilita las pruebas unitarias
- ✅ **Escalabilidad**: Fácil de extender y mantener

## 📚 Librerías Utilizadas

### Core Android
- **AndroidX Core KTX** (1.17.0): Extensions Kotlin para Android
- **AndroidX Lifecycle Runtime KTX** (2.10.0): Componentes del ciclo de vida
- **AndroidX Activity Compose** (1.12.2): Integración de Compose con Activity

### UI - Jetpack Compose
- **Jetpack Compose BOM** (2026.01.00): Gestión de versiones de Compose
- **Compose UI**: Biblioteca base de Compose
- **Compose Material 3**: Material Design 3 para Compose
- **Material Icons Extended**: Iconos adicionales de Material Design
- **Compose Navigation**: Navegación declarativa con Compose

### Inyección de Dependencias
- **Dagger Hilt** (2.58): Framework de inyección de dependencias
- **Hilt Navigation Compose** (1.3.0): Integración de Hilt con Navigation Compose
- **KSP** (2.3.4): Procesador de anotaciones Kotlin (reemplazo de KAPT)

### Networking
- **Retrofit** (2.11.0): Cliente HTTP para Android
- **OkHttp** (4.12.0): Cliente HTTP con logging
- **Kotlinx Serialization JSON** (1.8.0): Serialización JSON nativa de Kotlin
- **Retrofit Kotlinx Serialization Converter**: Conversor para Retrofit

### Carga de Imágenes
- **Coil** (2.7.0): Biblioteca moderna para carga de imágenes
- **Coil Compose**: Integración de Coil con Jetpack Compose
- **Coil SVG**: Soporte para imágenes SVG

### Utilidades
- **Timber** (5.0.1): Logging simplificado
- **Core Splash Screen** (1.0.1): Pantalla de inicio nativa

### Testing
- **JUnit** (4.13.2): Framework de pruebas unitarias
- **MockWebServer** (4.12.0): Servidor HTTP mock para pruebas
- **Kotlinx Coroutines Test** (1.9.0): Utilidades para probar coroutines
- **Turbine** (1.1.0): Biblioteca para probar Flows de Kotlin
- **MockK** (1.13.10): Framework de mocking para Kotlin
- **Hilt Android Testing** (2.58): Utilidades de testing para Hilt
- **Compose UI Test JUnit4**: Testing de UI con Compose
- **Espresso Core** (3.7.0): Framework de testing de UI

### Debugging
- **LeakCanary** (2.13): Detección de memory leaks en modo debug

## 🧪 Tests Disponibles

La aplicación incluye una suite completa de tests unitarios que garantizan la calidad y confiabilidad del código:

### Tests de API Service

#### `SFNApiServiceTest.kt`
Tests para el servicio de la API Space Flight News:
- ✅ `getArticles_Success_DevuelveListaDeArticulos`: Verifica la obtención exitosa de artículos
- ✅ `getArticles_Success_ConParametrosDeBusqueda`: Verifica búsqueda con parámetros
- ✅ `getArticleById_Success_DevuelveArticulo`: Verifica obtención de artículo por ID
- ✅ `getArticleById_Error404_LanzaExcepcion`: Manejo de error 404
- ✅ `getArticleById_Error500_LanzaExcepcion`: Manejo de error 500
- ✅ Tests similares para `getBlogs`, `getBlogById`, `getReports`, `getReportById`

#### `LaunchLibraryApiServiceTest.kt`
Tests para el servicio de Launch Library 2 API:
- ✅ `getLaunchById_Success_DevuelveDetallesDelLanzamiento`: Verifica obtención exitosa de detalles de lanzamiento
- ✅ `getLaunchById_Success_ConCamposOpcionalesNulos`: Manejo de campos opcionales nulos
- ✅ `getLaunchById_Error404_LanzaExcepcion`: Manejo de error 404
- ✅ `getLaunchById_Error500_LanzaExcepcion`: Manejo de error 500
- ✅ `getEventById_Success_DevuelveDetallesDelEvento`: Verifica obtención exitosa de detalles de evento
- ✅ `getEventById_Success_ConCamposOpcionalesNulos`: Manejo de campos opcionales nulos
- ✅ `getEventById_Error404_LanzaExcepcion`: Manejo de error 404
- ✅ `getEventById_Error500_LanzaExcepcion`: Manejo de error 500
- ✅ Tests de verificación de parámetros en las peticiones

### Tests de Repository

#### `SFNRepositoryTest.kt`
Tests para la implementación del repositorio que cubren todos los métodos:

**Para Space Flight News API:**
- ✅ `getArticles_Success_DevuelveResultConArticulos`: Caso exitoso
- ✅ `getArticles_NetworkError_DevuelveResultFailure`: Manejo de errores de red
- ✅ `getArticles_TimeoutError_DevuelveResultFailure`: Manejo de timeouts
- ✅ `getArticles_ConParametrosDeBusqueda_LlamaAlServicioCorrectamente`: Verificación de parámetros
- ✅ `getArticleById_Success_DevuelveResultConArticulo`: Caso exitoso
- ✅ `getArticleById_NetworkError_DevuelveResultFailure`: Manejo de errores de red
- ✅ Tests similares para blogs y reportes

**Para Launch Library 2 API:**
- ✅ `getLaunchById_Success_DevuelveResultConLaunchDetail`: Caso exitoso con todos los campos
- ✅ `getLaunchById_NetworkError_DevuelveResultFailure`: Manejo de errores de red
- ✅ `getLaunchById_TimeoutError_DevuelveResultFailure`: Manejo de timeouts
- ✅ `getLaunchById_ConCamposOpcionalesNulos_DevuelveResultSuccess`: Campos opcionales
- ✅ `getEventById_Success_DevuelveResultConEventDetail`: Caso exitoso con todos los campos
- ✅ `getEventById_NetworkError_DevuelveResultFailure`: Manejo de errores de red
- ✅ `getEventById_TimeoutError_DevuelveResultFailure`: Manejo de timeouts
- ✅ `getEventById_ConCamposOpcionalesNulos_DevuelveResultSuccess`: Campos opcionales

### Cobertura de Tests

Los tests cubren:
- ✅ **Casos exitosos**: Verificación de respuestas correctas y mapeo de datos
- ✅ **Manejo de errores**: Errores de red (IOException), timeouts (SocketTimeoutException), y errores HTTP (404, 500)
- ✅ **Campos opcionales**: Manejo correcto de valores nulos
- ✅ **Parámetros de petición**: Verificación de que los servicios se llaman con los parámetros correctos
- ✅ **Mapeo de datos**: Verificación de conversión correcta de DTOs a modelos de dominio

### Ejecutar Tests

Para ejecutar todos los tests unitarios:
```bash
./gradlew :app:testDebugUnitTest
```

Para ejecutar un test específico:
```bash
./gradlew :app:testDebugUnitTest --tests "com.diegoginko.spaceflightnews.data.remote.SFNApiServiceTest"
```

Para ejecutar tests con cobertura:
```bash
./gradlew :app:testDebugUnitTest :app:jacocoTestReport
```

## 🛠️ Configuración del Proyecto

### Requisitos
- **Android Studio**: Hedgehog (2023.1.1) o superior
- **JDK**: 17 o superior
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36 (Android 14)
- **Gradle**: 8.13.2
- **Kotlin**: 2.3.0

### Configuración inicial

1. Clona el repositorio:
```bash
git clone <repository-url>
cd Space-Flight-News
```

2. Abre el proyecto en Android Studio

3. Sincroniza el proyecto con Gradle (Android Studio lo hará automáticamente)

4. Ejecuta la aplicación:
   - Conecta un dispositivo Android o inicia un emulador
   - Haz clic en "Run" o presiona `Shift + F10`

### Estructura de directorios

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/diegoginko/spaceflightnews/
│   │   │   ├── data/              # Capa de datos
│   │   │   │   ├── di/           # Módulos de inyección de dependencias
│   │   │   │   ├── mapper/       # Mappers DTO -> Domain
│   │   │   │   ├── remote/       # Servicios API y DTOs
│   │   │   │   ├── repository/   # Implementación del repositorio
│   │   │   │   └── util/         # Utilidades (NetworkErrorHandler)
│   │   │   ├── domain/           # Capa de dominio
│   │   │   │   ├── model/        # Modelos de dominio
│   │   │   │   └── repository/   # Interfaces del repositorio
│   │   │   └── presentation/     # Capa de presentación
│   │   │       ├── navigation/   # Configuración de navegación
│   │   │       ├── ui/           # Pantallas y ViewModels
│   │   │       │   ├── detail/   # Pantalla de detalle
│   │   │       │   ├── home/     # Pantalla principal
│   │   │       │   ├── splash/   # Pantalla de inicio
│   │   │       │   └── theme/    # Tema de la aplicación
│   │   │       └── util/         # Utilidades (DateFormatter)
│   │   └── res/                  # Recursos (drawables, layouts, etc.)
│   └── test/                     # Tests unitarios
│       └── java/com/diegoginko/spaceflightnews/
│           └── data/
│               ├── remote/       # Tests de servicios API
│               └── repository/   # Tests del repositorio
```

## 🎨 Características de UI

- **Material Design 3**: Diseño moderno siguiendo las últimas guías de Material Design
- **Tema personalizado**: Colores inspirados en Space Flight News API
- **Modo oscuro**: Soporte completo para tema claro y oscuro
- **Edge-to-edge**: Interfaz que se extiende hasta los bordes de la pantalla
- **Navegación fluida**: Transiciones suaves entre pantallas
- **Estados de carga**: Indicadores visuales durante la carga de datos
- **Manejo de errores**: Mensajes de error amigables y opciones de reintento

## 📱 Pantallas

1. **Splash Screen**: Pantalla de bienvenida con el logo de la aplicación
2. **Home Screen**: Lista de artículos con barra de búsqueda y filtros
3. **Article Detail Screen**: Vista detallada del artículo con información de autores, lanzamientos y enlaces

## 🔗 APIs Utilizadas

- **Space Flight News API v4**: https://api.spaceflightnewsapi.net/v4/
- **Launch Library 2 API**: https://ll.thespacedevs.com/2.2.0/

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## 👤 Autor

Desarrollado por Diego Ginko

## 🙏 Agradecimientos

- Space Flight News API por proporcionar acceso a noticias espaciales
- Launch Library 2 API por proporcionar información detallada de lanzamientos
- Comunidad de Android por las excelentes herramientas y bibliotecas
