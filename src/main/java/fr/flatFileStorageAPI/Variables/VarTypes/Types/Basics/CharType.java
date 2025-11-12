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
public class CharType extends NormalMesh<Character>{
    //CONSTRUCTOR
    public CharType(){
        super(Character.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Character chr){
        return serializeChar(chr);
    }
    public @NotNull Character deserialize(byte[]bytes){
        return deserializeChar(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeChar(@NotNull Character chr){
        return ByteBuffer.allocate(Character.BYTES).putChar(chr).array();
    }
    public static Character deserializeChar(byte[]bytes){
        return ByteBuffer.wrap(bytes).getChar();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Character>{
        //CONSTRUCTOR
        public SetType(){
            super(Character.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Character>chrs){
            return serializeSet(chrs,CharType::serializeChar);
        }
        public @NotNull Set<Character>deserialize(byte[]bytes){
            return deserializeSet(bytes,CharType::deserializeChar);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Character>{
        //CONSTRUCTOR
        public ListType(){
            super(Character.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Character>chrs){
            return serializeList(chrs,CharType::serializeChar);
        }
        public @NotNull List<Character>deserialize(byte[]bytes){
            return deserializeList(bytes,CharType::deserializeChar);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Character>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Character.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Character @NotNull[]chrs){
            return serializeArray(chrs,CharType::serializeChar);
        }
        public @NotNull Character @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,CharType::deserializeChar);
        }
    }
}