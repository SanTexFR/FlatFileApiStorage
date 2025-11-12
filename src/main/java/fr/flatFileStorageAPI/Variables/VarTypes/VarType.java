package fr.flatFileStorageAPI.Variables.VarTypes;

import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface VarType<P>extends Vars{
    byte[]serialize(@NotNull P obj);
    P deserialize(byte[]bytes);

    @NotNull Class<?>getType();
    @NotNull String getStringType();
}