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
public class DoubleType extends NormalMesh<Double>{
    //CONSTRUCTOR
    public DoubleType(){
        super(Double.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Double d){
        return serializeDouble(d);
    }
    public @NotNull Double deserialize(byte[]bytes){
        return deserializeDouble(bytes);

    }

    //STATIC METHODS
    public static byte[]serializeDouble(@NotNull Double d){
        final ByteBuffer buffer=ByteBuffer.allocate(Double.BYTES);
        buffer.putDouble(d);
        return buffer.array();
    }
    public static Double deserializeDouble(byte[]bytes){
        return ByteBuffer.wrap(bytes).getDouble();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Double>{
        //CONSTRUCTOR
        public SetType(){
            super(Double.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Double>ds){
            return serializeSet(ds,DoubleType::serializeDouble);
        }
        public @NotNull Set<Double>deserialize(byte[]bytes){
            return deserializeSet(bytes,DoubleType::deserializeDouble);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Double>{
        //CONSTRUCTOR
        public ListType(){
            super(Double.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Double>ds){
            return serializeList(ds,DoubleType::serializeDouble);
        }
        public @NotNull List<Double>deserialize(byte[]bytes){
            return deserializeList(bytes,DoubleType::deserializeDouble);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Double>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Double.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Double @NotNull[]ds){
            return serializeArray(ds,DoubleType::serializeDouble);
        }
        public @NotNull Double @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,DoubleType::deserializeDouble);
        }
    }
}