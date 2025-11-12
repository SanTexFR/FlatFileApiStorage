# FlatFileStorageAPI

![Java](https://img.shields.io/badge/Java-8--21+-orange?logo=openjdk)
![Minecraft](https://img.shields.io/badge/Minecraft-1.14--1.21+-brightgreen?logo=minecraft)
![License](https://img.shields.io/badge/license-Open%20Usage%20--%20No%20Redistribution%20Claim-blue)
![Status](https://img.shields.io/badge/status-Beta-yellow)

**FlatFileStorageAPI** est une API légère et simple d’utilisation pour les serveurs **Bukkit / Spigot / Paper**.  
Elle permet de **stocker des données persistantes** sous forme de **fichiers plats** tout en maintenant une **vitesse d’accès O(1)** grâce au stockage en RAM.

---

## 🚀 Fonctionnalités

- ✅ Compatible **Java 8 à 21+**
- ✅ Compatible **Minecraft 1.14 à 1.21+**
- ⚡ **Accès mémoire ultra rapide** (O(1))
- 💾 **Stockage plat (FlatFile)** sans dépendance à une base de données
- 🔄 **Sauvegarde / chargement asynchrones**
- 📦 Supporte de nombreux **types de données** (y compris Bukkit)
- 🧠 Données entièrement **chargées en RAM**
- 🧩 **API flexible** et en constante évolution

---

## 📊 Performances (1 million d’UUIDs)

| Opération | Temps | Complexité |
|------------|--------|------------|
| `set`      | 128ms  | O(1) ✅ |
| `get`      | 128ms  | O(1) ✅ |
| `save`     | 940ms  | — |
| `load`     | 822ms  | — |

> 💡 Exemple : une HashMap contenant **10 000 000 UUIDs** utilise environ **160 Mo** de RAM,  
> avec un overhead total estimé à **300 Mo**.

---

## 🧩 Types de données supportés

### Types natifs :
`String`, `UUID`, `Integer`, `BigInteger`, `Double`, `BigDecimal`,  
`Float`, `Short`, `Long`, `Boolean`, `Byte`, `Character`, `Material`

### Types Bukkit :
`Location`, `ItemStack`

### Représentations Bukkit :
`Chunk`, `World`, `Inventory`

### Collections :
`List`, `Set`, `Array`

### Maps :
`HashMap`, `LinkedHashMap`

---

## ⚙️ Installation & Intégration

### 1️⃣ Téléchargement
Télécharge le JAR depuis la [page des releases](https://github.com/YourUsername/FlatFileStorageAPI/releases).

### 2️⃣ Ajout à ton projet

#### 📦 Maven
Place le JAR dans le dossier `/libs` de ton projet, puis ajoute cette dépendance :

```xml
<dependency>
    <groupId>your.group.id</groupId>
    <artifactId>FlatFileStorageAPI</artifactId>
    <version>1.0</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/libs/FlatFileStorageAPI.jar</systemPath>
</dependency>
⚙️ Gradle
Ajoute le fichier JAR comme dépendance locale.

💡 Utilisation de Var
La classe Var est le cœur de l’API — elle permet de stocker et manipuler tes données.

Création d’une instance
java
Copier le code
getOrLoadVarAsync(instance, filePath).thenAccept(var -> {
    // Utilisation de la variable ici
});
1. Définir une valeur
java
Copier le code
var.setValue(VarTypes.UUID, "key", UUID.randomUUID());
2. Récupérer une valeur
java
Copier le code
UUID value = var.getValue(VarTypes.UUID, "key");
Avec valeur par défaut :

java
Copier le code
UUID value = var.getValue(VarTypes.UUID, "key", defaultValue);
3. Sauvegarder les données
Synchrone :
java
Copier le code
var.saveSync();
Asynchrone :
java
Copier le code
var.saveAsync();
Recommandé pour éviter le lag sur le thread principal.

4. Décharger de la mémoire
Aucune référence forte n’est conservée par l’API.
Pour libérer une instance, supprime simplement tes références, et le Garbage Collector s’en chargera.

5. Récupérer toutes les clés
java
Copier le code
Set<String> keys = var.getKeys();
📁 Organisation recommandée
Organise tes fichiers par type de données :

bash
Copier le code
/plugins/FlatFileStorageAPI/data/
├── PlayerData.ffdata
├── Islands.ffdata
├── Economy.ffdata
Cela maximise la vitesse de chargement et de sauvegarde.

🧪 Statut du projet
FlatFileStorageAPI est actuellement en BETA 🧬
De nouvelles fonctionnalités sont ajoutées régulièrement (ex. : support d’objets Bukkit complexes).

💬 Support & Contributions
🧠 Idées, bugs, suggestions ?
Rejoins le Discord officiel : https://discord.gg/veZJqU5wkT

🤝 Pull requests bienvenues !
Merci de documenter ton code et d’ajouter des tests si possible.

🛠 Exemple d’utilisation complète
java
Copier le code
Var var = FlatFileStorageAPI.getOrLoadVarSync(plugin, "data/playerdata.ffdata");

// Sauvegarde d’une donnée
var.setValue(VarTypes.STRING, "playerName", "Notch");

// Lecture
String name = var.getValue(VarTypes.STRING, "playerName");

// Sauvegarde asynchrone
var.saveAsync();
📜 Licence
Utilisation libre – Interdiction de redistribution sous un autre nom

Vous êtes autorisé à :

✅ Utiliser librement ce plugin sur n’importe quel serveur Minecraft.

✅ Le modifier pour un usage personnel ou pour votre serveur.

Vous n’êtes pas autorisé à :

❌ Le redistribuer sous votre propre nom.

❌ Revendiquer sa propriété ou retirer les crédits d’origine.

❌ Le vendre ou le monétiser, que ce soit le plugin ou son code source.

Auteur : SanTexFR
Année : 2025

❤️ FlatFileStorageAPI — Simplifie la persistance de tes données Minecraft, sans base de données.
