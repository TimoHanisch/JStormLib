
package de.timoh.JStormLib;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT.HANDLEByReference;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author Timo Hanisch (timohanisch@gmail.com)
 * @version 1.1
 */
public class MPQArchive {
    
    private static StormLibWin lib;
    
    private HANDLEByReference ref;
    
    private MPQArchive() { 
        ref = new HANDLEByReference();
        if(lib == null){
            lib = StormLibWin.INSTANCE;
        }
    }
    
    public void addListFile(File f) throws FileNotFoundException, MPQArchiveException{
        if(!f.exists()){
            throw new FileNotFoundException("Could not find "+f);
        }
        int e = lib.SFileAddListFile(ref.getValue(), f.getAbsolutePath());
        if(e != 0){
            throw new MPQArchiveException("Error occured while adding a new list file. Errorcode "+e);
        }
    }
    
    public void openFile(){
        
    }
    
    public void flush() throws MPQArchiveException{
        if(lib.SFileFlushArchive(ref.getValue()) == 0){
            throw new MPQArchiveException("Error occured while flushing the archive. Errorcode "+Kernel32.INSTANCE.GetLastError());
        }
    }
    
    public void close() throws IOException{
        if(lib.SFileCloseArchive(ref.getValue()) == 0){
            throw new IOException("Could not close the archive "+this);
        }
    }
    
    public static MPQArchive createArchive(File f) throws IOException{
        return createArchive(f, StormLibWin.MPQ_CREATE_ARCHIVE_V2, StormLibWin.HASH_TABLE_SIZE_MIN);
    }
    
    public static MPQArchive createArchive(File f, int mpqVersion, int maxFiles) throws IOException{
        if(mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V1 && mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V2 
                && mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V3 && mpqVersion != StormLibWin.MPQ_CREATE_ARCHIVE_V4){
            mpqVersion = StormLibWin.MPQ_CREATE_ARCHIVE_V2;
        }
        if(maxFiles < StormLibWin.HASH_TABLE_SIZE_MIN){
            maxFiles = StormLibWin.HASH_TABLE_SIZE_MIN;
        }else if(maxFiles > StormLibWin.HASH_TABLE_SIZE_MAX){
            maxFiles = StormLibWin.HASH_TABLE_SIZE_MAX;
        }
        MPQArchive archive = new MPQArchive();
        if(lib.SFileCreateArchive(f.getAbsolutePath(), mpqVersion, maxFiles, archive.ref) != 0){
            return archive;
        }
        throw new IOException("Could not create the archive "+f);
    }
    
    public static MPQArchive openArchive(File f) throws FileNotFoundException, IOException{
        return openArchive(f, StormLibWin.BASE_PROVIDER_FILE);
    }
    
    public static MPQArchive openArchive(File f, int flag) throws FileNotFoundException, IOException{
        if(!f.exists()){
            throw new FileNotFoundException("Could not find "+f);
        }
        MPQArchive archive = new MPQArchive();
        if(lib.SFileOpenArchive(f.getAbsolutePath(), 0, flag, archive.ref) != 0){
            return archive;
        }
        throw new IOException("Could not open the archive "+f);
    }
}
