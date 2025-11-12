package fr.flatFileStorageAPI.Variables.VarTypes.Meshs;

import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.Vars;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

@SuppressWarnings({"unused","UnusedReturnValue","unchecked"})
public abstract class SetMesh<P>implements VarType<Set<P>>{
    //VARIABLES
    protected final Class<P>type;

    //CONSTRUCTOR
    public SetMesh(@NotNull Class<P>type){
        this.type=type;
    }


    //METHODS
    public @NotNull Class<P>getType(){
        return this.type;
    }
    public @NotNull String getStringType(){
        return"Set<"+getType().getSimpleName()+">";
    }

    public boolean equals(@NotNull Vars varType){
        return varType.isSet()&&varType.getStringType().equals(getStringType());
    }

    protected byte[]serializeSet(@NotNull Set<P>obj,@NotNull Function<P,byte[]>serializeMethod){
        return serializeCollection(obj,serializeMethod);
    }
    protected @NotNull Set<P>deserializeSet(byte[]bytes,@NotNull Function<byte[],Object>deserializeMethod){
        return(Set<P>)deserializeCollection(bytes,new HashSet<>(),deserializeMethod);
    }

    public boolean isSet(){
        return true;
    }
}