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
public class ShortType extends NormalMesh<Short>{
    //CONSTRUCTOR
    public ShortType(){
        super(Short.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Short sht){
        return serializeShort(sht);
    }
    public @NotNull Short deserialize(byte[]bytes){
        return deserializeShort(bytes);

    }

    //STATIC METHODS
    public static byte[]serializeShort(@NotNull Short sht){
        return ByteBuffer.allocate(Short.BYTES).putShort(sht).array();
    }
    public static Short deserializeShort(byte[]bytes){
        return ByteBuffer.wrap(bytes).getShort();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Short>{
        //CONSTRUCTOR
        public SetType(){
            super(Short.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Short>shts){
            return serializeSet(shts,ShortType::serializeShort);
        }
        public @NotNull Set<Short>deserialize(byte[]bytes){
            return deserializeSet(bytes,ShortType::deserializeShort);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Short>{
        //CONSTRUCTOR
        public ListType(){
            super(Short.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Short>shts){
            return serializeList(shts,ShortType::serializeShort);
        }
        public @NotNull List<Short>deserialize(byte[]bytes){
            return deserializeList(bytes,ShortType::deserializeShort);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Short>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Short.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Short @NotNull[]shts){
            return serializeArray(shts,ShortType::serializeShort);
        }
        public @NotNull Short @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,ShortType::deserializeShort);
        }
    }
}