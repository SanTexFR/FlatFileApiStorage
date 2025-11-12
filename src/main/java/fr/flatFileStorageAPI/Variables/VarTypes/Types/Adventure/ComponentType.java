package fr.flatFileStorageAPI.Variables.VarTypes.Types.Adventure;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics.StringType;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits.WorldType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Chunk;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class ComponentType extends NormalMesh<Component>{
    //VARIABLES
    private static final @NotNull StringType stringType;
    static{
        final VarType<?>type=VarTypes.valueOf("STRING");
        if(type==null)stringType=new StringType();
        else stringType=(StringType)type;
    }

    //CONSTRUCTOR
    public ComponentType(){
        super(Component.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Component component){
        return serializeComponent(component);
    }
    public @NotNull Component deserialize(byte[]bytes){
        return deserializeComponent(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeComponent(@NotNull Component component){
        return stringType.serialize(MiniMessage.miniMessage().serialize(component));
    }
    public static @NotNull Component deserializeComponent(byte[]bytes){
        return MiniMessage.miniMessage().deserialize(stringType.deserialize(bytes));
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Component>{
        //CONSTRUCTOR
        public SetType(){
            super(Component.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Component>components){
            return serializeSet(components,ComponentType::serializeComponent);
        }
        public @NotNull Set<Component>deserialize(byte[]bytes){
            return deserializeSet(bytes,ComponentType::deserializeComponent);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Component>{
        //CONSTRUCTOR
        public ListType(){
            super(Component.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Component>components){
            return serializeList(components,ComponentType::serializeComponent);
        }
        public @NotNull List<Component>deserialize(byte[]bytes){
            return deserializeList(bytes,ComponentType::deserializeComponent);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Component>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Component.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Component @NotNull[]components){
            return serializeArray(components, ComponentType::serializeComponent);
        }
        public @NotNull Component @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,ComponentType::deserializeComponent);
        }
    }
}