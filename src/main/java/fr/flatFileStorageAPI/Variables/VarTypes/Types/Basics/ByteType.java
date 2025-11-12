package fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused"})
public class ByteType extends NormalMesh<Byte>{
    //CONSTRUCTOR
    public ByteType(){
        super(Byte.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Byte b){
        return serializeByte(b);
    }
    public @NotNull Byte deserialize(byte[]bytes){
        return deserializeByte(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeByte(@NotNull Byte b){
        return new byte[]{b};
    }
    public static Byte deserializeByte(byte[]bytes){
        return bytes[0];
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Byte>{
        //CONSTRUCTOR
        public SetType(){
            super(Byte.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Byte>bs){
            return serializeSet(bs,ByteType::serializeByte);
        }
        public @NotNull Set<Byte>deserialize(byte[]bytes){
            return deserializeSet(bytes,ByteType::deserializeByte);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Byte>{
        //CONSTRUCTOR
        public ListType(){
            super(Byte.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Byte>bs){
            return serializeList(bs,ByteType::serializeByte);
        }
        public @NotNull List<Byte>deserialize(byte[]bytes){
            return deserializeList(bytes,ByteType::deserializeByte);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Byte>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Byte.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Byte @NotNull[]bs){
            return serializeArray(bs,ByteType::serializeByte);
        }
        public @NotNull Byte @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,ByteType::deserializeByte);
        }
    }
}