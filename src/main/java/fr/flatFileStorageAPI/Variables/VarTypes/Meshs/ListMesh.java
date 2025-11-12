package fr.flatFileStorageAPI.Variables.VarTypes.Meshs;

import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.Vars;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings({"unused","UnusedReturnValue","unchecked"})
public abstract class ListMesh<P>implements VarType<List<P>>{
    //VARIABLES
    protected final Class<P>type;

    //CONSTRUCTOR
    public ListMesh(@NotNull Class<P>type){
        this.type=type;
    }


    //METHODS
    public @NotNull Class<P>getType(){
        return this.type;
    }
    public @NotNull String getStringType(){
        return"List<"+getType().getSimpleName()+">";
    }

    public boolean equals(@NotNull Vars varType){
        return varType.isList()&&varType.getStringType().equals(getStringType());
    }

    protected byte[]serializeList(@NotNull List<P>obj,@NotNull Function<P,byte[]>serializeMethod){
        return serializeCollection(obj,serializeMethod);
    }
    protected @NotNull List<P> deserializeList(byte[]bytes, @NotNull Function<byte[],Object>deserializeMethod){
        return(List<P>)deserializeCollection(bytes,new ArrayList<>(),deserializeMethod);
    }

    public boolean isList(){
        return true;
    }
}