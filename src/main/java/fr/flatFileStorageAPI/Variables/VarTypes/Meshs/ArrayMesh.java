package fr.flatFileStorageAPI.Variables.VarTypes.Meshs;

import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.Vars;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings({"unused","UnusedReturnValue","unchecked"})
public abstract class ArrayMesh<P>implements VarType<P[]>{
    //VARIABLES
    protected final Class<P>type;

    //CONSTRUCTOR
    public ArrayMesh(@NotNull Class<P>type){
        this.type=type;
    }


    //METHODS
    public @NotNull Class<P>getType(){
        return this.type;
    }
    public @NotNull String getStringType(){
        return"Array<"+getType().getSimpleName()+">";
    }

    public boolean equals(@NotNull Vars varType){
        return varType.isArray()&&varType.getStringType().equals(getStringType());
    }

    protected byte[]serializeArray(@NotNull P @NotNull[]obj,@NotNull Function<P,byte[]>serializeMethod){
        return serializeCollection(Arrays.asList(obj),serializeMethod);
    }
    protected @NotNull P[]deserializeArray(byte[]bytes,@NotNull Function<byte[],Object>deserializeMethod){
        final List<P>deserializedList=(List<P>)deserializeCollection(bytes,new ArrayList<>(),deserializeMethod);
        return deserializedList.toArray((P[])java.lang.reflect.Array.newInstance(deserializedList.getFirst().getClass(),deserializedList.size()));
    }

    public boolean isArray(){
        return true;
    }
}