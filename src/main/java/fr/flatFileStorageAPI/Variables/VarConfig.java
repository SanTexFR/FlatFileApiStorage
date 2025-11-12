package fr.flatFileStorageAPI.Variables;

import fr.flatFileStorageAPI.FlatFileStorageAPI;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class VarConfig{
    public static boolean varDefaultWorld=FlatFileStorageAPI.getConfiguration().getBoolean("varDefaultWorld",true);
    public static void reloadConfiguration(){
        varDefaultWorld=FlatFileStorageAPI.getConfiguration().getBoolean("varDefaultWorld");
    }
}
