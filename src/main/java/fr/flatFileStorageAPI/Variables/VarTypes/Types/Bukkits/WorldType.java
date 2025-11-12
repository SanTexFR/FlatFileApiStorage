package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits;

import fr.flatFileStorageAPI.Variables.VarConfig;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics.StringType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused"})
public class WorldType extends NormalMesh<World>{
    //VARIABLES
    private static final @NotNull StringType stringType;
    static{
        final VarType<?>type=VarTypes.valueOf("STRING");
        if(type==null)stringType=new StringType();
        else stringType=(StringType)type;
    }

    //CONSTRUCTOR
    public WorldType(){
        super(World.class);
    }

    //METHODS
    public byte[]serialize(@NotNull World world){
        return serializeWorld(world);
    }
    public @NotNull World deserialize(byte[]bytes){
        return deserializeWorld(bytes);

    }

    //STATIC METHODS
    public static byte[]serializeWorld(@NotNull World world){
        return stringType.serialize(world.getName());
    }
    public static World deserializeWorld(byte[]bytes){
        World world=Bukkit.getWorld(stringType.deserialize(bytes));
        if(world==null){
            if(!VarConfig.varDefaultWorld)throw new RuntimeException("An world couldn't be deserialized, it doesn't exist.");
            else world=Bukkit.getWorlds().getFirst();
        }return world;
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<World> {
        //CONSTRUCTOR
        public SetType(){
            super(World.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull World>worlds){
            return serializeSet(worlds,WorldType::serializeWorld);
        }
        public @NotNull Set<World>deserialize(byte[]bytes){
            return deserializeSet(bytes,WorldType::deserializeWorld);
        }
    }

    //LIST
    public static class ListType extends ListMesh<World> {
        //CONSTRUCTOR
        public ListType(){
            super(World.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull World>worlds){
            return serializeList(worlds,WorldType::serializeWorld);
        }
        public @NotNull List<World>deserialize(byte[]bytes){
            return deserializeList(bytes,WorldType::deserializeWorld);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<World> {
        //CONSTRUCTOR
        public ArrayType(){
            super(World.class);
        }

        //METHODS
        public byte[]serialize(@NotNull World @NotNull[]worlds){
            return serializeArray(worlds,WorldType::serializeWorld);
        }
        public @NotNull World @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,WorldType::deserializeWorld);
        }
    }
}