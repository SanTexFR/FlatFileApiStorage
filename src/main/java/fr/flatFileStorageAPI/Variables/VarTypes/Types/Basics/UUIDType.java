package fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class UUIDType extends NormalMesh<UUID>{
    //CONSTRUCTOR
    public UUIDType(){
        super(UUID.class);
    }

    //METHODS
    public byte[]serialize(@NotNull UUID uuid){
        return serializeUUID(uuid);
    }
    public UUID deserialize(byte[]bytes){
        return deserializeUUID(bytes);
    }


    //STATIC METHODS
    public static byte[]serializeUUID(@NotNull UUID uuid){
        final ByteBuffer buffer=ByteBuffer.allocate(16);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return buffer.array();
    }
    public static UUID deserializeUUID(byte[]bytes){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        return new UUID(buffer.getLong(),buffer.getLong());
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<UUID>{
        //CONSTRUCTOR
        public SetType(){
            super(UUID.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull UUID>uuids){
            return serializeSet(uuids,UUIDType::serializeUUID);
        }
        public @NotNull Set<UUID>deserialize(byte[]bytes){
            return deserializeSet(bytes,UUIDType::deserializeUUID);
        }
    }

    //LIST
    public static class ListType extends ListMesh<UUID>{
        //CONSTRUCTOR
        public ListType(){
            super(UUID.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull UUID>uuids){
            return serializeList(uuids,UUIDType::serializeUUID);
        }
        public @NotNull List<UUID>deserialize(byte[]bytes){
            return deserializeList(bytes,UUIDType::deserializeUUID);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<UUID>{
        //CONSTRUCTOR
        public ArrayType(){
            super(UUID.class);
        }

        //METHODS
        public byte[]serialize(@NotNull UUID @NotNull[]uuids){
            return serializeArray(uuids,UUIDType::serializeUUID);
        }
        public @NotNull UUID @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,UUIDType::deserializeUUID);
        }
    }
}