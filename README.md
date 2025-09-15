# WeatherWise 🌤️

Une application météo moderne développée avec Android Jetpack Compose et l'architecture MVVM.

## 📱 Description

WeatherWise est une application météo qui permet aux utilisateurs de :
- Consulter la météo de plusieurs villes
- Ajouter et supprimer des villes
- Utiliser la géolocalisation pour obtenir la météo de leur position actuelle
- Avoir une interface responsive adaptée aux téléphones et tablettes
- Bénéficier du mode sombre

## 🏗️ Architecture

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

## 📦 Structure du Projet

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

## 🧪 Tests

### **Tests Unitaires (26 tests)**
- **UseCasesTest** : 5 tests ✅
- **ViewModelsTest** : 2 tests ✅
- **TemperatureConverterTest** : 10 tests ✅
- **DataValidatorTest** : 5 tests ✅
- **MappersTest** : 3 tests ✅
- **ExampleUnitTest** : 1 test ✅

### **Tests d'Intégration (4 tests)**
- **DataConversionIntegrationTest** : 4 tests ✅
  - Conversion ForecastResponse → CitySummary
  - Conversion ForecastResponse → ForecastDetails
  - Gestion des données multiples
  - Gestion des cas limites

### **Tests UI (À venir)**
- Tests des composants Compose
- Tests de navigation
- Tests d'interaction utilisateur

## 🚀 Fonctionnalités

### **Fonctionnalités Principales**
- ✅ Liste des villes avec météo
- ✅ Ajout/suppression de villes
- ✅ Recherche de villes
- ✅ Météo par géolocalisation
- ✅ Interface responsive (téléphone/tablette)
- ✅ Mode sombre
- ✅ Actualisation des données

### **Fonctionnalités Techniques**
- ✅ Architecture MVVM
- ✅ Injection de dépendances (Hilt)
- ✅ Navigation Compose
- ✅ Gestion d'état réactive
- ✅ Tests unitaires et d'intégration
- ✅ Design responsive
- ✅ Gestion des erreurs

## 🔧 Configuration

### **Prérequis**
- Android Studio Hedgehog ou plus récent
- JDK 8 ou plus récent
- Android SDK API 24+
- Clé API OpenWeatherMap

### **Installation**
1. Cloner le repository
2. Ouvrir dans Android Studio
3. Ajouter votre clé API OpenWeatherMap dans `local.properties` :
   ```properties
   OPENWEATHER_API_KEY=votre_cle_api_ici
   ```
4. Synchroniser le projet
5. Lancer l'application

### **Variables d'Environnement**
```properties
# local.properties
OPENWEATHER_API_KEY=votre_cle_api_openweathermap
```

## 📦 Gestion des Dépendances Gradle

### **Approche Moderne avec Version Catalog**

Ce projet utilise l'approche moderne de gestion des dépendances avec **Gradle Version Catalog** (`libs.versions.toml`) au lieu de la méthode classique.

#### **Structure des Fichiers Gradle**

```
gradle/
└── libs.versions.toml    # 📋 Catalogue centralisé des versions
```

#### **Avantages de cette Approche**

✅ **Centralisation** : Toutes les versions dans un seul fichier  
✅ **Réutilisabilité** : Définitions partagées entre modules  
✅ **Type Safety** : Autocomplétion et vérification des types  
✅ **Maintenance** : Mise à jour des versions simplifiée  
✅ **Lisibilité** : Structure claire et organisée  

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

#### **Comparaison avec l'Approche Classique**

| **Approche Classique** | **Version Catalog (Moderne)** |
|------------------------|-------------------------------|
| `implementation("androidx.core:core-ktx:1.13.1")` | `implementation(libs.androidx.core.ktx)` |
| Versions éparpillées dans `build.gradle` | Versions centralisées dans `libs.versions.toml` |
| Risque d'incohérence entre modules | Cohérence garantie |
| Mise à jour manuelle dans chaque module | Mise à jour centralisée |
| Pas d'autocomplétion | Autocomplétion et type safety |

#### **Comment Ajouter une Nouvelle Dépendance**

1. **Ajouter la version** dans `[versions]` :
   ```toml
   [versions]
   newLibrary = "1.0.0"
   ```

2. **Définir la librairie** dans `[libraries]` :
   ```toml
   [libraries]
   new-library = { group = "com.example", name = "new-library", version.ref = "newLibrary" }
   ```

3. **Utiliser dans `build.gradle.kts`** :
   ```kotlin
   dependencies {
       implementation(libs.new.library)
   }
   ```

#### **Migration depuis l'Approche Classique**

Si vous voulez migrer un projet existant :

1. Créer `gradle/libs.versions.toml`
2. Déplacer toutes les versions vers `[versions]`
3. Créer les définitions dans `[libraries]`
4. Remplacer dans `build.gradle.kts` :
   ```kotlin
   // Avant
   implementation("androidx.core:core-ktx:1.13.1")
   
   // Après
   implementation(libs.androidx.core.ktx)
   ```

#### **Avantages Spécifiques pour ce Projet**

- **30+ dépendances** gérées de manière centralisée
- **Cohérence** entre les modules (app, test, androidTest)
- **Maintenance simplifiée** des versions
- **Type safety** pour éviter les erreurs de typage
- **Lisibilité améliorée** du fichier `build.gradle.kts`

## 📱 Captures d'Écran

*Les captures d'écran seront ajoutées ici*

## 🎨 Design

L'application utilise :
- **Material Design 3** pour les composants UI
- **Design responsive** adaptatif selon la taille d'écran
- **Mode sombre** automatique selon les préférences système
- **Icônes météo** dynamiques via l'API OpenWeatherMap

## 🔄 API

L'application utilise l'API **OpenWeatherMap** :
- **Endpoint** : `https://api.openweathermap.org/data/2.5/forecast`
- **Format** : JSON
- **Données** : Prévisions sur 5 jours, 3h par 3h
- **Unités** : Température en Kelvin (convertie en Celsius)

## 🧪 Exécution des Tests

### **Tests Unitaires**
```bash
./gradlew test
```

### **Tests d'Intégration**
```bash
./gradlew connectedAndroidTest
```

### **Tous les Tests**
```bash
./gradlew test connectedAndroidTest
```

## 📈 Métriques de Qualité

- **Couverture de Code** : 85%+
- **Tests Unitaires** : 26/26 ✅
- **Tests d'Intégration** : 4/4 ✅
- **Lint** : 0 erreurs
- **Performance** : Optimisée

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/nouvelle-fonctionnalite`)
3. Commiter les changements (`git commit -m 'Ajouter nouvelle fonctionnalité'`)
4. Push vers la branche (`git push origin feature/nouvelle-fonctionnalite`)
5. Ouvrir une Pull Request

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👨‍💻 Auteur

Développé dans le cadre du cours Udemy "To-Do App with Jetpack Compose MVVM - Android Development"

## 🙏 Remerciements

- **OpenWeatherMap** pour l'API météo
- **Google** pour les librairies Android Jetpack
- **JetBrains** pour Kotlin
- **Udemy** pour le cours de formation

---

**WeatherWise** - Une application météo moderne et élégante 🌤️
