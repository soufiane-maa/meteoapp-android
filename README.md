# WeatherWise 🌤️

Une application météo  développée avec Android Jetpack Compose et l'architecture MVVM.



## Architecture

L'application suit l'architecture **MVVM (Model-View-ViewModel)** avec :
- **Clean Architecture** : Séparation claire des couches (Data, Domain, Presentation)
- **Dependency Injection** : Hilt pour la gestion des dépendances
- **Reactive Programming** : StateFlow et Compose pour la réactivité
- **Repository Pattern** : Abstraction de la couche de données

## 🛠️ Technologies et Librairies

### **Core Android**
- **Android SDK** : API 24+ (Android 7.0+)
- **Kotlin** : 1.9.0
- **Gradle** : 8.6.0
- **Build Tools** : 35.0.0

### **UI & Compose**
- **Jetpack Compose** : 2024.04.01
- **Material Design 3** : 1.1.2
- **Compose BOM** : 2024.04.01
- **Navigation Compose** : 2.8.2
- **Window Size Class** : 1.3.2 (Design responsive)

### **Architecture & DI**
- **Hilt** : 2.52 (Dependency Injection)
- **ViewModel** : 2.8.4
- **Lifecycle** : 2.8.4
- **Navigation** : 2.8.2

### **Networking**
- **Retrofit** : 2.11.0
- **Moshi** : 1.15.1 (JSON parsing)
- **OkHttp** : 4.12.0
- **OkHttp Logging** : 4.12.0

### **Async & Coroutines**
- **Kotlin Coroutines** : 1.8.1
- **Coroutines Android** : 1.8.1

### **Images & Media**
- **Coil Compose** : 2.6.0 (Image loading)

### **Location & Permissions**
- **Play Services Location** : 21.3.0
- **Accompanist Permissions** : 0.34.0

### **Testing**

#### **Tests Unitaires (Unit Tests)**
- **JUnit** : 4.13.2
- **MockK** : 1.13.11 (Mocking)
- **Kotlin Coroutines Test** : 1.8.1

#### **Tests d'Intégration (Integration Tests)**
- **AndroidX Test** : 1.3.0
- **Espresso Core** : 3.7.0
- **Compose UI Test** : 2024.04.01
- **Hilt Testing** : 2.52

#### **Tests UI (UI Tests)**
- **Compose Test Rule** : 2024.04.01
- **Compose UI Test Manifest** : 2024.04.01

##  Structure du Projet

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/babel/meteoapp/
│   │   │   ├── data/           # Couche de données
│   │   │   │   ├── network/    # Modèles réseau
│   │   │   │   └── repository/ # Implémentation repository
│   │   │   ├── domain/         # Couche métier
│   │   │   │   ├── model/      # Modèles de domaine
│   │   │   │   ├── repository/ # Interfaces repository
│   │   │   │   └── usecase/    # Cas d'usage
│   │   │   ├── presentation/   # Couche présentation
│   │   │   │   ├── ui/         # Composants UI
│   │   │   │   └── viewmodel/  # ViewModels
│   │   │   ├── di/             # Injection de dépendances
│   │   │   ├── navigation/     # Navigation
│   │   │   ├── utils/          # Utilitaires
│   │   │   └── config/         # Configuration
│   │   └── res/                # Ressources
│   ├── test/                   # Tests unitaires
│   └── androidTest/            # Tests d'intégration et UI
```



##  Gestion des Dépendances Gradle

### **Approche Moderne avec Version Catalog**

Ce projet utilise l'approche moderne de gestion des dépendances avec **Gradle Version Catalog** (`libs.versions.toml`) au lieu de la méthode classique.

#### **Structure des Fichiers Gradle**

```
gradle/
└── libs.versions.toml    # 📋 Catalogue centralisé des versions
```


#### **Fichier `gradle/libs.versions.toml`**

```toml
[versions]
kotlin = "1.9.0"
hilt = "2.52"
composeBom = "2024.04.01"
retrofit = "2.11.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
dagger-hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
```

#### **Utilisation dans `build.gradle.kts`**

```kotlin
dependencies {
    // Au lieu de : implementation("androidx.core:core-ktx:1.13.1")
    implementation(libs.androidx.core.ktx)
    
    // Au lieu de : implementation("com.google.dagger:hilt-android:2.52")
    implementation(libs.dagger.hilt.android)
    
    // Au lieu de : implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation(libs.retrofit)
}
```



