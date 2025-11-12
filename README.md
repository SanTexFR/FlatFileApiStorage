# FlatFileStorageAPI

![Java](https://img.shields.io/badge/Java-14--21+-orange?logo=openjdk)
![Minecraft](https://img.shields.io/badge/Minecraft-1.14--1.21+-brightgreen?logo=minecraft)
![License](https://img.shields.io/badge/license-Open%20Usage%20--%20No%20Redistribution%20Claim-blue)
![Status](https://img.shields.io/badge/status-Beta-yellow)

**FlatFileStorageAPI** est une API légère et simple d’utilisation pour les serveurs **Bukkit / Spigot / Paper**.
Elle permet de **stocker des données persistantes** sous forme de **fichiers plats (FlatFile)** tout en maintenant une **vitesse d’accès O(1)** grâce au stockage en RAM.

---

## 🚀 Fonctionnalités Clés

* ✅ Compatible **Java 14 à 21+**
* ✅ Compatible **Minecraft 1.14 à 1.21+**
* ⚡ **Accès mémoire ultra rapide** (`O(1)`)
* 💾 **Stockage plat (FlatFile)** sans dépendance à une base de données
* 🔄 **Sauvegarde / chargement asynchrones**
* 📦 Supporte de nombreux **types de données** (y compris Bukkit)
* 🧠 Données entièrement **chargées en RAM**
* 🧩 **API flexible** et en constante évolution

---

## 📊 Performances (1 million d’UUIDs)

| Opération | Temps | Complexité |
| :---------- | :--------- | :----------- |
| `set`       | 128ms      | O(1) ✅      |
| `get`       | 128ms      | O(1) ✅      |
| `save`      | 940ms      | —          |
| `load`      | 822ms      | —          |

> 💡 **Exemple de Consommation Mémoire** : une `HashMap` contenant **10 000 000 UUIDs** utilise environ **160 Mo** de RAM, avec un overhead total estimé à **300 Mo**.

---

## 🧩 Types de Données Supportés

### Types Natifs :

* `String`, `UUID`, `Integer`, `BigInteger`, `Double`, `BigDecimal`
* `Float`, `Short`, `Long`, `Boolean`, `Byte`, `Character`, `Material`

### Types Bukkit :

* `Location`, `ItemStack`

### Représentations Bukkit :

* `Chunk`, `World`, `Inventory`

### Collections & Maps :

* `List`, `Set`, `Array`
* `HashMap`, `LinkedHashMap`

---

## ⚙️ Installation & Intégration

### 1️⃣ Téléchargement

Téléchargez le JAR depuis la [page des releases](https://github.com/YourUsername/FlatFileStorageAPI/releases).

### 2️⃣ Ajout à votre projet

#### 📦 Maven

Placez le JAR dans le dossier `/libs` de votre projet, puis ajoutez cette dépendance dans votre `pom.xml` :

```xml
<dependency>
    <groupId>your.group.id</groupId>
    <artifactId>FlatFileStorageAPI</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/FlatFileStorageAPI.jar</systemPath>
</dependency>
```

#### ⚙️ Gradle

Ajoutez le fichier JAR comme dépendance locale (par exemple, dans le dossier `/libs`).

```gradle
repositories {
    flatDir {
        dirs 'libs' 
    }
}

dependencies {
    implementation name: 'FlatFileStorageAPI' // 'FlatFileStorageAPI' est le nom du fichier JAR sans extension
}
```

---

## 💡 Utilisation de l'API (`Var` Class)

La classe **`Var`** est le cœur de l’API — elle permet de stocker et manipuler vos données.

### Création ou Chargement d’une instance

Le chargement **asynchrone** est fortement recommandé pour éviter le lag sur le thread principal :

```java
// Remplacez 'plugin' par votre instance JavaPlugin
FlatFileStorageAPI.getOrLoadVarAsync(plugin, "data/playerdata.ffdata").thenAccept(var -> {
    // Utilisation de la variable 'var' ici
});
```

### 1\. Définir une valeur

```java
var.setValue(VarTypes.UUID, "user_unique_id", UUID.randomUUID());
```

### 2\. Récupérer une valeur

*   **Lecture simple** :
    ```java
    UUID value = var.getValue(VarTypes.UUID, "user_unique_id");
    ```

*   **Lecture avec valeur par défaut** :
    ```java
    UUID defaultValue = UUID.fromString("00000000-0000-0000-0000-000000000000");
    UUID value = var.getValue(VarTypes.UUID, "non_existent_key", defaultValue);
    ```

### 3\. Sauvegarder les données

La sauvegarde **asynchrone** est recommandée :

*   **Asynchrone (recommandé)** :
    ```java
    var.saveAsync(); 
    ```
*   **Synchrone (bloquante)** :
    ```java
    // var.saveSync(); 
    ```

### 4\. Décharger de la mémoire

Pour libérer une instance, supprimez simplement toutes vos références. Le Garbage Collector de Java s’en chargera, car l'API ne conserve **aucune référence forte**.

### 5\. Récupérer toutes les clés

```java
Set<String> keys = var.getKeys();
```

---

## 📁 Organisation Recommandée

Organisez vos fichiers par type de données pour maximiser la vitesse de chargement et de sauvegarde :

```bash
/plugins/FlatFileStorageAPI/data/
├── PlayerData.var
├── Islands.var
└── Economy.var
```

---

## 🛠 Exemple d’utilisation complète

```java
// 1. Chargement synchrone (à utiliser avec précaution)
Var var = FlatFileStorageAPI.getOrLoadVarSync(plugin, "data/playerdata.ffdata");

// 2. Sauvegarde d’une donnée
var.setValue(VarTypes.STRING, "playerName", "Notch");

// 3. Lecture
String name = var.getValue(VarTypes.STRING, "playerName"); 

// 4. Sauvegarde asynchrone
var.saveAsync();
```

---

## 🧪 Statut du Projet & Support

FlatFileStorageAPI est actuellement en **BETA** 🧬. De nouvelles fonctionnalités sont ajoutées régulièrement (ex. : support d’objets Bukkit complexes).

### 💬 Support & Idées

🧠 Idées, bugs, suggestions ?
Rejoignez le Discord officiel : [https://discord.gg/veZJqU5wkT](https://discord.gg/veZJqU5wkT)

---

## 📜 Licence

**Utilisation libre – Interdiction de redistribution sous un autre nom**

Vous êtes autorisé à :

*   ✅ Utiliser librement ce plugin sur n’importe quel serveur Minecraft.
*   ✅ Le modifier pour un usage personnel ou pour votre serveur.

Vous n’êtes **pas** autorisé à :

*   ❌ Le redistribuer sous votre propre nom.
*   ❌ Revendiquer sa propriété ou retirer les crédits d’origine.
*   ❌ Le vendre ou le monétiser, que ce soit le plugin ou son code source.

Auteur : SanTexFR
Année : 2025

❤️ **FlatFileStorageAPI** — Simplifie la persistance de tes données Minecraft, sans base de données.
