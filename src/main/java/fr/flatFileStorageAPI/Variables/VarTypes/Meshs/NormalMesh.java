package fr.flatFileStorageAPI.Variables.VarTypes.Meshs;

import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.Vars;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class NormalMesh<P>implements VarType<P>{
    //VARIABLES
    protected final Class<P>type;

    //CONSTRUCTOR
    public NormalMesh(@NotNull Class<P>type){
        this.type=type;
    }


    //METHODS
    public @NotNull Class<P>getType(){
        return this.type;
    }
    public @NotNull String getStringType(){
        return getType().getSimpleName();
    }

    public boolean equals(@NotNull Vars varType){
        return varType.isNormal()&&varType.getStringType().equals(getStringType());
    }

    public boolean isNormal(){
        return true;
    }
}