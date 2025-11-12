package fr.flatFileStorageAPI.Variables;

import fr.flatFileStorageAPI.FlatFileStorageAPI;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.MapMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.VarMapTypes;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.Vars;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.ref.Cleaner;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

@SuppressWarnings({"unused","UnusedReturnValue","unchecked"})
public class Var extends VarFile implements AutoCloseable {
    //STATIC VARIABLES
    private static final @NotNull ConcurrentHashMap<@NotNull String,@NotNull WeakReference<Var>> vars = new ConcurrentHashMap<>();
    private static final @NotNull Map<@NotNull String,@NotNull CompletableFuture<@NotNull Var>> loadingVars = new ConcurrentHashMap<>();
    private static final @NotNull ConcurrentHashMap<@NotNull String,@NotNull CompletableFuture<Void>> savingVars = new ConcurrentHashMap<>();

    //LOCAL VARIABLES
    private final @NotNull Plugin plugin;
    private ConcurrentHashMap<@NotNull String,@NotNull Object[]> data = new ConcurrentHashMap<>();
    private volatile boolean isSavingAsync;

    private final Cleaner.Cleanable cleanable;

    //CONSTRUCTOR
    private Var(@NotNull Plugin plugin,@NotNull String filePath) {
        super(new File(plugin.getDataFolder(),"data"), filePath);
        initialize();
        this.plugin = plugin;
        cleanable = FlatFileStorageAPI.getCleaner().register(this, new Unload(getVarPath()));
    }

    //METHODS

    //VAR
    public static @Nullable Var getVar(@NotNull Plugin plugin, @NotNull String filePath) {
        if (filePath.isEmpty() || !filePath.matches("[a-zA-Z0-9/_-]+"))
            throw new IllegalArgumentException("Var filePath must contain only alphabetic characters, numbers, '_', '-' or '/' and must not be empty.");
        final WeakReference<Var> weak = vars.get(plugin.getName() + "/" + filePath);
        return weak == null ? null : weak.get();
    }

    public static @NotNull Var getOrLoadVar(@NotNull Plugin plugin, @NotNull String filePath) {
        Var var = getVar(plugin, filePath);
        if (var != null) return var;

        var = new Var(plugin, filePath); // Création de l'objet Var
        vars.put(plugin.getName() + "/" + filePath, new WeakReference<>(var)); // Mise en cache de la variable chargée
        return var;
    }

    public static @NotNull CompletableFuture<Var> getOrLoadVarAsync(@NotNull Plugin plugin, @NotNull String filePath) {
        if (filePath.isEmpty() || !filePath.matches("[a-zA-Z0-9/_-]+")) {
            throw new IllegalArgumentException("Var filePath must contain only alphabetic characters, numbers, '_', '-' or '/' and must not be empty.");
        }

        final String key = plugin.getName() + "/" + filePath;

        // Vérifier si la variable existe déjà dans le cache des variables chargées
        final WeakReference<Var> weak = vars.get(key);
        if (weak != null) {
            final Var var = weak.get();
            if (var != null) return CompletableFuture.completedFuture(var);
        }

        // Gérer le cas où la variable est en cours de chargement (éviter la concurrence)
        return loadingVars.computeIfAbsent(key, k -> {
            CompletableFuture<Var> future = CompletableFuture.supplyAsync(() -> {
                try {
                    // Ici, nous effectuons le véritable chargement de la variable
                    Var var = new Var(plugin, filePath); // Création de l'objet Var
                    vars.put(k, new WeakReference<>(var)); // Mise en cache
                    return var;
                } catch (Exception e) {
                    plugin.getLogger().warning("Erreur lors du chargement de la variable: " + key + ": " + e.getMessage());
                    throw new RuntimeException("Échec du chargement de la variable", e);
                }
            });

            // Nettoyage une fois terminé
            future.whenComplete((res, ex) -> loadingVars.remove(k));
            return future;
        });
    }

    public long getFileSize() {
        File file = this.file;
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return 0L;
    }

    public static boolean isLoaded(@NotNull Plugin plugin, @NotNull String filePath) {
        return vars.containsKey(plugin.getName() + "/" + filePath);
    }

    //SAVING
    public synchronized CompletableFuture<Void> saveAsync() {
        final String key = getVarPath();
        if (isSavingAsync) {
            return savingVars.get(key); // Attendre la sauvegarde asynchrone en cours
        }

        isSavingAsync = true;

        CompletableFuture<Void> saveFuture = CompletableFuture.runAsync(() -> {
            try {
                super.save();
            } catch (Exception e) {
                plugin.getLogger().warning("Erreur lors de l'enregistrement asynchrone de " + getVarPath() + ": " + e.getMessage());
            }
        }).whenComplete((result, exception) -> {
            isSavingAsync = false;
            savingVars.remove(key);
        });

        savingVars.put(key, saveFuture);

        return saveFuture;
    }

    public synchronized void saveSync() {
        super.save();
    }

    @Deprecated
    public void unload() {
        this.data = null;
        vars.remove(getVarPath());
    }

    //NORMAL METHODS
    public synchronized <P> void setValue(@NotNull VarType<P> type, @NotNull String key, P value) {
        if (value != null) this.data.put(key, new Object[]{value, type});
        else remove(key);
    }

    public @Nullable <P> P getValue(@NotNull VarType<P> type, @NotNull String key) {
        final Object[] values = this.data.get(key);
        if (values == null || !((Vars) values[1]).getStringType().equals(type.getStringType())) return null;
        return (P) values[0];
    }

    public @NotNull <P> P getValue(@NotNull VarType<P> type, @NotNull String key, @NotNull P def) {
        return Objects.requireNonNullElse(getValue(type, key), def);
    }

    //MAP METHODS
    public synchronized <P, C> void setMap(@NotNull VarMapTypes mapType, @NotNull VarType<P> keyType, @NotNull VarType<C> valueType, @NotNull String key, @Nullable Map<P, C> map) {
        if (map != null) data.put(key, new Object[]{map, new MapMesh<>(mapType, keyType, valueType)});
        else data.remove(key);
    }

    public @Nullable <P, C> Map<P, C> getMap(@NotNull VarMapTypes mapType, @NotNull VarType<P> keyType, @NotNull VarType<C> valueType, @NotNull String key) {
        final Object[] values = this.data.get(key);
        if (values == null || !((Vars) values[1]).getStringType().equals(mapType.name() + "¦" + keyType.getStringType() + "¦" + valueType.getStringType()))
            return null;
        return (Map<P, C>) values[0];
    }

    public @NotNull <P, C> Map<P, C> getMap(@NotNull VarMapTypes mapType, @NotNull VarType<P> keyType, @NotNull VarType<C> valueType, @NotNull String key, @NotNull Map<P, C> def) {
        final Map<P, C> map = getMap(mapType, keyType, valueType, key);
        return map == null ? def : map;
    }

    //BASICS
    public void remove(@NotNull String key) {
        this.data.remove(key);
    }

    public void clear() {
        this.data.clear();
    }

    //OTHERS
    public @NotNull Set<@NotNull String> getKeys() {
        return new HashSet<>(this.data.keySet());
    }

    public @NotNull String getVarPath() {
        return this.plugin.getName() + "/" + super.filePath;
    }

    public @NotNull ConcurrentHashMap<@NotNull String, @NotNull Object[]> getData() {
        return this.data;
    }

    //LISTENERS
    public static void unloadAllVars() {

    }

    @Override
    public void close() {
        cleanable.clean();
    }

    //INNER CLASS
    private record Unload(@NotNull String path) implements Runnable {
        @Override
        public void run() {
            vars.remove(path);
        }
    }
}
