package fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused"})
public class BooleanType extends NormalMesh<Boolean>{
    //CONSTRUCTOR
    public BooleanType(){
        super(Boolean.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Boolean bool){
        return serializeBoolean(bool);
    }
    public @NotNull Boolean deserialize(byte[]bytes){
        return deserializeBoolean(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeBoolean(@NotNull Boolean bool){
        return new byte[]{(byte)(bool?1:0)};
    }
    public static Boolean deserializeBoolean(byte[]bytes){
        return bytes[0]!=0;
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Boolean>{
        //CONSTRUCTOR
        public SetType(){
            super(Boolean.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Boolean>bools){
            return serializeSet(bools,BooleanType::serializeBoolean);
        }
        public @NotNull Set<Boolean>deserialize(byte[]bytes){
            return deserializeSet(bytes,BooleanType::deserializeBoolean);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Boolean>{
        //CONSTRUCTOR
        public ListType(){
            super(Boolean.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Boolean>bools){
            return serializeList(bools,BooleanType::serializeBoolean);
        }
        public @NotNull List<Boolean>deserialize(byte[]bytes){
            return deserializeList(bytes,BooleanType::deserializeBoolean);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Boolean>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Boolean.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Boolean @NotNull[]bools){
            return serializeArray(bools,BooleanType::serializeBoolean);
        }
        public @NotNull Boolean @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,BooleanType::deserializeBoolean);
        }
    }
}