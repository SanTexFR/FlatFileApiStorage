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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"unused","UnusedReturnValue","unchecked"})
public class Var extends VarFile implements AutoCloseable {
    //STATIC VARIABLES
    private static final ExecutorService VIRTUAL_EXECUTOR = Executors.newVirtualThreadPerTaskExecutor();
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

        WeakReference<Var> weakRef = new WeakReference<>(this);
        cleanable = FlatFileStorageAPI.getCleaner().register(this, new Unload(getVarPath(), weakRef));
    }

    //METHODS

    //VAR
    public static @NotNull ConcurrentHashMap<@NotNull String,@NotNull WeakReference<Var>>getVars(){
        return vars;
    }
    public static @Nullable Var getVar(@NotNull Plugin plugin, @NotNull String filePath) {
        if (filePath.isEmpty() || !filePath.matches("[a-zA-Z0-9/_-]+"))
            throw new IllegalArgumentException("Var filePath must contain only alphabetic characters, numbers, '_', '-' or '/' and must not be empty.");
        final WeakReference<Var> weak = vars.get(plugin.getName() + "/" + filePath);
        return weak == null ? null : weak.get();
    }

    public static @NotNull Var getOrLoadVar(@NotNull Plugin plugin, @NotNull String filePath) {
        return getOrLoadVarAsync(plugin, filePath).join();
    }

//    public static @NotNull CompletableFuture<Var> getOrLoadVarAsync(@NotNull Plugin plugin, @NotNull String filePath) {
//        final String key = plugin.getName() + "/" + filePath;
//
//        // 1. Vérifier le cache de références faibles
//        WeakReference<Var> weak = vars.get(key);
//        Var existing = (weak != null) ? weak.get() : null;
//        if (existing != null) return CompletableFuture.completedFuture(existing);
//
//        // 2. Utiliser computeIfAbsent sur loadingVars pour garantir une seule exécution
//        return loadingVars.computeIfAbsent(key, k ->
//                CompletableFuture.supplyAsync(() -> new Var(plugin, filePath))
//                        .thenApply(var -> {
//                            vars.put(key, new WeakReference<>(var));
//                            return var;
//                        })
//                        .whenComplete((v, ex) -> loadingVars.remove(key))
//        );
//    }
    public static @NotNull CompletableFuture<Var> getOrLoadVarAsync(
            @NotNull Plugin plugin,
            @NotNull String filePath
    ) {
        final String key = plugin.getName() + "/" + filePath;

        // 1. Vérification rapide dans le cache des vars déjà chargées
        WeakReference<Var> weak = vars.get(key);
        Var existing = (weak != null) ? weak.get() : null;
        if (existing != null) {
            return CompletableFuture.completedFuture(existing);
        }

        // 2. Atomicité garantie par computeIfAbsent
        CompletableFuture<Var> future = loadingVars.computeIfAbsent(key, k ->
                CompletableFuture.supplyAsync(() -> {
                    // Re-vérification à l'intérieur du thread async au cas où
                    WeakReference<Var> w = vars.get(k);
                    Var v = (w != null) ? w.get() : null;
                    if (v != null) return v;

                    Var newVar = new Var(plugin, filePath);
                    vars.put(k, new WeakReference<>(newVar));
                    return newVar;
                }, VIRTUAL_EXECUTOR)
        );

        future.whenComplete((v, ex) -> loadingVars.remove(key, future));

        return future;
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
        },VIRTUAL_EXECUTOR).whenComplete((result, exception) -> {
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
        // 1. Vider les données locales
        this.data.clear();

        // 2. Nettoyage manuel du cache 'vars'
        WeakReference<Var> currentRef = vars.get(getVarPath());
        if (currentRef != null && currentRef.get() == this) {
            vars.remove(getVarPath(), currentRef); // Supprime uniquement si c'est cette instance précise
        }

        // 3. Désactiver le Cleaner pour qu'il ne se réexécute pas plus tard inutilement
        if (this.cleanable != null) {
            this.cleanable.clean();
        }
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
    private record Unload(@NotNull String path, @NotNull WeakReference<Var> weakRef) implements Runnable {
        @Override
        public void run() {
            // Supprime seulement si la map contient encore cette WeakReference précise
            vars.remove(path, weakRef);
        }
    }
}
