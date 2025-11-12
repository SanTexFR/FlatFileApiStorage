package fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused"})
public class FloatType extends NormalMesh<Float>{
    //CONSTRUCTOR
    public FloatType(){
        super(Float.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Float f){
        return serializeFloat(f);
    }
    public @NotNull Float deserialize(byte[]bytes){
        return deserializeFloat(bytes);

    }

    //STATIC METHODS
    public static byte[]serializeFloat(@NotNull Float f){
        return ByteBuffer.allocate(Float.BYTES).putFloat(f).array();
    }
    public static Float deserializeFloat(byte[]bytes){
        return ByteBuffer.wrap(bytes).getFloat();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Float>{
        //CONSTRUCTOR
        public SetType(){
            super(Float.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Float>fs){
            return serializeSet(fs,FloatType::serializeFloat);
        }
        public @NotNull Set<Float>deserialize(byte[]bytes){
            return deserializeSet(bytes,FloatType::deserializeFloat);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Float>{
        //CONSTRUCTOR
        public ListType(){
            super(Float.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Float>fs){
            return serializeList(fs,FloatType::serializeFloat);
        }
        public @NotNull List<Float>deserialize(byte[]bytes){
            return deserializeList(bytes,FloatType::deserializeFloat);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Float>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Float.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Float @NotNull[]fs){
            return serializeArray(fs,FloatType::serializeFloat);
        }
        public @NotNull Float @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,FloatType::deserializeFloat);
        }
    }
}