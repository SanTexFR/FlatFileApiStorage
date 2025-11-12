package fr.flatFileStorageAPI.Variables;

import fr.flatFileStorageAPI.FlatFileStorageAPI;
import fr.flatFileStorageAPI.Variables.VarTypes.*;
import fr.flatFileStorageAPI.Variables.VarTypes.Meshs.MapMesh;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@SuppressWarnings({"unused","UnusedReturnValue","unchecked"})
public abstract class VarFile{
    //VARIABLES
    protected final @NotNull String filePath;
    protected final @NotNull File file;

    //CONSTRUCTOR
    public VarFile(@NotNull File directory,@NotNull String filePath){
        this.filePath=filePath;
        this.file=new File(directory,filePath+".var");
    }


    //METHODS
    protected void initialize(){
        loadData();
    }
    private void createFileDirectory(){
        try{
            final Path path=Path.of(this.file.getPath());
            Files.createDirectories(path.getParent());
            if(!Files.exists(path))Files.createFile(path);
        }catch(IOException e){
            throw new RuntimeException("Error when creating the path from the file: "+this.file.toPath()+"; error: "+e.getMessage());
        }
    }

//    protected void loadData() {
//        if (!this.file.exists() || this.file.length() == 0) return;
//
//        final ConcurrentHashMap<@NotNull String, @NotNull Object[]> data = getData();
//
//        try (final RandomAccessFile file = new RandomAccessFile(this.file, "r");
//             final GZIPInputStream gzipInputStream = new GZIPInputStream(new FileInputStream(file.getFD()));
//             final BufferedInputStream bufferedStream = new BufferedInputStream(gzipInputStream, 64 * 1024);
//             final DataInputStream dataStream = new DataInputStream(bufferedStream)) {
//
//            int index = 0;
//
//            while (true) {
//                index++;
//                try {
//                    // Vérifie qu'il reste au moins 4 octets pour readInt()
//                    if (dataStream.available() < 4) {
//                        throw new EOFException("Not enough bytes to read entry at index " + index);
//                    }
//
//                    // KEY
//                    final String key = safeReadString(dataStream);
//                    if ("END".equals(key)) break;
//
//                    // TYPE
//                    final String type = safeReadString(dataStream);
//
//                    if ("Map".equals(type)) {
//                        final VarMapTypes mapType = VarMapTypes.valueOf(safeReadString(dataStream));
//
//                        final VarType<?> keyVarType = VarTypes.valueOf(safeReadString(dataStream));
//                        if (keyVarType == null)
//                            throw new RuntimeException("Invalid key VarType");
//
//                        String valueTypeString = safeReadString(dataStream);
//                        VarType<?> valueVarType = VarTypes.valueOf(valueTypeString);
//                        if (valueVarType == null) valueVarType = CustomVarType.customTypes.get(valueTypeString);
//                        if (valueVarType == null)
//                            throw new RuntimeException("Invalid value VarType");
//
//                        final MapMesh<?, ?> mapMesh = new MapMesh<>(mapType, keyVarType, valueVarType);
//                        final byte[] mapData = safeReadBytes(dataStream);
//                        if (mapData == null) throw new EOFException("Map data truncated at entry " + index);
//
//                        data.put(key, new Object[]{mapMesh.deserializeMap(mapData), mapMesh});
//
//                    } else if ("Default".equals(type)) {
//                        final VarType<?> varType = VarTypes.valueOf(safeReadString(dataStream));
//                        if (varType == null)
//                            throw new RuntimeException("Invalid VarType");
//
//                        final byte[] valueBytes = safeReadBytes(dataStream);
//                        if (valueBytes == null) throw new EOFException("Default value truncated at entry " + index);
//
//                        data.put(key, new Object[]{varType.deserialize(valueBytes), varType});
//
//                    } else if ("Custom".equals(type)) {
//                        final CustomVarType<?> customVarType = CustomVarType.customTypes.get(safeReadString(dataStream));
//                        if (customVarType == null)
//                            throw new RuntimeException("Invalid CustomVarType");
//
//                        final byte[] valueBytes = safeReadBytes(dataStream);
//                        if (valueBytes == null) throw new EOFException("Custom value truncated at entry " + index);
//
//                        data.put(key, new Object[]{customVarType.deserialize(valueBytes), customVarType});
//
//                    } else {
//                        throw new RuntimeException("Unknown type in file: " + type);
//                    }
//
//                } catch (EOFException eof) {
//                    FlatFileStorageAPI.logger.severe("EOF reached or incomplete data at entry " + index + ": " + eof.getMessage());
//                    eof.printStackTrace();
//                    break; // on arrête après ce message
//                } catch (Exception e) {
//                    FlatFileStorageAPI.logger.severe("Error at entry " + index + ": " + e);
//                    e.printStackTrace();
//                }
//            }
//
//        } catch (Exception e) {
//            FlatFileStorageAPI.logger.severe("Fatal error when loading file: " + e);
//            e.printStackTrace();
//        }
//    }
//
//    /** Lit une string de manière sûre, retourne EOFException si données insuffisantes */
//    private String safeReadString(DataInputStream dataStream) throws IOException {
//        if (dataStream.available() < 4)
//            throw new EOFException("Not enough bytes to read string length");
//        int length = dataStream.readInt();
//        if (dataStream.available() < length)
//            throw new EOFException("Not enough bytes to read string data");
//        byte[] bytes = new byte[length];
//        dataStream.readFully(bytes);
//        return new String(bytes, StandardCharsets.UTF_8);
//    }
//
//    /** Lit un tableau de bytes de manière sûre, retourne null si EOF */
//    private byte[] safeReadBytes(DataInputStream dataStream) throws IOException {
//        if (dataStream.available() < 4)
//            throw new EOFException("Not enough bytes to read bytes length");
//        int length = dataStream.readInt();
//        if (dataStream.available() < length)
//            throw new EOFException("Not enough bytes to read byte array data");
//        byte[] bytes = new byte[length];
//        dataStream.readFully(bytes);
//        return bytes;
//    }


    protected void loadData(){
        if(!this.file.exists())return;
        if(this.file.length()==0)return;

        final ConcurrentHashMap<@NotNull String,@NotNull Object[]>data=getData();
        try(final RandomAccessFile file=new RandomAccessFile(this.file,"r");
             final GZIPInputStream gzipInputStream=new GZIPInputStream(new FileInputStream(file.getFD()));
             final BufferedInputStream bufferedStream=new BufferedInputStream(gzipInputStream,64*1024);
             final DataInputStream dataStream=new DataInputStream(bufferedStream)){

            while(dataStream.available()>0){
                //KEY
                final byte[]keyBytes=new byte[dataStream.readInt()];
                dataStream.readFully(keyBytes);
                final String key=new String(keyBytes,StandardCharsets.UTF_8);

                if("END".equals(key))
                    break;

                //TYPE
                final byte[]typeBytes=new byte[dataStream.readInt()];
                dataStream.readFully(typeBytes);
                final String type=new String(typeBytes,StandardCharsets.UTF_8);

                if("Map".equals(type)){
                    //MAP TYPE
                    final byte[]mapTypeBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(mapTypeBytes);
                    final String mapTypeString=new String(mapTypeBytes,StandardCharsets.UTF_8);
                    final VarMapTypes mapType=VarMapTypes.valueOf(mapTypeString);

                    //KEY TYPE
                    final byte[]keyTypeBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(keyTypeBytes);
                    final String keyString=new String(keyTypeBytes,StandardCharsets.UTF_8);
                    final VarType<?>keyVarType=VarTypes.valueOf(keyString);
                    if(keyVarType==null)throw new RuntimeException("Error when getting the varType from loading1: '"+keyString+"'");

                    //VALUE TYPE
                    final byte[]valueTypeBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(valueTypeBytes);
                    final String valueString=new String(valueTypeBytes,StandardCharsets.UTF_8);
                    VarType<?>valueVarType=VarTypes.valueOf(valueString);
                    if(valueVarType==null){
                        valueVarType=CustomVarType.customTypes.get(valueString);
                        if(valueVarType==null)throw new RuntimeException("Error when getting the varType from loading2: '"+valueString+"'");
                    }

                    final MapMesh<?,?>mapMesh=new MapMesh<>(mapType,keyVarType,valueVarType);

                    //VALUE
                    final byte[]mapData=new byte[dataStream.readInt()];
                    dataStream.readFully(mapData);

                    //STOCKER LES DONNEES
                    data.put(key,new Object[]{mapMesh.deserializeMap(mapData),mapMesh});
                }else if("Default".equals(type)){
                    //VARTYPE
                    final byte[]dataTypeBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(dataTypeBytes);
                    final VarType<?>varType=VarTypes.valueOf(new String(dataTypeBytes,StandardCharsets.UTF_8));
                    if(varType==null)throw new RuntimeException("Error when getting the varType from loading: "+new String(dataTypeBytes,StandardCharsets.UTF_8));

                    //VALUE
                    final byte[]valueBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(valueBytes);

                    //STOCKER LES DONNEES
                    data.put(key,new Object[]{varType.deserialize(valueBytes),varType});
                }else if("Custom".equals(type)){
                    //VARTYPE
                    final byte[]dataTypeBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(dataTypeBytes);
                    final CustomVarType<?>customVarType=CustomVarType.customTypes.get(new String(dataTypeBytes,StandardCharsets.UTF_8));
                    if(customVarType==null)throw new RuntimeException("Error when getting the customVarType from loading: "+new String(dataTypeBytes,StandardCharsets.UTF_8));

                    //VALUE
                    final byte[]valueBytes=new byte[dataStream.readInt()];
                    dataStream.readFully(valueBytes);

                    //STOCKER LES DONNEES
                    data.put(key,new Object[]{customVarType.deserialize(valueBytes),customVarType});
                }else FlatFileStorageAPI.logger.warning("Unknown type in the file: "+type);
            }
        }catch(IOException e){
            FlatFileStorageAPI.logger.severe("Error when loading the file: "+e.getMessage());
        }
    }

    protected void save(){
        //SI JAMAIS IL N'Y A AUCUNE DATA
        if (getData().isEmpty()) {
            try {
                Path path = this.file.toPath();
                if (Files.exists(path)) {
                    if (!Files.deleteIfExists(path)) {
                        Bukkit.getLogger().severe("❌ Impossible de supprimer le fichier : " + path.toAbsolutePath());
                    }
                }
            } catch (IOException e) {
                Bukkit.getLogger().severe("❌ Erreur lors de la suppression du fichier : " + this.file.getAbsolutePath());
                e.printStackTrace();
            }
            return;
        }

        if(!this.file.exists())createFileDirectory();

        try(RandomAccessFile file=new RandomAccessFile(this.file,"rw");
             final GZIPOutputStream gzipOutputStream=new GZIPOutputStream(new FileOutputStream(file.getFD())){
                 {def.setLevel(Deflater.BEST_SPEED);}};
            final BufferedOutputStream bufferedStream=new BufferedOutputStream(gzipOutputStream,64*1024);
            final DataOutputStream dataStream=new DataOutputStream(bufferedStream)){

            for(final Map.Entry<String,Object[]> entry:getData().entrySet()){
                final byte[]key=entry.getKey().getBytes(StandardCharsets.UTF_8);
                dataStream.writeInt(key.length);
                dataStream.write(key);
                final Vars vars=(Vars)entry.getValue()[1];
                if(vars.isMap()){
                    final MapMesh<Object,Object>mapMesh=(MapMesh<Object,Object>)vars;
                    final byte[]type="Map".getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(type.length);
                    dataStream.write(type);

                    final byte[]mapType=mapMesh.getStringMapType().getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(mapType.length);
                    dataStream.write(mapType);

                    final byte[]dataType1=mapMesh.getStringKeyType().getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(dataType1.length);
                    dataStream.write(dataType1);
                    final byte[]dataType2=mapMesh.getStringValueType().getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(dataType2.length);
                    dataStream.write(dataType2);
                    final byte[]value=mapMesh.serializeMap(((Map<Object,Object>)entry.getValue()[0]));
                    dataStream.writeInt(value.length);
                    dataStream.write(value);
                }else if(vars.isCustom()){
                    final CustomVarType<Object>customVarType=(CustomVarType<Object>)vars;
                    final byte[]type="Custom".getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(type.length);
                    dataStream.write(type);
                    final byte[]dataType=customVarType.getStringType().getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(dataType.length);
                    dataStream.write(dataType);
                    final byte[]value=customVarType.serialize(entry.getValue()[0]);
                    dataStream.writeInt(value.length);
                    dataStream.write(value);
                }else{
                    final VarType<Object>varType=(VarType<Object>)vars;
                    final byte[]type="Default".getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(type.length);
                    dataStream.write(type);
                    final byte[]dataType=varType.getStringType().getBytes(StandardCharsets.UTF_8);
                    dataStream.writeInt(dataType.length);
                    dataStream.write(dataType);
                    final byte[]value=varType.serialize(entry.getValue()[0]);
                    dataStream.writeInt(value.length);
                    dataStream.write(value);
                }
            }
            final byte[]endMarker="END".getBytes(StandardCharsets.UTF_8);
            dataStream.writeInt(endMarker.length);
            dataStream.write(endMarker);

            dataStream.flush();
            gzipOutputStream.finish();
        }catch (IOException e){
            FlatFileStorageAPI.logger.severe("Error when saving the file: "+e.getMessage());
        }
    }


    //INTERIT
    public abstract @NotNull ConcurrentHashMap<@NotNull String,@NotNull Object[]>getData();
}