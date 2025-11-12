package fr.flatFileStorageAPI.Variables.VarTypes.Meshs;


import fr.flatFileStorageAPI.Variables.VarTypes.VarMapTypes;
import fr.flatFileStorageAPI.Variables.VarTypes.VarType;
import fr.flatFileStorageAPI.Variables.VarTypes.Vars;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.*;

@SuppressWarnings({"unused","UnusedReturnValue"})
public class MapMesh<P,C>implements Vars{
    //VARIABLES
    protected final @NotNull VarType<P>keyInstance;
    protected final @NotNull VarType<C>valueInstance;
    protected final @NotNull VarMapTypes mapType;

    //CONSTRUCTOR
    public MapMesh(@NotNull VarMapTypes mapType,@NotNull VarType<P>keyInstance,@NotNull VarType<C>valueInstance){
        this.mapType=mapType;
        this.keyInstance=keyInstance;
        this.valueInstance=valueInstance;
    }

    //METHODS
    public @NotNull VarMapTypes getMapType(){
        return this.mapType;
    }
    public @NotNull String getStringMapType(){
        return this.mapType.name();
    }

    public @NotNull Class<?>getKeyType(){
        return this.keyInstance.getType();
    }
    public @NotNull String getStringKeyType(){
        return this.keyInstance.getStringType();
    }

    public @NotNull Class<?>getValueType(){
        return this.valueInstance.getType();
    }
    public @NotNull String getStringValueType(){
        return this.valueInstance.getStringType();
    }

    public @NotNull String getStringType(){
        return getStringMapType()+"¦"+getStringKeyType()+"¦"+getStringValueType();
    }

    public boolean equals(@NotNull Vars varType){
        return varType.isMap()&&varType.getStringType().equals(getStringType());
    }

    public byte[]serializeMap(@NotNull Map<P,C>map){
        byte[][]keys=new byte[map.size()][],
                values=new byte[map.size()][];
        int
                index=0,
                length=0;
        for(final Map.Entry<P,C>entry:map.entrySet()){
            final byte[]
                    key=this.keyInstance.serialize(entry.getKey()),
                    value=this.valueInstance.serialize(entry.getValue());
            length+=8+(key.length+value.length);
            keys[index]=key;
            values[index]=value;
            index++;
        }

        final ByteBuffer buffer=ByteBuffer.allocate(4+length);
        buffer.putInt(map.size());
        for(int i=0;i<map.size();i++){
            buffer.putInt(keys[i].length);
            buffer.put(keys[i]);

            buffer.putInt(values[i].length);
            buffer.put(values[i]);
        }
        buffer.flip();
        final byte[]result=new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public @NotNull Map<P,C>deserializeMap(byte[]bytes){
        final ByteBuffer buffer=ByteBuffer.wrap(bytes);
        final Map<P,C>map;
        if(getMapType().equals(VarMapTypes.HASHMAP))map=new HashMap<>();
        else map=new LinkedHashMap<>();

        final int mapSize=buffer.getInt();
        for(int i=0;i<mapSize;i++){
            //KEY
            final byte[]keyBytes=new byte[buffer.getInt()];
            buffer.get(keyBytes);

            //VALUE
            final byte[]valueBytes=new byte[buffer.getInt()];
            buffer.get(valueBytes);

            //ENTRY
            map.put(this.keyInstance.deserialize(keyBytes),this.valueInstance.deserialize(valueBytes));
        }return map;
    }

    public boolean isMap(){
        return true;
    }
}