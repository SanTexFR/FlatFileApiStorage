package fr.flatFileStorageAPI;

import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import fr.flatFileStorageAPI.Variables.Var;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.lang.ref.Cleaner;
import java.util.*;
import java.util.logging.Logger;

@SuppressWarnings({"unused","UnusedReturnValue"})
public final class FlatFileStorageAPI extends JavaPlugin{
    //VARIABLES
    public static Logger logger=Bukkit.getLogger();
    private static FlatFileStorageAPI instance;
    private static final Cleaner cleaner=Cleaner.create();
    private static ServerImplementation serverImplementation;

    //METHODS
    static{
        VarTypes.initializer();
    }
    @Override
    public void onEnable(){
        //INSTANCE
        instance=this;

        serverImplementation=new FoliaCompatibility(this).getServerImplementation();

        //BSTATS
        final Metrics metrics=new Metrics(this,24917);
        metrics.addCustomChart(new Metrics.SimplePie("chart_id",()->"My value"));

        //YML
        saveDefaultConfig();
        reloadConfig();
        //TEST
        //testSequence();
    }

    @Override
    public void onDisable(){
        Var.unloadAllVars();
    }

    //CUSTOM METHODS
    public static ServerImplementation getServerImplementation(){
        return serverImplementation;
    }
    public static @NotNull FlatFileStorageAPI getInstance(){
        return instance;
    }
    public static @NotNull FileConfiguration getConfiguration(){
        return getInstance().getConfig();
    }
    public static @NotNull Cleaner getCleaner(){
        return cleaner;
    }


    //TEST
    @TestOnly
    private static void testSequence(){
        test();
    }
    @TestOnly
    private static void test(){
        System.out.println("(------- TEST -------)");
        Var.getOrLoadVarAsync(getInstance(),"test").thenAccept(var->{
            final UUID uuid=UUID.randomUUID();
            long time=System.currentTimeMillis();
            for(int i=0;i<1000;i++)
                var.setValue(VarTypes.UUID,"uuid"+i, UUID.randomUUID());
            System.out.println("Set 1.000.000 values time: "+(System.currentTimeMillis()-time)+"ms");
            var.saveSync();
            var.unload();

            getServerImplementation().global().runDelayed(()->{
                test3();
            },60L);
        });
    }
    @TestOnly
    private static void test2(){
        final Var var=Var.getVar(getInstance(),"test");

        long time=System.currentTimeMillis();
        for(int i=0;i<1000;i++)
            var.getValue(VarTypes.UUID,"uuid"+i);
        System.out.println("Get 1.000.000 values time: "+(System.currentTimeMillis()-time)+"ms");
        var.unload();

        getServerImplementation().global().runDelayed(()->{
            test3();
        },20L);
    }
    @TestOnly
    private static void test3(){
        long time=System.currentTimeMillis();
        Var.getOrLoadVarAsync(getInstance(),"test").thenAccept(var->{
            System.out.println("async initialize time: "+(System.currentTimeMillis()-time)+"ms");
            var.unload();

            getServerImplementation().global().runDelayed(()->{
                test4();
            },20L);
        }).exceptionally(ex -> {
            // Handle any exceptions (in case of errors in loading the var)
            System.err.println("Error loading variable: " + ex.getMessage());
            return null;
        });


    }
    @TestOnly
    private static void test4(){
//        Var.getOrLoadVarAsync(getInstance(),"test").thenAccept(var->{
//            long time=System.currentTimeMillis();
//            var.saveAsync().thenRun(()->{
//                System.out.println("async save time: "+(System.currentTimeMillis()-time)+"ms");
//                var.unload();
//                Bukkit.getScheduler().runTaskLater(getInstance(),()->{
//                    Var.getOrLoadVarAsync(getInstance(),"test").thenAccept(var2->{
//                        long time2=System.currentTimeMillis();
//                        var2.saveSync();
//                        System.out.println("sync save time: "+(System.currentTimeMillis()-time2)+"ms");
//                        var2.unload();
//                    });
//                },20L);
//            });
//        });
    }
}