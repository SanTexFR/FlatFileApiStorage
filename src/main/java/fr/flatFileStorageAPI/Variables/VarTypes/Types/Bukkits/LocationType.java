package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class LocationType extends NormalMesh<Location>{
    //VARIABLES
    private static final @NotNull WorldType worldType;
    static{
        final VarType<?>type=VarTypes.valueOf("WORLD");
        if(type==null)worldType=new WorldType();
        else worldType =(WorldType)type;
    }

    //CONSTRUCTOR
    public LocationType(){
        super(Location.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Location loc){
        return serializeLocation(loc);
    }
    public @NotNull Location deserialize(byte[]bytes){
        return deserializeLocation(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeLocation(@NotNull Location loc){
        final byte[]serializedWorld=worldType.serialize(loc.getWorld());
        final ByteBuffer buffer=ByteBuffer.allocate(40+serializedWorld.length);

        //WORLD
        buffer.putInt(serializedWorld.length);
        buffer.put(serializedWorld);

        //X,Y,Z
        buffer.putDouble(loc.getX());
        buffer.putDouble(loc.getY());
        buffer.putDouble(loc.getZ());

        //YAW,PITCH
        buffer.putFloat(loc.getYaw());
        buffer.putFloat(loc.getPitch());

        return buffer.array();
    }
    public static @NotNull Location deserializeLocation(byte[]bytes){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        final byte[]worldBytes=new byte[buffer.getInt()];
        buffer.get(worldBytes);
        final World world=worldType.deserialize(worldBytes);
        final double x=buffer.getDouble(),
                y=buffer.getDouble(),
                z=buffer.getDouble();
        final float yaw=buffer.getFloat(),
                pitch=buffer.getFloat();
        return new Location(world,x,y,z,yaw,pitch);
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Location>{
        //CONSTRUCTOR
        public SetType(){
            super(Location.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Location> locs){
            return serializeSet(locs,LocationType::serializeLocation);
        }
        public @NotNull Set<Location>deserialize(byte[]bytes){
            return deserializeSet(bytes,LocationType::deserializeLocation);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Location>{
        //CONSTRUCTOR
        public ListType(){
            super(Location.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Location> locs){
            return serializeList(locs,LocationType::serializeLocation);
        }
        public @NotNull List<Location>deserialize(byte[]bytes){
            return deserializeList(bytes,LocationType::deserializeLocation);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Location>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Location.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Location @NotNull[]locs){
            return serializeArray(locs,LocationType::serializeLocation);
        }
        public @NotNull Location @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,LocationType::deserializeLocation);
        }
    }
}