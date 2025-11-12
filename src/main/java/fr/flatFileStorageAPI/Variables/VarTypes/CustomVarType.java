package fr.flatFileStorageAPI.Variables.VarTypes;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class CustomVarType<P>implements VarType<P>{
    //VARIABLES (STATICS)
    public Class<P>type;
    public static final @NotNull ConcurrentHashMap<@NotNull String,@NotNull CustomVarType<?>>customTypes=new ConcurrentHashMap<>();

    //CONSTRUCTOR
    public CustomVarType(@NotNull Class<P>type){
        this.type=type;
        customTypes.put(getStringType(),this);
    }

    //OTHERS
    public @NotNull Class<P>getType(){
        return this.type;
    }
    public @NotNull String getStringType(){
        return getType().getSimpleName();
    }

    public boolean isCustom(){
        return true;
    }
    public boolean equals(@NotNull Vars varType){
        return varType.isCustom()&&varType.getStringType().equals(getStringType());
    }
}