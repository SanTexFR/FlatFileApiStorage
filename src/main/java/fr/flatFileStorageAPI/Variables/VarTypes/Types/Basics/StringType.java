package fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused"})
public class StringType extends NormalMesh<String>{
    //CONSTRUCTOR
    public StringType(){
        super(String.class);
    }

    //METHODS
    public byte[]serialize(@NotNull String str){
        return serializeString(str);
    }
    public @NotNull String deserialize(byte[]bytes){
        return deserializeString(bytes);

    }

    //STATIC METHODS
    public static byte[]serializeString(@NotNull String str){
        return str.getBytes(StandardCharsets.UTF_8);
    }
    public static String deserializeString(byte[]bytes){
        return new String(bytes,StandardCharsets.UTF_8);
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<String>{
        //CONSTRUCTOR
        public SetType(){
            super(String.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull String>strs){
            return serializeSet(strs,StringType::serializeString);
        }
        public @NotNull Set<String>deserialize(byte[]bytes){
            return deserializeSet(bytes,StringType::deserializeString);
        }
    }

    //LIST
    public static class ListType extends ListMesh<String>{
        //CONSTRUCTOR
        public ListType(){
            super(String.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull String>strs){
            return serializeList(strs,StringType::serializeString);
        }
        public @NotNull List<String>deserialize(byte[]bytes){
            return deserializeList(bytes,StringType::deserializeString);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<String>{
        //CONSTRUCTOR
        public ArrayType(){
            super(String.class);
        }

        //METHODS
        public byte[]serialize(@NotNull String @NotNull[]strs){
            return serializeArray(strs,StringType::serializeString);
        }
        public @NotNull String @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,StringType::deserializeString);
        }
    }
}