package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class ItemStackType extends NormalMesh<ItemStack>{
    //VARIABLES
    private static final @NotNull ItemStack airItem=new ItemStack(Material.AIR);

    //CONSTRUCTOR
    public ItemStackType(){
        super(ItemStack.class);
    }

    //METHODS
    public byte[]serialize(@NotNull ItemStack item){
        return serializeItemStack(item);
    }
    public @NotNull ItemStack deserialize(byte[]bytes){
        return deserializeItemStack(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeItemStack(@Nullable ItemStack item){
        if(item==null||item.getType().equals(Material.AIR))return new byte[]{(byte)0xff};
        else return item.serializeAsBytes();
    }
    public static ItemStack deserializeItemStack(byte[]bytes){
        if(bytes.length==1&&bytes[0]==(byte)0xff)return airItem;
        else return ItemStack.deserializeBytes(bytes);
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<ItemStack>{
        //CONSTRUCTOR
        public SetType(){
            super(ItemStack.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull ItemStack> itemStacks){
            return serializeSet(itemStacks,ItemStackType::serializeItemStack);
        }
        public @NotNull Set<ItemStack>deserialize(byte[]bytes){
            return deserializeSet(bytes,ItemStackType::deserializeItemStack);
        }
    }

    //LIST
    public static class ListType extends ListMesh<ItemStack>{
        //CONSTRUCTOR
        public ListType(){
            super(ItemStack.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull ItemStack> itemStacks){
            return serializeList(itemStacks,ItemStackType::serializeItemStack);
        }
        public @NotNull List<ItemStack>deserialize(byte[]bytes){
            return deserializeList(bytes,ItemStackType::deserializeItemStack);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<ItemStack>{
        //CONSTRUCTOR
        public ArrayType(){
            super(ItemStack.class);
        }

        //METHODS
        public byte[]serialize(@NotNull ItemStack @NotNull[]itemStacks){
            return serializeArray(itemStacks,ItemStackType::serializeItemStack);
        }
        public @NotNull ItemStack @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,ItemStackType::deserializeItemStack);
        }
    }
}