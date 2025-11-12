package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bigs;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class BigDecimalType extends NormalMesh<BigDecimal>{
    //VARIABLES
    private static final @NotNull BigIntegerType bigIntegerType;
    static{
        final VarType<?>type=VarTypes.valueOf("BIG_INTEGER");
        if(type==null)bigIntegerType=new BigIntegerType();
        else bigIntegerType=(BigIntegerType)type;
    }

    //CONSTRUCTOR
    public BigDecimalType(){
        super(BigDecimal.class);
    }

    //METHODS
    public byte[]serialize(@NotNull BigDecimal bDecimal){
        return serializeBigDecimal(bDecimal);
    }
    public @NotNull BigDecimal deserialize(byte[]bytes){
        return deserializeBigDecimal(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeBigDecimal(@NotNull BigDecimal bDecimal){
        final byte[]bigIntBytes=BigIntegerType.serializeBigInteger(bDecimal.unscaledValue());
        final ByteBuffer buffer=ByteBuffer.allocate(bigIntBytes.length+8);
        buffer.putInt(bigIntBytes.length);
        buffer.put(bigIntBytes);
        buffer.putInt(bDecimal.scale());
        return buffer.array();
    }
    public static @NotNull BigDecimal deserializeBigDecimal(byte[]bytes){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        final byte[]bigIntBytes=new byte[buffer.getInt()];
        buffer.get(bigIntBytes);
        final BigInteger bInt=bigIntegerType.deserialize(bigIntBytes);
        final int scale=buffer.getInt();
        return new BigDecimal(bInt,scale);
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<BigDecimal>{
        //CONSTRUCTOR
        public SetType(){
            super(BigDecimal.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull BigDecimal>bDecimals){
            return serializeSet(bDecimals,BigDecimalType::serializeBigDecimal);
        }
        public @NotNull Set<BigDecimal>deserialize(byte[]bytes){
            return deserializeSet(bytes,BigDecimalType::deserializeBigDecimal);
        }
    }

    //LIST
    public static class ListType extends ListMesh<BigDecimal>{
        //CONSTRUCTOR
        public ListType(){
            super(BigDecimal.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull BigDecimal>bDecimals){
            return serializeList(bDecimals,BigDecimalType::serializeBigDecimal);
        }
        public @NotNull List<BigDecimal>deserialize(byte[]bytes){
            return deserializeList(bytes,BigDecimalType::deserializeBigDecimal);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<BigDecimal>{
        //CONSTRUCTOR
        public ArrayType(){
            super(BigDecimal.class);
        }

        //METHODS
        public byte[]serialize(@NotNull BigDecimal @NotNull[]bDecimals){
            return serializeArray(bDecimals,BigDecimalType::serializeBigDecimal);
        }
        public @NotNull BigDecimal @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,BigDecimalType::deserializeBigDecimal);
        }
    }
}