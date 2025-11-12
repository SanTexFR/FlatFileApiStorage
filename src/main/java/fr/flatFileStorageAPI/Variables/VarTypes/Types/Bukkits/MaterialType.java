package fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits;

import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ArrayMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.ListMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.NormalMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.SetMesh;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics.StringType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.VarTypes;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class MaterialType extends NormalMesh<Material>{
    //VARIABLES
    private static final @NotNull StringType stringType;
    static{
        final VarType<?>type=VarTypes.valueOf("STRING");
        if(type==null)stringType=new StringType();
        else stringType=(StringType)type;
    }

    //CONSTRUCTOR
    public MaterialType(){
        super(Material.class);
    }

    //METHODS
    public byte[]serialize(@NotNull Material material){
        return serializeMaterial(material);
    }
    public @NotNull Material deserialize(byte[]bytes){
        return deserializeMaterial(bytes);
    }

    //STATIC METHODS
    public static byte[]serializeMaterial(@NotNull Material material){
        return stringType.serialize(material.name());
    }
    public static @NotNull Material deserializeMaterial(byte[]bytes){
        return Objects.requireNonNull(Material.matchMaterial(stringType.deserialize(bytes)));
    }

    //INTERNAL CLASS

    //SET
    public static class SetType extends SetMesh<Material>{
        //CONSTRUCTOR
        public SetType(){
            super(Material.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Set<@NotNull Material>materials){
            return serializeSet(materials,MaterialType::serializeMaterial);
        }
        public @NotNull Set<Material>deserialize(byte[]bytes){
            return deserializeSet(bytes,MaterialType::deserializeMaterial);
        }
    }

    //LIST
    public static class ListType extends ListMesh<Material>{
        //CONSTRUCTOR
        public ListType(){
            super(Material.class);
        }

        //METHODS
        public byte[]serialize(@NotNull List<@NotNull Material>materials){
            return serializeList(materials,MaterialType::serializeMaterial);
        }
        public @NotNull List<Material>deserialize(byte[]bytes){
            return deserializeList(bytes,MaterialType::deserializeMaterial);
        }
    }

    //ARRAY
    public static class ArrayType extends ArrayMesh<Material>{
        //CONSTRUCTOR
        public ArrayType(){
            super(Material.class);
        }

        //METHODS
        public byte[]serialize(@NotNull Material @NotNull[]materials){
            return serializeArray(materials,MaterialType::serializeMaterial);
        }
        public @NotNull Material @NotNull[]deserialize(byte[]bytes){
            return deserializeArray(bytes,MaterialType::deserializeMaterial);
        }
    }
}