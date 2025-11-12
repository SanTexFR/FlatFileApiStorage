package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bigs;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class BigIntegerType extends NormalMesh<BigInteger>{
    //CONSTRUCTOR
    public BigIntegerType(){
        super(BigInteger.class);
    }

    //METHODS
    public byte[]serialize(@NotNull BigInteger bInt){
        return serializeBigInteger(bInt);
    }
    public @NotNull BigInteger deserialize(byte[]bytes){
        return deserializeBigInteger(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeBigInteger(@NotNull BigInteger bInt){
        return bInt.toByteArray();
    }
    public static @NotNull BigInteger deserializeBigInteger(byte[]bytes){
        return new BigInteger(bytes);
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<BigInteger>{
        //CONSTRUCTOR
        public SetType(){
            super(BigInteger.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull BigInteger>bInts){
            return serializeSet(bInts,BigIntegerType::serializeBigInteger);
        }
        public @NotNull Set<BigInteger>deserialize(byte[]bytes){
            return deserializeSet(bytes,BigIntegerType::deserializeBigInteger);
        }
    }

    //LIST
    public static class ListType extends ListMesh<BigInteger>{
        //CONSTRUCTOR
        public ListType(){
            super(BigInteger.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull BigInteger>bInts){
            return serializeList(bInts,BigIntegerType::serializeBigInteger);
        }
        public @NotNull List<BigInteger>deserialize(byte[]bytes){
            return deserializeList(bytes,BigIntegerType::deserializeBigInteger);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<BigInteger>{
        //CONSTRUCTOR
        public ArrayType(){
            super(BigInteger.class);
        }

        //METHODS
        public byte[]serialize(@NotNull BigInteger @NotNull[]bInts){
            return serializeArray(bInts,BigIntegerType::serializeBigInteger);
        }
        public @NotNull BigInteger @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,BigIntegerType::deserializeBigInteger);
        }
    }
}