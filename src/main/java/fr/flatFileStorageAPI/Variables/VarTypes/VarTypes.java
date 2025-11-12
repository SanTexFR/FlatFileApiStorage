package fr.flatFileStorageAPI.Variables.VarTypes;

import fr.flatFileStorageAPI.FlatFileStorageAPI;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Adventure.ComponentType;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Basics.*;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Bigs.BigDecimalType;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Bigs.BigIntegerType;
import fr.flatFileStorageAPI.Variables.VarTypes.Types.Bukkits.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"unused","UnusedReturnValue"})
public interface VarTypes{
    //VARIABLES
    @NotNull ConcurrentHashMap<@NotNull String,@NotNull VarType<?>>types=new ConcurrentHashMap<>();

    // STRING
    @NotNull StringType STRING = new StringType();
    @NotNull StringType.SetType STRING_SET = new StringType.SetType();
    @NotNull StringType.ListType STRING_LIST = new StringType.ListType();
    @NotNull StringType.ArrayType STRING_ARRAY = new StringType.ArrayType();

    // INTEGER
    @NotNull IntegerType INTEGER = new IntegerType();
    @NotNull IntegerType.SetType INTEGER_SET = new IntegerType.SetType();
    @NotNull IntegerType.ListType INTEGER_LIST = new IntegerType.ListType();
    @NotNull IntegerType.ArrayType INTEGER_ARRAY = new IntegerType.ArrayType();

    // BIG_INTEGER
    @NotNull BigIntegerType BIG_INTEGER = new BigIntegerType();
    @NotNull BigIntegerType.SetType BIG_INTEGER_SET = new BigIntegerType.SetType();
    @NotNull BigIntegerType.ListType BIG_INTEGER_LIST = new BigIntegerType.ListType();
    @NotNull BigIntegerType.ArrayType BIG_INTEGER_ARRAY = new BigIntegerType.ArrayType();

    // DOUBLE
    @NotNull DoubleType DOUBLE = new DoubleType();
    @NotNull DoubleType.SetType DOUBLE_SET = new DoubleType.SetType();
    @NotNull DoubleType.ListType DOUBLE_LIST = new DoubleType.ListType();
    @NotNull DoubleType.ArrayType DOUBLE_ARRAY = new DoubleType.ArrayType();

    // BIG_DECIMAL
    @NotNull BigDecimalType BIG_DECIMAL = new BigDecimalType();
    @NotNull BigDecimalType.SetType BIG_DECIMAL_SET = new BigDecimalType.SetType();
    @NotNull BigDecimalType.ListType BIG_DECIMAL_LIST = new BigDecimalType.ListType();
    @NotNull BigDecimalType.ArrayType BIG_DECIMAL_ARRAY = new BigDecimalType.ArrayType();

    // FLOAT
    @NotNull FloatType FLOAT = new FloatType();
    @NotNull FloatType.SetType FLOAT_SET = new FloatType.SetType();
    @NotNull FloatType.ListType FLOAT_LIST = new FloatType.ListType();
    @NotNull FloatType.ArrayType FLOAT_ARRAY = new FloatType.ArrayType();

    // SHORT
    @NotNull ShortType SHORT = new ShortType();
    @NotNull ShortType.SetType SHORT_SET = new ShortType.SetType();
    @NotNull ShortType.ListType SHORT_LIST = new ShortType.ListType();
    @NotNull ShortType.ArrayType SHORT_ARRAY = new ShortType.ArrayType();

    // LONG
    @NotNull LongType LONG = new LongType();
    @NotNull LongType.SetType LONG_SET = new LongType.SetType();
    @NotNull LongType.ListType LONG_LIST = new LongType.ListType();
    @NotNull LongType.ArrayType LONG_ARRAY = new LongType.ArrayType();

    // BOOLEAN
    @NotNull BooleanType BOOLEAN = new BooleanType();
    @NotNull BooleanType.SetType BOOLEAN_SET = new BooleanType.SetType();
    @NotNull BooleanType.ListType BOOLEAN_LIST = new BooleanType.ListType();
    @NotNull BooleanType.ArrayType BOOLEAN_ARRAY = new BooleanType.ArrayType();

    // DATE
    @NotNull DateType DATE = new DateType();
    @NotNull DateType.SetType DATE_SET = new DateType.SetType();
    @NotNull DateType.ListType DATE_LIST = new DateType.ListType();
    @NotNull DateType.ArrayType DATE_ARRAY = new DateType.ArrayType();

    // BYTE
    @NotNull ByteType BYTE = new ByteType();
    @NotNull ByteType.SetType BYTE_SET = new ByteType.SetType();
    @NotNull ByteType.ListType BYTE_LIST = new ByteType.ListType();
    @NotNull ByteType.ArrayType BYTE_ARRAY = new ByteType.ArrayType();

    // CHAR
    @NotNull CharType CHAR = new CharType();
    @NotNull CharType.SetType CHAR_SET = new CharType.SetType();
    @NotNull CharType.ListType CHAR_LIST = new CharType.ListType();
    @NotNull CharType.ArrayType CHAR_ARRAY = new CharType.ArrayType();

    // UUID
    @NotNull UUIDType UUID = new UUIDType();
    @NotNull UUIDType.SetType UUID_SET = new UUIDType.SetType();
    @NotNull UUIDType.ListType UUID_LIST = new UUIDType.ListType();
    @NotNull UUIDType.ArrayType UUID_ARRAY = new UUIDType.ArrayType();

    // MATERIAL
    @NotNull MaterialType MATERIAL = new MaterialType();
    @NotNull MaterialType.SetType MATERIAL_SET = new MaterialType.SetType();
    @NotNull MaterialType.ListType MATERIAL_LIST = new MaterialType.ListType();
    @NotNull MaterialType.ArrayType MATERIAL_ARRAY = new MaterialType.ArrayType();

    //ITEMSTACK
    @NotNull ItemStackType ITEMSTACK = new ItemStackType();
    @NotNull ItemStackType.SetType ITEMSTACK_SET = new ItemStackType.SetType();
    @NotNull ItemStackType.ListType ITEMSTACK_LIST = new ItemStackType.ListType();
    @NotNull ItemStackType.ArrayType ITEMSTACK_ARRAY = new ItemStackType.ArrayType();

    //INVENTORY
    @NotNull InventoryType INVENTORY = new InventoryType();
    @NotNull InventoryType.SetType INVENTORY_SET = new InventoryType.SetType();
    @NotNull InventoryType.ListType INVENTORY_LIST = new InventoryType.ListType();
    @NotNull InventoryType.ArrayType INVENTORY_ARRAY = new InventoryType.ArrayType();

    //WORLD
    @NotNull WorldType WORLD = new WorldType();
    @NotNull WorldType.SetType WORLD_SET = new WorldType.SetType();
    @NotNull WorldType.ListType WORLD_LIST = new WorldType.ListType();
    @NotNull WorldType.ArrayType WORLD_ARRAY = new WorldType.ArrayType();

    //CHUNK
    @NotNull ChunkType CHUNK = new ChunkType();
    @NotNull ChunkType.SetType CHUNK_SET = new ChunkType.SetType();
    @NotNull ChunkType.ListType CHUNK_LIST = new ChunkType.ListType();
    @NotNull ChunkType.ArrayType CHUNK_ARRAY = new ChunkType.ArrayType();

    //LOCATION
    @NotNull LocationType LOCATION = new LocationType();
    @NotNull LocationType.SetType LOCATION_SET = new LocationType.SetType();
    @NotNull LocationType.ListType LOCATION_LIST = new LocationType.ListType();
    @NotNull LocationType.ArrayType LOCATION_ARRAY = new LocationType.ArrayType();

    //LOCATION
    @NotNull ComponentType COMPONENT = new ComponentType();
    @NotNull ComponentType.SetType COMPONENT_SET = new ComponentType.SetType();
    @NotNull ComponentType.ListType COMPONENT_LIST = new ComponentType.ListType();
    @NotNull ComponentType.ArrayType COMPONENT_ARRAY = new ComponentType.ArrayType();


    //METHODS
    static void initializer(){
        try{
            for(Field field:VarTypes.class.getDeclaredFields()){
                if(!(VarType.class.isAssignableFrom(field.getType())))continue;
                final VarType<?>varType=(VarType<?>)field.get(null);
                types.put(varType.getStringType(),varType);
            }
        }catch(IllegalAccessException e){
            FlatFileStorageAPI.logger.severe("varTypes initialization error: "+e.getMessage());
        }
    }

    static @Nullable VarType<?>valueOf(@NotNull final String str){
        return types.get(str);
    }
    static @NotNull Set<@NotNull String>getTypes(){
        return types.keySet();
    }
}