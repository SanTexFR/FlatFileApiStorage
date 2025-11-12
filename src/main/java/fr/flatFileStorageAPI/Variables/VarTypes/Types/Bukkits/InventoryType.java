package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class InventoryType extends NormalMesh<Inventory>{
    //VARIABLES
    private static final @NotNull ItemStack airItem=new ItemStack(Material.AIR);
    private static final @NotNull ItemStackType.ArrayType itemType;
    static{
        final VarType<?>type=VarTypes.valueOf("ARRAY_ITEMSTACK");
        if(type==null)itemType=new ItemStackType.ArrayType();
        else itemType=(ItemStackType.ArrayType)type;
    }

    //CONSTRUCTOR
    public InventoryType(){
        super(Inventory.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Inventory inv){
        return serializeInventory(inv);
    }
    public @NotNull Inventory deserialize(byte[]bytes){
        return deserializeInventory(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeInventory(@NotNull Inventory inv){
        final ItemStack[]contents=inv.getContents();
        Arrays.setAll(contents,i->contents[i]!=null?contents[i]:airItem);
        final byte[]bytes=itemType.serialize(contents);
        final byte[]type=inv.getType().name().getBytes();
        final ByteBuffer buffer=ByteBuffer.allocate(8+type.length+bytes.length);
        buffer.putInt(type.length);
        buffer.put(type);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        return buffer.array();
    }
    public static Inventory deserializeInventory(byte[]bytes){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        final byte[]typeBytes=new byte[buffer.getInt()];
        buffer.get(typeBytes);
        final String type=new String(typeBytes);
        final byte[]valueBytes=new byte[buffer.getInt()];
        buffer.get(valueBytes);
        final Inventory inv=Bukkit.createInventory(null,org.bukkit.event.inventory.InventoryType.valueOf(type));
        inv.setContents(itemType.deserialize(valueBytes));
        return inv;
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Inventory>{
        //CONSTRUCTOR
        public SetType(){
            super(Inventory.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Inventory>itemStacks){
            return serializeSet(itemStacks,InventoryType::serializeInventory);
        }
        public @NotNull Set<Inventory>deserialize(byte[]bytes){
            return deserializeSet(bytes,InventoryType::deserializeInventory);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Inventory>{
        //CONSTRUCTOR
        public ListType(){
            super(Inventory.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Inventory> itemStacks){
            return serializeList(itemStacks,InventoryType::serializeInventory);
        }
        public @NotNull List<Inventory>deserialize(byte[]bytes){
            return deserializeList(bytes,InventoryType::deserializeInventory);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Inventory>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Inventory.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Inventory @NotNull[]itemStacks){
            return serializeArray(itemStacks,InventoryType::serializeInventory);
        }
        public @NotNull Inventory @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,InventoryType::deserializeInventory);
        }
    }
}