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
public class IntegerType extends NormalMesh<Integer>{
    //CONSTRUCTOR
    public IntegerType(){
        super(Integer.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Integer str){
        return serializeInteger(str);
    }
    public @NotNull Integer deserialize(byte[]bytes){
        return deserializeInteger(bytes);

    }

    //STATIC METHODS
    public static byte[]serializeInteger(@NotNull Integer integer){
        return ByteBuffer.allocate(Integer.BYTES).putInt(integer).array();
    }
    public static Integer deserializeInteger(byte[]bytes){
        return ByteBuffer.wrap(bytes).getInt();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Integer>{
        //CONSTRUCTOR
        public SetType(){
            super(Integer.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Integer>ints){
            return serializeSet(ints,IntegerType::serializeInteger);
        }
        public @NotNull Set<Integer>deserialize(byte[]bytes){
            return deserializeSet(bytes,IntegerType::deserializeInteger);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Integer>{
        //CONSTRUCTOR
        public ListType(){
            super(Integer.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Integer>ints){
            return serializeList(ints,IntegerType::serializeInteger);
        }
        public @NotNull List<Integer>deserialize(byte[]bytes){
            return deserializeList(bytes,IntegerType::deserializeInteger);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Integer>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Integer.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Integer @NotNull[]ints){
            return serializeArray(ints,IntegerType::serializeInteger);
        }
        public @NotNull Integer @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,IntegerType::deserializeInteger);
        }
    }
}