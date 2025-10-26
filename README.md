# Level-Up

Aplicación móvil de e-commerce gaming desarrollada en Android con Jetpack Compose.

## Equipo
- **Rodrigo Candia** - [@Rodri1988](https://github.com/Rodri1988)
- **Angélica Trujillo** - [@xhojitax](https://github.com/xhojitax)
- **Romina Baeza** - [@RihannonHawke](https://github.com/RihannonHawke)

## Funcionalidades

### Autenticación
- ✅ Registro de usuarios con validaciones
- ✅ Inicio de sesión
- ✅ Foto de perfil (captura con cámara o selección desde galería)
- ✅ Validación de email y contraseña
- ✅ Manejo de errores con mensajes visuales

### Interfaz y Navegación
- ✅ Diseño con Material Design 3
- ✅ Navegación fluida entre pantallas
- ✅ Soporte para rotación de pantalla con scroll
- ✅ Animaciones de transición

### Almacenamiento
- ✅ Base de datos local con Room
- ✅ Persistencia de usuarios
- ✅ Almacenamiento de fotos de perfil

## Recursos Nativos Utilizados

1. **Cámara**: Captura de foto de perfil con solicitud de permisos en tiempo de ejecución
2. **Galería**: Selección de foto desde almacenamiento del dispositivo

## Tecnologías

- **Lenguaje**: Kotlin
- **UI**: Jetpack Compose + Material Design 3
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Base de Datos**: Room (SQLite)
- **Navegación**: Navigation Compose
- **Asincronía**: Coroutines & Flow
- **Gestión de estado**: StateFlow
- **Procesamiento de anotaciones**: KAPT

## Estructura del Proyecto
```
app/src/main/java/com/example/levelup/
├── model/                          # Entidades y estados UI
│   ├── LoginUIState.kt
│   ├── RegisterUIState.kt
│   ├── UserDao_Impl               # DAO generado por Room
│   └── UserEntity.kt
│
├── repository/                     # Repositorios y base de datos
│   ├── ApplicationDatabase.kt
│   ├── ApplicationDatabase_Impl   # Base de datos generada por Room
│   └── UserRepository.kt
│
├── view_model/                     # ViewModels (lógica de negocio)
│   ├── LoginViewModel.kt
│   ├── RegisterViewModel.kt
│   └── ViewModelFactory.kt
│
├── ui/                            # Interfaz de usuario
│   ├── screens/                   # Pantallas Compose
│   │   ├── HomeScreen.kt
│   │   ├── IndexScreen.kt        
│   │   ├── LoginScreen.kt
│   │   ├── ProfileImagePicker.kt 
│   │   └── RegisterScreen.kt
│   │
│   └── theme/                     # Temas y estilos
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
├── navigation/                    # Sistema de navegación
│   ├── NavRouter.kt
│   └── ScreenRoute.kt
│
├── utils/                         # Utilidades y validaciones
│   └── Strings.kt                
│
├── ExampleInstrumentedTest       # Tests
├── ExampleUnitTest               # Tests
├── MainActivity.kt               # Activity principal
└── SplashActivity.kt             # Animacion
```

## Instalación y Ejecución

### Requisitos
- Android Studio Hedgehog (2023.1.1) o superior
- JDK 17
- SDK mínimo: API 24 (Android 7.0)
- SDK objetivo: API 34 (Android 14)

### Pasos para ejecutar

1. **Clonar el repositorio:**
```bash
git clone https://github.com/Rodri1988/LevelUp_Android.git
cd levelup
```

2. **Abrir en Android Studio:**
    - File → Open
    - Seleccionar la carpeta del proyecto
    - Esperar a que Gradle sincronice automáticamente

3. **Configurar emulador o dispositivo:**
    - Emulador: Tools → Device Manager → Create Device
    - Dispositivo físico: Habilitar "Opciones de desarrollador" y "Depuración USB"

4. **Ejecutar la aplicación:**
    - Click en el botón Run (o Shift + F10)
    - Seleccionar dispositivo
    - Esperar instalación

### Permisos Requeridos
La aplicación solicitará los siguientes permisos en tiempo de ejecución:
- `CAMERA`: Para capturar foto de perfil con la cámara del dispositivo
- `READ_EXTERNAL_STORAGE`: Para seleccionar foto de galería (Android < 13)
- `READ_MEDIA_IMAGES`: Para acceder a galería (Android 13+)

## Gestión del Proyecto

- **Repositorio**: [GitHub](https://github.com/Rodri1988/LevelUp_Android.git)
- **Tablero Kanban**: [GitHub Projects](https://github.com/users/Rodri1988/projects/3)
- **Sistema de Issues**: Para seguimiento de tareas, bugs y mejoras

### Metodología
- Desarrollo ágil con sprints de 2 semanas
- Distribución equitativa de tareas entre los 3 miembros
- Commits descriptivos siguiendo convenciones técnicas

## Características de Diseño

- Interfaz en español
- Logo personalizado de Level-Up
- Colores corporativos consistentes
- Botones con estados visuales claros
- Validaciones en tiempo real con feedback inmediato
- Soporte completo para modo vertical y horizontal

## Validaciones Implementadas

### Registro
- Email con formato válido
- Contraseña mínimo 6 caracteres
- Confirmación de contraseña coincidente
- Email único (no duplicados)

### Login
- Email con formato válido
- Verificación de credenciales
- Mensajes de error claros


**Desarrollado ️ por el equipo Level-Up**

*Última actualización: Octubre 2025*