package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class ChunkType extends NormalMesh<Chunk>{
    //VARIABLES
    private static final @NotNull WorldType worldType;
    static{
        final VarType<?>type=VarTypes.valueOf("WORLD");
        if(type==null)worldType=new WorldType();
        else worldType=(WorldType)type;
    }

    //CONSTRUCTOR
    public ChunkType(){
        super(Chunk.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Chunk chunk){
        return serializeChunk(chunk);
    }
    public @NotNull Chunk deserialize(byte[]bytes){
        return deserializeChunk(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeChunk(@NotNull Chunk chunk){
        final byte[]serializedWorld=worldType.serialize(chunk.getWorld());
        final ByteBuffer buffer=ByteBuffer.allocate(Integer.BYTES+serializedWorld.length+Long.BYTES);

        //WORLD
        buffer.putInt(serializedWorld.length);
        buffer.put(serializedWorld);

        //KEY
        buffer.putLong(chunk.getChunkKey());

        return buffer.array();
    }
    public static @NotNull Chunk deserializeChunk(byte[]bytes){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        final byte[]worldBytes=new byte[buffer.getInt()];
        buffer.get(worldBytes);

        final long key=buffer.getLong();
        return worldType.deserialize(worldBytes).getChunkAtAsync(chunkX(key),chunkZ(key)).join();
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Chunk>{
        //CONSTRUCTOR
        public SetType(){
            super(Chunk.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Chunk>chunks){
            return serializeSet(chunks,ChunkType::serializeChunk);
        }
        public @NotNull Set<Chunk>deserialize(byte[]bytes){
            return deserializeSet(bytes,ChunkType::deserializeChunk);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Chunk>{
        //CONSTRUCTOR
        public ListType(){
            super(Chunk.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Chunk>chunks){
            return serializeList(chunks,ChunkType::serializeChunk);
        }
        public @NotNull List<Chunk>deserialize(byte[]bytes){
            return deserializeList(bytes,ChunkType::deserializeChunk);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Chunk>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Chunk.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Chunk @NotNull[]chunks){
            return serializeArray(chunks,ChunkType::serializeChunk);
        }
        public @NotNull Chunk @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,ChunkType::deserializeChunk);
        }
    }

    public static int chunkX(long key) {
        return (int) key;
    }

    public static int chunkZ(long key) {
        return (int) (key >> 32);
    }

}