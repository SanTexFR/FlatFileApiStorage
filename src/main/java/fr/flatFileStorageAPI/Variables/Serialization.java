package fr.flatFileStorageAPI.Variables;

import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.Base64;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class Serialization{
    //VARIABLES
    public static final Base64.Encoder BASE64_ENCODER=Base64.getEncoder();
    public static final Base64.Decoder BASE64_DECODER=Base64.getDecoder();

    //METHODS
    public static byte[]serializeToBytes(@Nullable Object obj){
        if(obj==null)return new byte[0];
        try{
            try(final ByteArrayOutputStream bos=new ByteArrayOutputStream();
                final ObjectOutputStream oos=new ObjectOutputStream(bos)){
                oos.writeObject(obj);
                return bos.toByteArray();
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static @Nullable Object deserializeFromBytes(byte[]data){
        if(data.length==0)return null;
        try(final ByteArrayInputStream bis=new ByteArrayInputStream(data);
            final ObjectInputStream ois=new ObjectInputStream(bis)){
            return ois.readObject();
        }catch(IOException|ClassNotFoundException e){
            throw new RuntimeException("Failed to deserialize value",e);
        }
    }



    public static String serializeValue(@Nullable Object obj){
        try{
            try(ByteArrayOutputStream bos=new ByteArrayOutputStream();
                final ObjectOutputStream oos=new ObjectOutputStream(bos)){
                oos.writeObject(obj);
                return BASE64_ENCODER.encodeToString(bos.toByteArray());
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public static Object deserializeValue(@Nullable String serializedObj){
        try(ByteArrayInputStream bis=new ByteArrayInputStream(BASE64_DECODER.decode(serializedObj));
            final ObjectInputStream ois=new ObjectInputStream(bis)){
            return ois.readObject();
        }catch(ClassNotFoundException|IOException e){
            throw new RuntimeException("Failed to deserialize value", e);
        }
    }
}
