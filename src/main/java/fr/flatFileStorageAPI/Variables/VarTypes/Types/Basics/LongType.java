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
public class LongType extends NormalMesh<Long>{
    //CONSTRUCTOR
    public LongType(){
        super(Long.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Long lng){
        return serializeLong(lng);
    }
    public @NotNull Long deserialize(byte[]bytes){
        return deserializeLong(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeLong(@NotNull Long lng){
        return ByteBuffer.allocate(Long.BYTES).putLong(lng).array();
    }
    public static Long deserializeLong(byte[]bytes){
        return ByteBuffer.wrap(bytes).getLong();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Long>{
        //CONSTRUCTOR
        public SetType(){
            super(Long.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Long>lngs){
            return serializeSet(lngs,LongType::serializeLong);
        }
        public @NotNull Set<Long>deserialize(byte[]bytes){
            return deserializeSet(bytes,LongType::deserializeLong);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Long>{
        //CONSTRUCTOR
        public ListType(){
            super(Long.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Long>lngs){
            return serializeList(lngs,LongType::serializeLong);
        }
        public @NotNull List<Long>deserialize(byte[]bytes){
            return deserializeList(bytes,LongType::deserializeLong);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Long>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Long.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Long @NotNull[]lngs){
            return serializeArray(lngs,LongType::serializeLong);
        }
        public @NotNull Long @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,LongType::deserializeLong);
        }
    }
}