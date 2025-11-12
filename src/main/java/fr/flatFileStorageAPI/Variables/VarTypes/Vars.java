package fr.flatFileStorageAPI.Variables.VarTypes;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.function.Function;

@SuppressWarnings({"unused","UnusedReturnValue"})
public interface Vars{
    @NotNull String getStringType();
    boolean equals(@NotNull Vars varType);

    default boolean isNormal(){
        return false;
    }
    default boolean isList(){
        return false;
    }
    default boolean isSet(){
        return false;
    }
    default boolean isArray(){
        return false;
    }
    default boolean isMap(){
        return false;
    }

    default boolean isCustom(){return false; }

    default<T>byte[]serializeCollection(@NotNull Collection<T>collection,@NotNull Function<T,byte[]>serializeMethod){
        final byte[][]bytes=new byte[collection.size()][];

        int length=0,count=0;
        for(final T obj:collection){
            bytes[count]=serializeMethod.apply(obj);
            length+=bytes[count].length+Integer.BYTES;
            count++;
        }
        final ByteBuffer buffer=ByteBuffer.allocate(length);
        for(byte[]data:bytes){
            buffer.putInt(data.length);
            buffer.put(data);
        }

        return buffer.array();
    }

    default<T>Collection<T>deserializeCollection(byte[]bytes,Collection<T>collection,@NotNull Function<byte[],? extends T>deserializeMethod){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        while(buffer.hasRemaining()){
            int length=buffer.getInt();
            final byte[]objData=new byte[length];
            buffer.get(objData);
            collection.add(deserializeMethod.apply(objData));
        }return collection;
    }
}