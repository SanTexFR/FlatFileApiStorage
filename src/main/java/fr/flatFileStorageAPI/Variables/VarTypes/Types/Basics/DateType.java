package fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class DateType extends NormalMesh<Date>{
    //CONSTRUCTOR
    public DateType(){
        super(Date.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Date date){
        return serializeDate(date);
    }
    public @NotNull Date deserialize(byte[]bytes){
        return deserializeDate(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeDate(@NotNull Date date){
        return ByteBuffer.allocate(Long.BYTES).putLong(date.getTime()).array();
    }
    public static Date deserializeDate(byte[]bytes){
        return new Date(ByteBuffer.wrap(bytes).getLong());
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Date>{
        //CONSTRUCTOR
        public SetType(){
            super(Date.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Date>dates){
            return serializeSet(dates,DateType::serializeDate);
        }
        public @NotNull Set<Date>deserialize(byte[]bytes){
            return deserializeSet(bytes,DateType::deserializeDate);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Date>{
        //CONSTRUCTOR
        public ListType(){
            super(Date.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Date>dates){
            return serializeList(dates,DateType::serializeDate);
        }
        public @NotNull List<Date>deserialize(byte[]bytes){
            return deserializeList(bytes,DateType::deserializeDate);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Date>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Date.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Date @NotNull[]dates){
            return serializeArray(dates,DateType::serializeDate);
        }
        public @NotNull Date @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,DateType::deserializeDate);
        }
    }
}